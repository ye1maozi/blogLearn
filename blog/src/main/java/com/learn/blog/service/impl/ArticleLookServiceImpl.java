package com.learn.blog.service.impl;

import com.learn.blog.dao.ArticleLookMapper;
import com.learn.blog.pojo.ArticleLook;
import com.learn.blog.service.ArticleLookService;
import com.learn.blog.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

@Service
public class ArticleLookServiceImpl extends BaseServiceImpl<ArticleLook> implements ArticleLookService {

    @Autowired
    ArticleLookMapper articleLookMapper;

    @Override
    public boolean checkLook(HttpServletRequest request, int id) {
        /*浏览次数*/
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        Date date = calendar.getTime();
        String ip = IPUtil.getUserIp(request);
        int checkCount = articleLookMapper.checkArticleLook(id, ip,date);
        if(checkCount==0){
            ArticleLook articleLook = new ArticleLook();
            articleLook.setArticleid(id);
            articleLook.setIp(ip);
            articleLook.setCreatetime(new Date());
//            articleLook.setUserid(date);
            articleLookMapper.insert(articleLook);
            return true;
        }
        return false;
    }
}
