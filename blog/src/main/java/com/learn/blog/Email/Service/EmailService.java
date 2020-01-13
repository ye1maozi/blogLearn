package com.learn.blog.Email.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    @Autowired
    JavaMailSender mailSender;

    public void sendMail() {
        System.out.println("send email");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 邮件发送者，这里不能随便填写，必须是真实的发送邮件的邮箱名称
        mailMessage.setFrom("javasend@aliyun.com");
        // 邮件接受者
        mailMessage.setTo("heiseye00@163.com");
        // 邮件主题
        mailMessage.setSubject("testSubject");
        // 邮件内容
        mailMessage.setText("hello email!");
        // 发送邮件
        mailSender.send(mailMessage);
    }
}
