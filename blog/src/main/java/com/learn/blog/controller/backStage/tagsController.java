package com.learn.blog.controller.backStage;

import com.learn.blog.pojo.Tags;
import com.learn.blog.service.ArticleTagsService;
import com.learn.blog.service.TagsService;
import com.learn.blog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/tags","/api/fore/tags"})
public class tagsController {

    @Autowired
    TagsService tagsService;


    @GetMapping("")
    public Object getTags(){
        List<Tags> list = tagsService.selectAll();

        return Result.success(list);
    }

    @PostMapping("")
    public Object addTags(@RequestBody Tags tag){

        tagsService.insert(tag);

        return Result.success();
    }

}
