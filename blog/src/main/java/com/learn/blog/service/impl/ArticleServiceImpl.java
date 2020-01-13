package com.learn.blog.service.impl;

import com.learn.blog.dao.ArticleMapper;
import com.learn.blog.pojo.Article;
import com.learn.blog.service.ArticleService;
import com.learn.blog.util.ArticleArchivesVo;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Override
    public List<Article> selectNewList() {

        return    articleMapper.selectByTime(5);
    }

    @Override
    public List<Article> queryPage(Map<String, Object> params) {
//        pageNo=1&pageSize=10&six=create_time&sort=desc
        if (params == null  || params.isEmpty()){
            return articleMapper.selectAll();
        }
        int start = Integer.parseInt( params.get("pageNo").toString());
        if (params.get("pageNo") == null){
          return articleMapper.selectAll();
        }
        int pageSize = Integer.parseInt( params.get("pageSize").toString());
        start = (start - 1) * pageSize;
        Integer end = start + pageSize;
        String sortby = (String) params.get("six");
        String sort = (String) params.get("sort");
        Integer categoryId = null;
        Integer tagId= null;
        if (params.containsKey("categoryId") ){
            categoryId = Integer.parseInt( params.get("categoryId").toString());
        }
        if (params.containsKey("tagId") ){
            tagId = Integer.parseInt( params.get("tagId").toString());
        }
        Integer year = null;
        Integer month = null;

        if (params.containsKey("year") ){
            year = Integer.parseInt( params.get("year").toString());
        }
        if (params.containsKey("month") ){
            month = Integer.parseInt( params.get("month").toString());
        }

        return articleMapper.queryPage(start,end,sortby,sort,categoryId,tagId,year,month);
    }

    @Override
    public List<ArticleArchivesVo> queyArticleArchives() {
        return articleMapper.queyArticleArchives();
    }
}
