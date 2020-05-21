package com.qdw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogQurey {
    private String title;
    private Integer typeId;
    private Boolean recommend;
    //是否查找所有，如果是，就会把recommend置空
    private Boolean isAll;
}
