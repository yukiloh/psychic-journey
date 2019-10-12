package xyz.murasakichigo.community.mapper;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import xyz.murasakichigo.community.dto.CommunityUser;

import java.util.List;
/*ctrl+alt+O: 自动移除无用的import*/

@Repository
@Mapper
public interface IUserMapper {

    @Select("SELECT * FROM user_table")
    List<CommunityUser> findAll();

    @Select("SELECT * FROM user_table where github_account_id =#{id}")
    CommunityUser findUserByGithub_account_id(String id);

    @Select("SELECT * FROM user_table where id =#{id}")
    CommunityUser findUserById(Integer id);

    @Select("SELECT * FROM user_table where token = #{token}")
    CommunityUser findUserByToken(String token);


    @Insert("insert into user_table (username,github_account_id,token,gmt_create,avatar_url) value (#{username},#{github_account_id},#{token},#{gmt_create},#{avatar_url})")
    void createUser(CommunityUser user);

    @Update("update user_table set username = #{username},github_account_id = #{github_account_id},token = #{token},gmt_modified = #{gmt_modified},gmt_last_login = #{gmt_last_login},avatar_url = #{avatar_url} where id = #{id}")
    void updateUser(CommunityUser user);

    @Delete("delete from user_table where id = #{id}")
    void deleteUserById(Integer id);


}
