package xyz.murasakichigo.community.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import xyz.murasakichigo.community.dto.CommunityQuestion;

import java.util.List;
/*ctrl+alt+O: 自动移除无用的import*/


/*mybatis的mapper*/
@Mapper
@Component
public interface IQuestionMapper {

    @Select("SELECT * FROM quest_table")
    List<CommunityQuestion> findAll();

    @Insert("insert into quest_table (title,description,gmt_create,gmt_modified,author_user_id,comment_count,view_count,like_count,tag,author_name) value (#{title},#{description},#{gmt_create},#{gmt_modified},#{author_user_id},#{comment_count},#{view_count},#{like_count},#{tag},#{author_name})")
    void createIssue(CommunityQuestion question);

    /*升降序：order by field  + asc升序/desc降序 */
    @Select("select * from quest_table order by id desc limit #{page},10")
    List<CommunityQuestion> findQuestionByPage(Integer page);

    @Select("SELECT COUNT(id) FROM quest_table")
    Integer countQuestion();


    /*通过用户id查找*/
    @Select("select * from quest_table where author_user_id = #{id} order by id desc limit #{page},10")
    List<CommunityQuestion> findQuestionById(int id,int page);

    @Select("SELECT COUNT(id) FROM quest_table where author_user_id = #{id}")
    Integer countProfileQuestion(Integer id);

    /*通过问题id查找*/
    @Select("select * from quest_table where id = #{id} ")
    CommunityQuestion findQuestionByIssueId(String id);

    @Update("UPDATE `springboot_community_project`.`quest_table` t SET t.`title` = #{title},t.`description` = #{description},t.`gmt_modified` = #{gmt_modified} WHERE t.`id` = #{id}")
    void updateQuestion(CommunityQuestion question);

    @Update("UPDATE `springboot_community_project`.`quest_table` t SET t.`view_count` = #{view_count} WHERE t.`id` = #{id}")
    void updateQuestionView(int view_count, String id);

    @Select("select * from quest_table t where t.title like '%' #{keyword} '%'")
    List<CommunityQuestion> findKeyword(String keyword);
}


