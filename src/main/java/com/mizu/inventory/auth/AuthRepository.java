package com.mizu.inventory.auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, UUID> {
  Optional<Auth> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByRoleId(UUID roleId);
}
