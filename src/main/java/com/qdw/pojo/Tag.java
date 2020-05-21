package com.qdw.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import sun.plugin2.message.Message;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("Tag")
public class Tag {
    private Integer id;
    @NotBlank(message = "标签名不能为空")
    private String name;

    private List<Blog> blogs;




}
