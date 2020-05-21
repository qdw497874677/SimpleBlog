package com.qdw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("UserVo")
public class UserVo implements UserDetails {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String profile;
    private String email;
    private String avatar;
    private String role;
    private Date createTime;
    private Date updataTime;

    private List<Role> roles;
    private List<Blog> blogs;

    //将继承GrantedAuthority的这个类的集合返回(返回用户的权限集合。)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    //账号是否失效，返回false账号失效，不可用。
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //账号是否被锁，返回false，账号被锁，不可用
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //账号认证是否过期，返回false，过期，不可用
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //账号是否可用。返回false不可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
