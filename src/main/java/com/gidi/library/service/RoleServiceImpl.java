package com.gidi.library.service;

import com.gidi.library.exception.ResourceNotFoundException;
import com.gidi.library.model.Role;
import com.gidi.library.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRole(Integer roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role does not exist"));
    }
}
