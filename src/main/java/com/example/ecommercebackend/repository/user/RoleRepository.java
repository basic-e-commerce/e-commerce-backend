package com.example.ecommercebackend.repository.user;

import com.example.ecommercebackend.dto.user.role.RoleValidationDto;
import com.example.ecommercebackend.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByRoleNameEqualsIgnoreCase(String roleName);
    Optional<Role> findByRoleName(String roleName);

    @Query("SELECT new com.example.ecommercebackend.dto.user.role.RoleValidationDto(r.id,r.roleName) FROM Role r")
    List<RoleValidationDto>  findRoleDtosByUserId(@Param("userId") Integer userId);
}
