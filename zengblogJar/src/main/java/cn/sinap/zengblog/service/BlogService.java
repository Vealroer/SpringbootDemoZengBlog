package cn.sinap.zengblog.service;

import cn.sinap.zengblog.domain.Blog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);  //后台展示博客

    Blog getDetailedBlog(Long id);  //前端展示博客

    List<Blog> getAllBlog();

    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客

    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客

    List<Blog> getIndexBlog();  //主页博客展示

    List<Blog> getAllRecommendBlog();  //推荐博客展示

    List<Blog> getSearchBlog(String query);  //全局搜索博客

    Map<String,List<Blog>> archiveBlog();  //归档博客

    int countBlog();  //查询博客条数

    void saveBlog(Blog blog);

    void updateBlog(Blog blog);

    void deleteBlog(Long id);

    List<Blog> searchAllBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客

    void addBlogView(Long id , Integer views); //根据访问次数增加访问量
}
