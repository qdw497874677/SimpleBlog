package com.qdw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("BlogTag")
public class BlogTag {
    private Integer id;
    private Integer blogId;
    private Integer tagId;
}
