package org.theronin.nutcase.domain.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.theronin.nutcase.domain.user.entity.CustomUserDetails;
import org.theronin.nutcase.domain.user.entity.User;
import org.theronin.nutcase.domain.user.repository.UserRepository;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    private UserRepository userRepository;

    @Inject
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);
		String lowercaseLogin = login.toLowerCase();
		Optional<User> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);
		return userFromDatabase.map(user -> {
			if (!user.getActivated()) {
				throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
			}
			Set<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName()))
				.collect(Collectors.toSet());
			return new CustomUserDetails(user.getId(), lowercaseLogin,
				user.getPassword(),
				grantedAuthorities, true, true, true, true);
		}).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the "
			+ "database"));
	}
}
