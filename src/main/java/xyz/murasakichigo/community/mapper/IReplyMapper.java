package xyz.murasakichigo.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.murasakichigo.community.model.CommunityReply;

import java.util.List;
@Repository
@Mapper
public interface IReplyMapper {

    @Select("select * from reply_table")
    List<CommunityReply> findAll();

    @Select("select rt.*,ut.username from reply_table rt,user_table ut where parent_id = #{id} and rt.critic_id = ut.id order by rt.reply_id desc")
    List<CommunityReply> findReplyByIssueId(String id);

    @Select("select rt.critic_id from reply_table rt where rt.reply_id = #{id}")
    Integer findCriticIdByReplyId(String id);

    @Insert("insert into reply_table (parent_id,reply_description,critic_id,gmt_reply_create) value(#{parent_id},#{reply_description},#{critic_id},#{gmt_reply_create})")
    void createReply(CommunityReply communityReply);

    @Delete("DELETE FROM reply_table WHERE reply_id = #{id}")
    void deleteReply(String id);

    /*根据parent_id删除回复*/
    @Delete("DELETE FROM reply_table WHERE parent_id = #{id}")
    void deleteReplyByParentId(String id);


}
