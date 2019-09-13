package xyz.murasakichigo.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import xyz.murasakichigo.community.dto.Reply;

import java.util.List;
@Component
@Mapper
public interface IReplyMapper {

    @Select("select * from reply_table")
    List<Reply> findAll();

    @Select("select * from reply_table where parent_id = #{id}")
    List<Reply> findReplyByIssueId(String id);
}
