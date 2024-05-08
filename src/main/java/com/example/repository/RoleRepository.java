package com.example.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Role;
import com.example.entity.RoleEnum;

public interface RoleRepository extends CrudRepository<Role, Integer>{
    Optional<Role> findByName(RoleEnum name);
}
