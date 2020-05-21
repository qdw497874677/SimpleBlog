package com.qdw.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

@Repository
@Data
@Alias("Role")
public class Role implements GrantedAuthority {
    private Integer id;
    private String role;
    @Override
    public String getAuthority() {
        return role;
    }

//    public void setRole(String authority) {
//        this.role = authority;
//    }
}
