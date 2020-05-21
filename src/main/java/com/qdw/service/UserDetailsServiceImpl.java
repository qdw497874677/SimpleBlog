package com.qdw.service;

import com.qdw.mapper.UserMapper;
import com.qdw.pojo.Role;
import com.qdw.pojo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) {
        UserVo userVo = userMapper.selectUserByUsername(username);
        if (userVo !=null){
            List<Role> roles = userMapper.selectUserAuthorities(userVo.getId());
            userVo.setRoles(roles);
        }
        System.out.println(userVo);
        return userVo == null?null
                : User.withUserDetails(userVo)
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .build();
    }
}
