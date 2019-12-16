package com.yf.model.custom.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 发送邮件需要使用的基本信息
 */
public class MyAuthenticator extends Authenticator{   
    String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    @Override
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);   
    }   
}   
