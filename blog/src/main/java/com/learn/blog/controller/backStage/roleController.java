package com.learn.blog.controller.backStage;

import com.learn.blog.pojo.Role;
import com.learn.blog.service.RoleService;
import com.learn.blog.service.UserService;
import com.learn.blog.util.Parse2ChildUtil;
import com.learn.blog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/roles")
public class roleController {

    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;


    @GetMapping("")
    public Object getAllRole(){
//        HashMap<String, List<Role>> list = new HashMap<>();
        //所有的角色
        List<Role>  roles = roleService.selectAll();
        List<Role> resultList = Parse2ChildUtil.ParseToChild(roles);
        return Result.success(resultList);
    }

    @PostMapping("")
    public Object add(@RequestBody Role permission){
        int code = roleService.insert(permission);
        return Result.success(code);
    }

    @PutMapping("")
    public Object update(@RequestBody Role permission)
    {
        int code = roleService.updateByPrimaryKeySelective(permission);
        return Result.success(code);
    }

    @DeleteMapping("")
    public Object delete(@RequestBody Role permission)
    { 
        int code = roleService.deleteByPrimaryKey(permission.getId());
        return Result.success(code);
    }
}
