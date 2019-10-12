package xyz.murasakichigo.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.murasakichigo.community.dto.CommunityQuestion;

@Repository
@Mapper
public interface IQuestionImgMapper {

    @Insert("insert into question_img_table (question_id,question_addr) value (#{questionID},#{question_addr})")
    void createQuestionImgAddr(Integer questionID,String question_addr);

    @Select("select question_addr from question_img_table where question_id = #{question_id} ")
    String findQuestionImgById(Integer question_id);
}
