package com.qdw.service;

import com.qdw.pojo.Blog;
import com.qdw.vo.BlogForSearch;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchFromESService {
    Boolean createIndexForBlogs() throws IOException;
    Boolean putContent(List<BlogForSearch> blogs,String index) throws IOException;
    List<Map<String,Object>> searchPageTerm(String keywords, int pageNo, int pageSize) throws IOException;
    List<Map<String,Object>> searchPageMatch(String keywords, int pageNo, int pageSize) throws IOException;
}
