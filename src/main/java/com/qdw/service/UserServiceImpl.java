package com.qdw.service;

import com.qdw.mapper.UserMapper;
import com.qdw.pojo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserVo> selectAllUser() {
        return userMapper.queryUserList();
    }

    @Override
    public UserVo selectUserById(int id) {
        return userMapper.queryUserById(id);
    }

    @Override
    public UserVo getUserByUsername(String name) {
        return userMapper.selectUserByUsername(name);
    }


}
