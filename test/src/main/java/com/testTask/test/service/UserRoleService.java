package com.testTask.test.service;


import com.testTask.test.entity.UserRole;
import com.testTask.test.model.RoleModel;

public interface UserRoleService {
    UserRole save(UserRole userRole);
    UserRole save(RoleModel userRoleModel);
    UserRole findById(Long id);
    void deleteById(Long id);
}
