package ru.itmentor.spring.boot_security.demo.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.service.RoleService;

@Component
public class IdToRoleConverter implements Converter<String, Role> {

    private RoleService roleService;

    public IdToRoleConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role convert(String id) {
        return roleService.findById(Integer.parseInt(id));
    }
}
