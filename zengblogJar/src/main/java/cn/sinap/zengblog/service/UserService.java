package cn.sinap.zengblog.service;

import cn.sinap.zengblog.domain.User;
import org.springframework.stereotype.Service;


public interface UserService {

    User checkUser(String username,String password);
}
