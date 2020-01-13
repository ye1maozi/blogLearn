package com.learn.blog.controller.backStage;

import com.learn.blog.pojo.Permission;
import com.learn.blog.pojo.Role;
import com.learn.blog.pojo.RolePermission;
import com.learn.blog.service.PermissionService;
import com.learn.blog.service.RolePermissionService;
import com.learn.blog.service.RoleService;
import com.learn.blog.util.Parse2ChildUtil;
import com.learn.blog.util.Result;
import com.learn.blog.util.RoleChangeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/permissions")
public class permissionController {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    @Autowired
    RolePermissionService rolePermissionService;
    @GetMapping("")
    public Object getPremList(){
        List<Permission> permissions = permissionService.selectAll();
        //设置children
        List<Permission> resultList =  Parse2ChildUtil.ParseToChild(permissions);

        return Result.success(resultList);
    }

    private  List<Permission> check2NewChildren( Map<Integer,List<Permission>> id2List , Permission perm){

        return  id2List.get(perm.getPid());
    }

    @GetMapping("/{rolename}")
    public Object getPermByRole(@PathVariable("rolename") String rolename){
        List<Permission> perm = permissionService.selectAll();
//        if (rolename == "admin"){
//            perm = permissionService.selectAll();
//        } else {
//            perm = permissionService.listPermissionByRole(rolename);
//        }

        List<Permission> resultList =  Parse2ChildUtil.ParseToChild(perm);

        return Result.success(resultList);
    }
    // 直接返回所有的权限 和已选择的权限
    @GetMapping("/role/{rolename}")
    public Object getAllPermByRole(@PathVariable("rolename") String rolename){
        List<Permission> perm;
        if (rolename == "admin"){
            perm = permissionService.selectAll();
        } else {
            perm = permissionService.listPermissionByRole(rolename);
        }

        List<Permission> resultList =  Parse2ChildUtil.ParseToChild(perm);

        return Result.success(resultList);
    }

    @PostMapping("/role/{rolename}")
    public Object changeRolePerms(@PathVariable("rolename") String rolename,String perms){
        Role role = roleService.selectByName(rolename);
        if (role == null){
            return Result.fail("null role");
        }
        String[] permis = perms.split(",");
        //插入
        return Result.success();
    }
    @PostMapping("/role")
    public Object changeRolePermsNotName(@RequestBody RoleChangeVO vo){
        Role role = roleService.selectByPrimaryKey(vo.getId());
        if (role == null){
            return Result.fail("null role");
        }
        List<RolePermission> list = vo.getChangeList();
        for (RolePermission rp:list
             ) {
            rp.setGroupid(role.getId());
            if (rp.getNewFlag()){
                rolePermissionService.insert(rp);
            }else{
                rolePermissionService.delete(rp);
            }
        }
        //插入
        return Result.success();
    }
    @GetMapping("/all")
    public Object getAllRoleAndPerm(){
        HashMap<String,List<Permission>> list = new HashMap<>();
        //所有的角色
        List<Role>  roles = roleService.selectAll();
        for (Role r :
                roles) {
            //每个角色的权限
            List<Permission> perm = permissionService.listPermissionByRole(r.getName());
            list.put(r.getName() , perm);
        }
        return Result.success(list);
    }

    @PostMapping("")
    public Object addPermissoion(@RequestBody Permission permission){
        permission.setId(null);
        permission.setStatus(0);
        int code = permissionService.insert(permission);
        return Result.success(code);
    }

    @PutMapping("")
    public Object updatePermission(@RequestBody Permission permission)
    {
        permission.setStatus(0);
        int code = permissionService.updateByPrimaryKeySelective(permission);
        return Result.success(permission);
    }

    @DeleteMapping("")
    public Object deletePermission(@RequestBody Permission permission)
    {
        //不删除 只更新状态
        Permission perm = permissionService.selectByPrimaryKey(permission.getId());
        if (null != perm){
            perm.setStatus( 1);
        }
        int code = permissionService.updateByPrimaryKeySelective(perm);
//        int code = permissionService.deleteByPrimaryKey(permission.getId());
        return Result.success(code);
    }
}
