package org.theronin.nutcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.theronin.nutcase.config.NCProperties;

@SpringBootApplication
@EnableConfigurationProperties({NCProperties.class})
public class Application {

		private static final Logger log = LoggerFactory.getLogger(Application.class);

		public static void main(String[] args) {
				SpringApplication.run(Application.class, args);
		}

}
