package cn.sinap.zengblog.controller.admin;

import cn.sinap.zengblog.domain.Type;
import cn.sinap.zengblog.service.TypeService;
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
@Api(value = "TypeController",description = "博客分类管理")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @ApiOperation("展示所有分类")
    @GetMapping("/types")
    public String Types(@RequestParam(required = false,
            defaultValue = "1",value = "pagenum")int pageNum,
                        Model model){
        PageHelper.startPage(pageNum,8);
        List<Type> allType = typeService.getAllType();
        //分页对象
        PageInfo<Type> pageInfo = new PageInfo<>(allType);
        model.addAttribute("pageInfo",pageInfo);
        //System.out.println(pageInfo);
        return "admin/types";
    }

    @ApiOperation("新增分类")
    @GetMapping("/types/input")
    public String toAddTypes(Model model){
        //返回一个新的Type对象到前端，用于接收用户新建
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String addTypesPost(Type type, RedirectAttributes attributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null){
            attributes.addFlashAttribute("msg","不能添加相同的分类");
            return "redirect:/admin/types/input";
        } else {
            attributes.addFlashAttribute("msg","添加成功");
        }
        typeService.saveType(type);
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editType(@PathVariable long id,Type type, RedirectAttributes attributes){
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null){
            attributes.addFlashAttribute("msg","不能和原来分类相同");
            return "redirect:/admin/types/input";
        } else {
            attributes.addFlashAttribute("msg","修改成功");
        }
        typeService.updateType(type);
        return "redirect:/admin/types";
    }


    @ApiOperation("编辑分类")
    @GetMapping("/types/{id}/input")
    public String toEditType(@PathVariable long id , Model model){
        Type type = typeService.getType(id);
        model.addAttribute("type",type);
        return "admin/types-input";
    }

    @ApiOperation("删除分类")
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg","删除成功");
        return "redirect:/admin/types";
    }

}
