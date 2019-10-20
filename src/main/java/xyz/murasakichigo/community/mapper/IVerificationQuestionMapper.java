package xyz.murasakichigo.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.murasakichigo.community.model.VerificationQuestion;

@Mapper
@Repository
public interface IVerificationQuestionMapper {

    @Select("SELECT COUNT(id) FROM verification_table")
    Integer countVerificationQuestion();

    @Select("SELECT * FROM verification_table where id = #{id}")
    VerificationQuestion findVerificationQuestionById(Integer id);
}
