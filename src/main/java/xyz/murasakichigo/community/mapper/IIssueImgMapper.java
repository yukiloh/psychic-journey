package xyz.murasakichigo.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IIssueImgMapper {

    @Insert("insert into issue_img_table (issue_id,issue_addr) value (#{issueID},#{issue_addr})")
    void createIssueImgAddr(Integer issueID, String issue_addr);

    @Select("select issue_addr from issue_img_table where issue_id = #{issue_id} ")
    String findIssueImgById(Integer issue_id);

    @Update("UPDATE issue_img_table  SET isDeleted = 1 WHERE issue_id = #{id}")
    void markDeletedImg(String id);
}
