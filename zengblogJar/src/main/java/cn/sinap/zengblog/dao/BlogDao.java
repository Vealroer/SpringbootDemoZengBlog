package cn.sinap.zengblog.dao;

import cn.sinap.zengblog.domain.Blog;
import cn.sinap.zengblog.domain.BlogAndTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogDao {
    Blog getBlog(Long id);  //后台展示博客

    Blog getDetailedBlog(@Param("id") Long id);  //博客详情

    List<Blog> getAllBlog();

    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客

    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客

    List<Blog> getIndexBlog();  //主页博客展示

    List<Blog> getAllRecommendBlog();  //推荐博客展示

    List<Blog> getSearchBlog(String query);  //全局搜索博客

    List<Blog> searchAllBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客

    List<String> findGroupYear();  //查询所有年份，返回一个集合

    List<Blog> findByYear(@Param("year") String year);  //按年份查询博客

    void saveBlog(Blog blog);

    void saveBlogAndTag(BlogAndTag blogAndTag);

    void deleteBlogAndTag(Long id); //删除博客时删除该博客对应的标签信息

    void updateBlog(Blog blog);

    void deleteBlog(Long id);

    void addBlogView(Long id , Integer views);


}
