package org.theronin.nutcase.config.persistence;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
    "org.theronin.nutcase.domain.*"
})
@EntityScan(basePackages = {
    "org.theronin.nutcase.domain.*"
})
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class JPAConfiguration {


}
