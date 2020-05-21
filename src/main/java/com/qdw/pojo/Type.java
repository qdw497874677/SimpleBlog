package com.qdw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("Type")
public class Type {
    private Integer id;
    @NotBlank(message = "分类名称不能为空")
    private String name;
    private Integer count;

    private List<Blog> blogs;

}
