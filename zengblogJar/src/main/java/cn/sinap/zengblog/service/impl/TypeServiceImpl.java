package cn.sinap.zengblog.service.impl;

import cn.sinap.zengblog.dao.TypeDao;
import cn.sinap.zengblog.domain.Type;
import cn.sinap.zengblog.service.TypeService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Transactional
    @Override
    public void saveType(Type type) {
         typeDao.saveType(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeDao.getType(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Transactional
    @Override
    public List<Type> getAllType() {
        return typeDao.getAllType();
    }

    @Transactional
    @Override
    public List<Type> getBlogType() {
        return typeDao.getBlogType();
    }

    @Transactional
    @Override
    public void updateType(Type type) {
        typeDao.updateType(type);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeDao.deleteType(id);
    }
}
