package org.theronin.nutcase.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theronin.nutcase.domain.user.NutcaseUser;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface NutcaseUserRepository extends JpaRepository<NutcaseUser, Long> {

		Optional<NutcaseUser> findByEmail(String email);
}
