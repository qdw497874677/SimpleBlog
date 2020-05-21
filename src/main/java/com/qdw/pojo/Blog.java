package com.qdw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("Blog")
public class Blog implements Serializable {
    private Integer id;
    private String authorName;
    @NotBlank
    private String title;
    private String content;
    private String summary;
    private String firstPicture;
    private String flag;
    private Integer views;
    //赞赏
    private boolean appreciation;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private boolean shareStatement;
    private Date createTime;
    private Date updateTime;
    //更改博客指定的所有tagid
    private String tagIds;

    private List<Tag> tags;
    private List<Comment> comments;
    private Type type;
    private UserVo userVo;

    //把tags转化为tagids
    public void initTagIds(){
        this.tagIds = tagsToIds(tags);
    }
    public String tagsToIds(List<Tag> tags) {
        StringBuffer ids = new StringBuffer();
        if (tags!=null){
            for (int i = 0; i < tags.size(); i++) {
                if (i == tags.size()-1){
                    ids.append(tags.get(i).getId());
                }else {
                    ids.append(tags.get(i).getId()+",");
                }
            }

        }

        return ids.toString();
    }

}
