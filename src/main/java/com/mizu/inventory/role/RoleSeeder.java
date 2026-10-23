package com.mizu.inventory.role;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationRunner {

  private final RoleRepository roleRepository;

  @Override
  public void run(ApplicationArguments args) {
    for (String name : List.of(Role.ADMIN, Role.USER)) {
      if (!roleRepository.existsByName(name)) {
        roleRepository.save(Role.builder().name(name).build());
      }
    }
  }
}
