package org.theronin.nutcase.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to nutcase.
 *
 * <p>
 * Properties are configured in the application.yml/properties file.
 * </p>
 */
@ConfigurationProperties(prefix = "nc", ignoreUnknownFields = false)
public class NCProperties {

	private final Http http = new Http();

	private final Datasource datasource = new Datasource();

	private final Cache cache = new Cache();

	private final Mail mail = new Mail();

	private final Security security = new Security();

	public Http getHttp() {
		return http;
	}

	public Datasource getDatasource() {
		return datasource;
	}

	public Cache getCache() {
		return cache;
	}

	public Mail getMail() {
		return mail;
	}

	public Security getSecurity() {
		return security;
	}

	public static class Http {

		private final Cache cache = new Cache();

		public Cache getCache() {
			return cache;
		}

		public static class Cache {

			private int timeToLiveInDays = 31;

			public int getTimeToLiveInDays() {
				return timeToLiveInDays;
			}

			public void setTimeToLiveInDays(int timeToLiveInDays) {
				this.timeToLiveInDays = timeToLiveInDays;
			}
		}
	}

	public static class Datasource {

		private boolean cachePrepStmts = true;

		private int prepStmtCacheSize = 250;

		private int prepStmtCacheSqlLimit = 2048;

		private boolean useServerPrepStmts = true;

		public boolean isCachePrepStmts() {
			return cachePrepStmts;
		}

		public void setCachePrepStmts(boolean cachePrepStmts) {
			this.cachePrepStmts = cachePrepStmts;
		}

		public int getPrepStmtCacheSize() {
			return prepStmtCacheSize;
		}

		public void setPrepStmtCacheSize(int prepStmtCacheSize) {
			this.prepStmtCacheSize = prepStmtCacheSize;
		}

		public int getPrepStmtCacheSqlLimit() {
			return prepStmtCacheSqlLimit;
		}

		public void setPrepStmtCacheSqlLimit(int prepStmtCacheSqlLimit) {
			this.prepStmtCacheSqlLimit = prepStmtCacheSqlLimit;
		}

		public boolean isUseServerPrepStmts() {
			return useServerPrepStmts;
		}

		public void setUseServerPrepStmts(boolean useServerPrepStmts) {
			this.useServerPrepStmts = useServerPrepStmts;
		}
	}

	public static class Cache {

		private int timeToLiveSeconds = 3600;

		public int getTimeToLiveSeconds() {
			return timeToLiveSeconds;
		}

		public void setTimeToLiveSeconds(int timeToLiveSeconds) {
			this.timeToLiveSeconds = timeToLiveSeconds;
		}
	}

	public static class Mail {

		private String from = "nutcase@nutcase.com";

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}
	}

	public static class Security {

		private final Rememberme rememberme = new Rememberme();

		private final Authentication authentication = new Authentication();

		public Rememberme getRememberme() {
			return rememberme;
		}

		public Authentication getAuthentication() {
			return authentication;
		}

		public static class Authentication {

			private final Oauth oauth = new Oauth();

			public Oauth getOauth() {
				return oauth;
			}

			public static class Oauth {

				private String clientid;

				private String secret;

				private int tokenValidityInSeconds = 1800;

				public String getClientid() {
					return clientid;
				}

				public void setClientid(String clientid) {
					this.clientid = clientid;
				}

				public String getSecret() {
					return secret;
				}

				public void setSecret(String secret) {
					this.secret = secret;
				}

				public int getTokenValidityInSeconds() {
					return tokenValidityInSeconds;
				}

				public void setTokenValidityInSeconds(int tokenValidityInSeconds) {
					this.tokenValidityInSeconds = tokenValidityInSeconds;
				}
			}
		}

		public static class Rememberme {

			@NotNull
			private String key;

			public String getKey() {
				return key;
			}

			public void setKey(String key) {
				this.key = key;
			}
		}
	}

}
