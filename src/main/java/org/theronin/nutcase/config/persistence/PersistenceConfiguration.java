package org.theronin.nutcase.config.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.Arrays;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.theronin.nutcase.config.NCProperties;

@Configuration
public class PersistenceConfiguration {

    private final Logger log = LoggerFactory.getLogger(PersistenceConfiguration.class);

    @Inject
    private Environment env;

    @Bean(destroyMethod = "close")
    public DataSource dataSource(DataSourceProperties dataSourceProperties, NCProperties ncProperties) {
        log.debug("Configuring Datasource");
        if (dataSourceProperties.getUrl() == null) {
            log.error("Your database connection pool configuration is incorrect! The application"
                    + " cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(dataSourceProperties.getDriverClassName());
        config.addDataSourceProperty("url", dataSourceProperties.getUrl());
        if (dataSourceProperties.getUsername() != null) {
            config.addDataSourceProperty("user", dataSourceProperties.getUsername());
        } else {
            config.addDataSourceProperty("user", ""); // HikariCP doesn't allow null user
        }
        if (dataSourceProperties.getPassword() != null) {
            config.addDataSourceProperty("password", dataSourceProperties.getPassword());
        } else {
            config.addDataSourceProperty("password", ""); // HikariCP doesn't allow null password
        }

        //MySQL optimizations, see https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
        if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource".equals(dataSourceProperties.getDriverClassName())) {
            config.addDataSourceProperty("cachePrepStmts", ncProperties.getDatasource().isCachePrepStmts());
            config.addDataSourceProperty("prepStmtCacheSize", ncProperties.getDatasource().getPrepStmtCacheSize());
            config.addDataSourceProperty("prepStmtCacheSqlLimit", ncProperties.getDatasource().getPrepStmtCacheSqlLimit());
        }
        return new HikariDataSource(config);
    }

    /**
     * Open the TCP port for the H2 database, so it is available remotely.
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2TCPServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers");
    }
}
