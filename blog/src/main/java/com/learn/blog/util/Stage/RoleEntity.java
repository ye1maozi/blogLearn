package com.learn.blog.util.Stage;

import java.util.List;
import java.util.Set;

public class RoleEntity extends BaseEntity {

    private String rolename;
    private String roleDesc;
    private Set<String> permissions;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
