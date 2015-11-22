package org.theronin.nutcase.config.persistence;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
    "org.theronin.nutcase.domain.*"
})
@EntityScan(basePackages = {
    "org.theronin.nutcase.domain.*"
})
public class JPAConfiguration extends JpaBaseConfiguration {

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
        return adapter;
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("eclipselink.weaving", "false");
        map.put("eclipselink.ddl-generation", "create-tables");
        return map;
    }

}
