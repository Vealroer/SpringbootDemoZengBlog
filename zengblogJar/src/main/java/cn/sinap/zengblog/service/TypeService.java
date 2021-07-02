package cn.sinap.zengblog.service;

import cn.sinap.zengblog.domain.Type;
import com.github.pagehelper.Page;

import java.util.List;

public interface TypeService {

    void saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    List<Type> getAllType();

    List<Type> getBlogType(); //展示博客的类型

    void updateType(Type type);

    void deleteType(Long id);


}
