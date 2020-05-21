package com.qdw.mapper;

import com.qdw.pojo.Role;
import com.qdw.pojo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//表示这是一个mybatis的一个mapper
@Mapper
@Repository
public interface UserMapper {
    List<UserVo> queryUserList();
    UserVo queryUserById(int id);
    int addUser(UserVo userVo);
    int updateUser(UserVo userVo);
    int deleteUser(UserVo userVo);
    //根据用户id查找所有对应的Role
    List<Role> selectUserAuthorities(int id);
    //根据username查找用户
    UserVo selectUserByUsername(String username);
    //根据Role的id查找一个Role
    Role selectRoleById(int id);
}
