package xyz.murasakichigo.community.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import xyz.murasakichigo.community.model.CommunityIssue;

import java.util.List;
/*ctrl+alt+O: 自动移除无用的import*/



@Repository
@Mapper     /*mybatis的mapper*/
public interface IIssueMapper {

    @Select("SELECT * FROM issue_table")
    List<CommunityIssue> findAll();

    @Insert("insert into issue_table (title,description,gmt_create,gmt_modified,author_user_id,comment_count,view_count,like_count,tag,author_name) value (#{title},#{description},#{gmt_create},#{gmt_modified},#{author_user_id},#{comment_count},#{view_count},#{like_count},#{tag},#{author_name})")
    void createIssue(CommunityIssue issue);

    /*升降序：order by field  + asc升序/desc降序 */
    @Select("select * from issue_table order by id desc limit #{page},10")
    List<CommunityIssue> findIssueByPage(Integer page);

    @Select("SELECT COUNT(id) FROM issue_table")
    Integer countIssue();


    /*通过用户id查找*/
    @Select("select * from issue_table where author_user_id = #{id} order by id desc limit #{page},10")
    List<CommunityIssue> findIssueById(int id, int page);

    @Select("SELECT COUNT(id) FROM issue_table where author_user_id = #{id}")
    Integer countProfileIssue(Integer id);

    /*通过问题id查找*/
    @Select("select * from issue_table where id = #{id} ")
    CommunityIssue findIssueByIssueId(String id);

    @Update("UPDATE `springboot_community_project`.`issue_table` t SET t.`title` = #{title},t.`description` = #{description},t.`gmt_modified` = #{gmt_modified} WHERE t.`id` = #{id}")
    void updateIssue(CommunityIssue issue);

    @Update("UPDATE `springboot_community_project`.`issue_table` t SET t.`view_count` = #{view_count} WHERE t.`id` = #{id}")
    void updateIssueView(int view_count, String id);

    @Select("select * from issue_table t where t.title like '%' #{keyword} '%'")
    List<CommunityIssue> findKeyword(String keyword);

    @Select("SELECT COUNT(id) FROM issue_table t where t.title like '%' #{keyword} '%'")
    Integer countIssueByKeyword(String keyword);


    @Select("select max(id) from issue_table;")
    Integer findMaxIssueId();

    @Select("select * from issue_table t where t.title like '%' #{keyword} '%'  order by id desc limit #{page},10")
    List<CommunityIssue> findIssueByKeyword(String keyword, int page);
}


