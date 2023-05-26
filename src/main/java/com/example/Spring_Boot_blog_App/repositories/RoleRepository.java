package com.example.Spring_Boot_blog_App.repositories;

import com.example.Spring_Boot_blog_App.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
