package org.theronin.nutcase.domain.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.theronin.nutcase.domain.user.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
