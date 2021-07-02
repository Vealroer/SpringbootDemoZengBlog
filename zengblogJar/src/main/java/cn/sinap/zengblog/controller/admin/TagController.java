package cn.sinap.zengblog.controller.admin;

import cn.sinap.zengblog.domain.Tag;
import cn.sinap.zengblog.domain.Type;
import cn.sinap.zengblog.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@Api(value = "TagController",description = "标签管理")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("展示所有标签")
    @RequestMapping("/tags")
    public String tags(@RequestParam(required = false,
                        defaultValue = "1",value = "pagenum") int pagenum,
                       Model model){
        PageHelper.startPage(pagenum,8);
        List<Tag> allTag = tagService.getAllTag();
        PageInfo<Tag> pageInfo = new PageInfo<>(allTag);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/tags";
    }

    @ApiOperation("新增标签")
    @GetMapping("/tags/input")
    public String toAddTags(Model model){
        //返回一个新的Type对象到前端，用于接收用户新建
        model.addAttribute("tag",new Type());
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String addTagPost(Tag tag, RedirectAttributes attributes){
        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName != null){
            attributes.addFlashAttribute("msg","不能添加相同的标签");
            return "redirect:/admin/tags/input";
        } else {
            attributes.addFlashAttribute("msg","添加成功");
        }
        tagService.saveTag(tag);
        return "redirect:/admin/tags";
    }

    @ApiOperation("编辑标签")
    @GetMapping("/tags/{id}/input")
    public String toEditTag(@PathVariable long id , Model model){
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag",tag);
        return "admin/tags-input";
    }

    @PostMapping("/tags/{id}")
    public String editTag(@PathVariable long id,Tag tag, RedirectAttributes attributes){
        Tag tagByName = tagService.getTagByName(tag.getName());
        if (tagByName != null){
            attributes.addFlashAttribute("msg","不能和原来标签相同");
            return "redirect:/admin/tags/input";
        } else {
            attributes.addFlashAttribute("msg","修改成功");
        }
        tagService.updateTag(tag);
        return "redirect:/admin/tags";
    }

    @ApiOperation("删除标签")
    @GetMapping("/tags/{id}/delete")
    public String deleteType(@PathVariable long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg","删除成功");
        return "redirect:/admin/tags";
    }

}
