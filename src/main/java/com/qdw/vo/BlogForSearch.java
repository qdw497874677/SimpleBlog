package com.qdw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogForSearch {
    private Integer blogId;
    private String title;
    private String summary;
    private String typeName;
    private List<String> tagsName;
    private String authorName;
    private Date createtime;
}
