package cn.sinap.zengblog.controller;

import cn.sinap.zengblog.domain.Comment;
import cn.sinap.zengblog.domain.User;
import cn.sinap.zengblog.service.BlogService;
import cn.sinap.zengblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")  //展示留言
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.getCommentByBlogId(blogId));
        model.addAttribute("blog", blogService.getDetailedBlog(blogId));
        return "blog";
    }

    @PostMapping("/comments")   //提交留言
    public String post(Comment comment, HttpSession session, RedirectAttributes attributes) {
        //后端校验
        if (comment.getContent() == "" || comment.getEmail() == "" || comment.getNickname() == "") {
            attributes.addFlashAttribute("msg", "请填写完整评论信息");
            return "redirect:/comments/" + comment.getBlogId();
        }
        Long blogId = comment.getBlogId();
        comment.setBlog(blogService.getDetailedBlog(blogId));  //绑定博客与评论
        comment.setBlogId(blogId);
        User user = (User) session.getAttribute("user");
        comment.setAvatar(avatar);
        if (user != null) {   //用户为管理员
            if (comment.getNickname().equals(user.getNickname()) && comment.getEmail().equals(user.getEmail())) {
                comment.setAdminComment(true);
                comment.setAvatar(user.getAvatar());
            }
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
