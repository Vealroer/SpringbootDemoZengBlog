package cn.sinap.zengblog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogAndTag {
    private Long tagId;

    private Long blogId;

}
