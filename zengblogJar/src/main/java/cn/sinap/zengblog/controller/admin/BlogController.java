package cn.sinap.zengblog.controller.admin;

import cn.sinap.zengblog.domain.Blog;
import cn.sinap.zengblog.domain.User;
import cn.sinap.zengblog.service.BlogService;
import cn.sinap.zengblog.service.TagService;
import cn.sinap.zengblog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Api(value = "BlogController",description = "博客管理")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    public void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("tags",tagService.getAllTag());
    }


    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false,
            value = "pagenum",defaultValue = "1")int pagenum, Model model){
        PageHelper.startPage(pagenum,8);
        List<Blog> allBlog = blogService.getAllBlog();
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);
        model.addAttribute("pageInfo",pageInfo);
        setTypeAndTag(model);
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")  //按条件查询博客
    public String searchBlogs(Blog blog, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 8);
        List<Blog> allBlog = blogService.searchAllBlog(blog);
        System.out.println(allBlog);
        //得到分页结果对象
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("msg", "查询成功");
        setTypeAndTag(model);
        return "admin/blogs";
    }

    @GetMapping("/blogs/input") //去新增博客页面
    public String toAddBlog(Model model){
        Blog blog = new Blog();
        blog.setFlag("原创");
        model.addAttribute("blog", blog);  //返回一个blog对象给前端th:object
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs") //新增、编辑博客
    public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes){
        //设置user属性
        blog.setUser((User) session.getAttribute("user"));
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        //设置blog的type
        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值
        blog.setTags(tagService.getTagByString(blog.getTagIds()));


        if (blog.getId() == null) {   //id为空，则为新增
            blogService.saveBlog(blog);
            System.out.println("新增博客方法");
            attributes.addFlashAttribute("msg", "新增成功");
        } else {
            blogService.updateBlog(blog);
            System.out.println("修改博客方法");
            attributes.addFlashAttribute("msg", "修改成功");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlogs(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/input") //去编辑博客页面
    public String toEditBlog(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlog(id);
        blog.init();   //将tags集合转换为tagIds字符串
        model.addAttribute("blog", blog);     //返回一个blog对象给前端th:object
        System.out.println(blog);
        setTypeAndTag(model);
        return "admin/blogs-input";
    }


}
