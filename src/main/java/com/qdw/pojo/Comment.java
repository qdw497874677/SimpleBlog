package com.qdw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("Comment")
public class Comment {
    private Integer id;
    private String nickname;
    private String email;
    private String ip;
    private String content;
    private String avatar;
    private Date createTime;

    private Blog blog;
    private List<Comment> replyComments;
    private Comment parentComment;
}
