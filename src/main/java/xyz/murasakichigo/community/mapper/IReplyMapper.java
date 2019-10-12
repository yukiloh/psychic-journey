package xyz.murasakichigo.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import xyz.murasakichigo.community.dto.ReplyDTO;

import java.util.List;
@Repository
@Mapper
public interface IReplyMapper {

    @Select("select * from reply_table")
    List<ReplyDTO> findAll();

    @Select("select rt.*,ut.username from reply_table rt,user_table ut where parent_id = #{id} and rt.critic_id = ut.id order by rt.reply_id desc")
    List<ReplyDTO> findReplyByIssueId(String id);

    @Insert("insert into reply_table (parent_id,reply_description,critic_id,gmt_reply_create) value(#{parent_id},#{reply_description},#{critic_id},#{gmt_reply_create})")
    void createReply(ReplyDTO replyDTO);

}
