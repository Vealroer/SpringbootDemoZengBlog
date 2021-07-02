package cn.sinap.zengblog.controller;

import cn.sinap.zengblog.domain.Blog;
import cn.sinap.zengblog.domain.Comment;
import cn.sinap.zengblog.domain.Tag;
import cn.sinap.zengblog.domain.Type;
import cn.sinap.zengblog.exception.NotFoundException;
import cn.sinap.zengblog.service.BlogService;
import cn.sinap.zengblog.service.CommentService;
import cn.sinap.zengblog.service.TagService;
import cn.sinap.zengblog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/")
    public String index(@RequestParam(required = false,
            defaultValue = "1", value = "pagenum") int pagenum, Model model) {
        PageHelper.startPage(pagenum, 5);
        List<Blog> blogList = blogService.getIndexBlog(); //首页博客模块需要的内容
        List<Blog> recommendBlogList = blogService.getAllRecommendBlog(); //首页推荐博客模块需要的内容
        List<Type> typeList = typeService.getBlogType(); //首页类别模块需要的内容
        List<Tag> tagList = tagService.getBlogTag(); //首页标签模块需要的内容

        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("types",typeList);
        model.addAttribute("tags",tagList);
        model.addAttribute("recommendBlogs",recommendBlogList);
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blogShow(@PathVariable long id,Model model){
        Integer views = blogService.getDetailedBlog(id).getViews();
        views +=1;
        blogService.addBlogView(id,views);
        Blog blog = blogService.getDetailedBlog(id);
        model.addAttribute("blog", blog);
        List<Comment> comments = commentService.getCommentByBlogId(id);
        model.addAttribute("comments", comments);
        return "blog";
    }

    @PostMapping("/search")
    public String search(@RequestParam(required = false,
            defaultValue = "1", value = "pagenum") int pagenum, Model model,
                         @RequestParam String query){
        PageHelper.startPage(pagenum,1000);
        List<Blog> searchBlog = blogService.getSearchBlog(query);
        PageInfo<Blog> pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/game")
    public String toGame(){
        return "game";
    }


}
