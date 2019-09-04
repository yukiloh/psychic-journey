package xyz.murasakichigo.community.mapper;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.murasakichigo.community.dto.CommunityQuestion;
import xyz.murasakichigo.community.dto.CommunityUser;

import java.util.List;
/*ctrl+alt+O: 自动移除无用的import*/


/*mybatis的mapper*/
@Mapper
@Component
public interface IQuestionMapper {

    @Select("SELECT * FROM quest_table")
    List<CommunityQuestion> findAll();

    @Insert("insert into quest_table (title,description,gmt_create,gmt_modified,author_user_id,comment_count,view_count,like_count,tag) value (#{title},#{description},#{gmt_create},#{gmt_modified},#{author_user_id},#{comment_count},#{view_count},#{like_count},#{tag})")
    void createIssue(CommunityQuestion question);



}


