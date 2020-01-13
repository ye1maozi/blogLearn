package com.learn.blog.controller;

import com.learn.blog.util.MultipartFilesVO;
import com.learn.blog.util.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @PostMapping("")
    public Object uploadFiles(@RequestParam MultipartFile[] files ,@RequestParam("types") String[] names, HttpServletRequest request){
        if (files == null || files.length != names.length){
            return Result.fail("empty");
        }
        List<MultipartFilesVO> list = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            String url = saveFile(files[i]);
            list.add(new MultipartFilesVO(names[i],url));
        }
        return Result.success(list);
    }

    private String saveFile(MultipartFile imgFile ) {
        if (imgFile == null || imgFile.isEmpty()){
            return "";
        }
        try{
            String originalFileName = imgFile.getOriginalFilename();
            String suffix = originalFileName.substring(originalFileName.indexOf(".")).toLowerCase();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            int size = (int) imgFile.getSize();

            String date = fmt.format(new Date());
            File folder = new File(uploadFolder);
            // 自定义的文件名称
            String trueFileName = UUID.randomUUID().toString();
            String name = trueFileName + "_" + date  + suffix;
            File file = new File(folder,name);
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            imgFile.transferTo(file);

            return "upload-images/" + file.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
