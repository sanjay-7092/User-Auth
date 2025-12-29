package com.nasa.auth.controller;

import com.nasa.auth.dto.Role;
import com.nasa.auth.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    RoleController(RoleService roleService){
        this.roleService=roleService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_NASA_USER','ROLE_EXTERNAL_USER')")
    public ResponseEntity<List<Role>> getAll(){
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_NASA_USER','ROLE_EXTERNAL_USER')")
    public ResponseEntity<Role> getById(@PathVariable Long id){
        return ResponseEntity.ok(roleService.getById(id));
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Role> create(@RequestBody Role role){
        return ResponseEntity.ok(roleService.create(role));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_NASA_USER')")
    public ResponseEntity<Role> updateById(@RequestBody Role role, @PathVariable Long id){
        return ResponseEntity.ok(roleService.updateById(role,id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        roleService.delete(id);
        return ResponseEntity.ok("Deleted Successfully");
    }
}
