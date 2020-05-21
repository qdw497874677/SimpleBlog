package com.qdw.mapper;

import com.qdw.pojo.BlogTag;
import com.qdw.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface BlogTagMapper {
    int insertBlogTag(int blogId,int tagId);
    int insertBlogTags(List<BlogTag> list);
    int hasBLogTag(int blogId,int tagId);
    int deleteBlogTagByBlogId(int blogId);
    int deleteBlogTagByTagId(int tagId);
    List<Tag> qureyTagsByBlogId(int blogId);
    List<Integer> qureyBlogsByTagId(int tagId);
    int countByBlogId(int blogId);

}
