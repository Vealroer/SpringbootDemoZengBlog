package cn.sinap.zengblog.service.impl;

import cn.sinap.zengblog.dao.UserDao;
import cn.sinap.zengblog.domain.User;
import cn.sinap.zengblog.service.UserService;
import cn.sinap.zengblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        //可以用spring自带的DigestUtils.md5DigestAsHex
        return userDao.findUserByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
