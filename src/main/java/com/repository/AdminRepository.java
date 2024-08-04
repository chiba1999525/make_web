package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByIdAndAdminIdAndAdminPassword(int id, String adminId, String adminPassword);
    
    boolean existsByIdAndAdminIdAndAdminPassword(int id, String adminId, String adminPassword);
}
