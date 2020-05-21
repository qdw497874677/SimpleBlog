package com.qdw.service;

import com.qdw.pojo.UserVo;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    List<UserVo> selectAllUser();
    UserVo selectUserById(int id);
    UserVo getUserByUsername(String name);

}
