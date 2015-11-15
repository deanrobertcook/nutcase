package org.theronin.nutcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theronin.nutcase.domain.NutcaseUser;

import java.util.Optional;

/**
 *
 */
public interface NutcaseUserRepository extends JpaRepository<NutcaseUser, Long> {
    Optional<NutcaseUser> findByEmail(String email);
}
