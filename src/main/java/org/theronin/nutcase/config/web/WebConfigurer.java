package org.theronin.nutcase.config.web;

import java.util.Arrays;
import java.util.EnumSet;
import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.theronin.nutcase.config.Constants;
import org.theronin.nutcase.config.cache.CacheConfiguration;
import org.theronin.nutcase.config.web.filter.CachingHttpHeadersFilter;

@Configuration
@AutoConfigureAfter(CacheConfiguration.class)
public class WebConfigurer implements ServletContextInitializer {

	private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

	@Inject
	private Environment env;

	@Bean
	public LoadTimeWeaver loadTimeWeaver() {
		return new org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver();
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		log.info("Web application configuration, using profiles: {}", Arrays.toString(env.getActiveProfiles()));
		EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
		if (env.acceptsProfiles(Constants.SPRING_PROFILE_PRODUCTION)) {
			initCachingHttpHeadersFilter(servletContext, disps);
		}
		if (env.acceptsProfiles(Constants.SPRING_PROFILE_DEVELOPMENT)) {
			// add dev config here
		}
		log.info("Web application fully configured");
	}

	/**
	 * Initializes the caching HTTP Headers Filter.
	 */
	private void initCachingHttpHeadersFilter(ServletContext servletContext,
		EnumSet<DispatcherType> disps) {
		log.debug("Registering Caching HTTP Headers Filter");
		FilterRegistration.Dynamic cachingHttpHeadersFilter
			= servletContext.addFilter("cachingHttpHeadersFilter",
				new CachingHttpHeadersFilter(env));

		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/assets/*");
		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/scripts/*");
		cachingHttpHeadersFilter.setAsyncSupported(true);
	}
}
