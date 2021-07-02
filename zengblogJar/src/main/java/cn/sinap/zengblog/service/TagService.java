package cn.sinap.zengblog.service;

import cn.sinap.zengblog.domain.Tag;

import java.util.List;

public interface TagService {
    void saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    List<Tag> getAllTag();

    List<Tag> getBlogTag();  //首页展示博客标签

    List<Tag> getTagByString(String text);   //从字符串中获取tag集合

    void updateTag(Tag tag);

    void deleteTag(Long id);
}
