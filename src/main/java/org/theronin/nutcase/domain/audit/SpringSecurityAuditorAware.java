package org.theronin.nutcase.domain.audit;


import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.theronin.nutcase.config.Constants;
import org.theronin.nutcase.domain.user.util.SecurityUtils;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
      return (userName != null ? userName : Constants.SYSTEM_ACCOUNT);
    }
}
