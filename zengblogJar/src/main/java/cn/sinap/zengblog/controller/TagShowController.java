package cn.sinap.zengblog.controller;

import cn.sinap.zengblog.domain.Blog;
import cn.sinap.zengblog.domain.Tag;
import cn.sinap.zengblog.service.BlogService;
import cn.sinap.zengblog.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Api(value = "TagShowController" , description = "标签页面")
public class TagShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id,
                       @RequestParam(required = false,defaultValue = "1",value = "pagenum")
                               int pagenum,
                       Model model){
        List<Tag> tags = tagService.getBlogTag();
        if (id == -1){
            id = tags.get(0).getId();
        }
        PageHelper.startPage(pagenum,5);
        List<Blog> blogs = blogService.getByTagId(id);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("tags",tags);
        model.addAttribute("activeTagId",id);
        return "tags";
    }

}
