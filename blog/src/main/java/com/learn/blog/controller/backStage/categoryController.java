package com.learn.blog.controller.backStage;

import com.learn.blog.pojo.Category;
import com.learn.blog.service.CategoryService;
import com.learn.blog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/categorys","/api/fore/categorys"})
public class categoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public Object getTags(){
        List<Category> list = categoryService.selectAll();

        return Result.success(list);
    }

    @PostMapping("")
    public Object addTags(@RequestBody Category tag){

        categoryService.insert(tag);

        return Result.success();
    }


    @PutMapping("")
    public Object editCategory(@RequestBody Category tag){

        categoryService.updateByPrimaryKeySelective(tag);

        return Result.success();
    }
}
