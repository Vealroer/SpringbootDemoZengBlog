package cn.sinap.zengblog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;  //头像
    private Date createTime;
    private boolean adminComment;  //是否为管理员评论

    private Blog blog;

    private Long blogId;
    private Long parentCommentId;  //父评论id
    private String parentNickname;

    //回复的评论
    private List<Comment> replyComments = new ArrayList<>();
    //父评论
    private Comment parentComment;
}
