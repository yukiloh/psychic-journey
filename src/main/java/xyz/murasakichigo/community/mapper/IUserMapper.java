package xyz.murasakichigo.community.mapper;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.murasakichigo.community.dto.CommunityUser;
import java.util.List;
import org.slf4j.MDC;
/*ctrl+alt+O: 自动移除无用的import*/


/*mybatis的mapper*/
@Mapper
@Component
public interface IUserMapper {

    @Select("SELECT * FROM user_table")
    List<CommunityUser> findAll();

    @Select("SELECT * FROM user_table where github_account_id =#{id}")
    CommunityUser findUserByGithub_account_id(String id);

    @Select("SELECT * FROM user_table where token = #{token}")
    CommunityUser findUserByToken(String token);


    @Insert("insert into user_table (username,github_account_id,token,gmt_create) value (#{username},#{github_account_id},#{token},#{gmt_create})")
    void createUser(CommunityUser user);

    @Update("update user_table set username = #{username},github_account_id = #{github_account_id},token = #{token},gmt_modified = #{gmt_modified},gmt_last_login = #{gmt_last_login} where id = #{id}")
    void updateUser(CommunityUser user);

    @Delete("delete from user_table where id = #{id}")
    void deleteUserById(Integer id);


}
