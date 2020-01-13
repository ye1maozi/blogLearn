package com.learn.blog.service.impl;

import com.learn.blog.dao.ArticleTagsMapper;
import com.learn.blog.pojo.ArticleTags;
import com.learn.blog.service.ArticleTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleTagsServiceImpl extends BaseServiceImpl<ArticleTags> implements ArticleTagsService {
    @Autowired
    ArticleTagsMapper articleTagsMapper;

    @Override
    public void updateArticle2Tags(Integer aid, List<Integer> list) {
        List<ArticleTags> tags = articleTagsMapper.selectByArticleId(aid);
        Map<Integer,Boolean> check = new HashMap<>();
        //找出删除的 新增的
        for (Integer newId: list
             ) {
            check.put(newId,false);
        }
        for (ArticleTags oldId : tags){
            if (check.containsKey(oldId.getTagid())){
                check.remove(oldId.getTagid());
            } else {
                check.put(oldId.getTagid(),true);
            }
        }
       Iterator<Map.Entry<Integer,Boolean>> entries = check.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer,Boolean> entry = entries.next();
            Integer id = entry.getKey();
            Boolean isDelete = entry.getValue();
            if (isDelete) {
                articleTagsMapper.deleteByArticleAndTagId(aid,id);
            } else {
                ArticleTags articleTags = new ArticleTags();
                articleTags.setTagid(id);
                articleTags.setArticleid(aid);
                articleTagsMapper.insert(articleTags);
            }
        }
    }
}

