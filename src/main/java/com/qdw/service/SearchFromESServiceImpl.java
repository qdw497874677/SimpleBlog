package com.qdw.service;

import com.alibaba.fastjson.JSON;
import com.qdw.pojo.Blog;
import com.qdw.vo.BlogForSearch;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.Field;
import org.springframework.data.elasticsearch.core.query.SimpleField;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Service
public class SearchFromESServiceImpl implements SearchFromESService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public Boolean createIndexForBlogs() throws IOException {
        //创建 创建索引的请求
        CreateIndexRequest request = new CreateIndexRequest("qdw_index");
        //执行请求，获得相应
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);



        return null;
    }

    @Override
    public Boolean putContent(List<BlogForSearch> blogs,String index) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("1m");
        for (BlogForSearch blog : blogs) {
//            bulkRequest.add(new IndexRequest("myblogs").source(JSON.toJSONString(blog), XContentType.JSON));
            bulkRequest.add(new IndexRequest(index).source(JSON.toJSONString(blog), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    @Override
    public List<Map<String, Object>> searchPageTerm(String keywords, int pageNo, int pageSize) throws IOException {
        if (pageNo<=1){
            pageNo = 1;
        }
        SearchRequest request = new SearchRequest("myblogs");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

        TermQueryBuilder query = QueryBuilders.termQuery("title", keywords);
        searchSourceBuilder.query(query);
//        query = QueryBuilders.termQuery("summary",keywords);
//        searchSourceBuilder.query(query);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        highlightBuilder.field("title");
        //关闭多个高亮
//        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        //执行查询
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());
        //解析结果
        List<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            //将高亮字段解析出来
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if (title!=null){
                Text[] fragments = title.fragments();
                StringBuilder newTitle = new StringBuilder();
                for (Text fragment : fragments) {
                    newTitle.append(fragment);
                }
                sourceAsMap.put("title",newTitle);
                System.out.println(newTitle);
            }
            list.add(hit.getSourceAsMap());
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> searchPageMatch(String keywords, int pageNo, int pageSize) throws IOException {
        if (pageNo<=1){
            pageNo = 1;
        }
        SearchRequest request = new SearchRequest("myblogs");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

        MatchQueryBuilder query = QueryBuilders.matchQuery("title", keywords);
        searchSourceBuilder.query(query);
//        query = QueryBuilders.termQuery("summary",keywords);
//        searchSourceBuilder.query(query);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        highlightBuilder.field("title");
        //关闭多个高亮
//        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        //执行查询
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());
        //解析结果
        List<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            //将高亮字段解析出来
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if (title!=null){
                Text[] fragments = title.fragments();
                StringBuilder newTitle = new StringBuilder();
                for (Text fragment : fragments) {
                    newTitle.append(fragment);
                }
                sourceAsMap.put("title",newTitle);
                System.out.println(newTitle);
            }
            list.add(hit.getSourceAsMap());
        }
        return list;
    }
}
