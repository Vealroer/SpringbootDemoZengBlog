package cn.sinap.zengblog.service.impl;

import cn.sinap.zengblog.dao.TagDao;
import cn.sinap.zengblog.domain.Tag;
import cn.sinap.zengblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public void saveTag(Tag tag) {
        tagDao.saveTag(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.getTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.getAllTag();
    }

    @Override
    public List<Tag> getBlogTag() {
        return tagDao.getBlogTag();
    }

    @Override
    public List<Tag> getTagByString(String text) {
        List<Tag> tags = new ArrayList<>();
        List<Long> longs = convertToList(text);
        for (Long aLong : longs) {
            tags.add(tagDao.getTag(aLong));
        }
        return tags;
    }

    public List<Long> convertToList(String ids){
        List<Long> longs = new ArrayList<>();
        if (!"".equals(ids) && ids!=null ){
            String[] split = ids.split(",");
            for (int i = 0; i < split.length; i++) {
                longs.add(Long.valueOf(split[i]));
            }
        }
        return longs;
    }

    @Override
    public void updateTag(Tag tag) {
        tagDao.updateTag(tag);
    }

    @Override
    public void deleteTag(Long id) {
        tagDao.deleteTag(id);
    }
}
