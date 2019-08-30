package xyz.murasakichigo.community.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import xyz.murasakichigo.community.dto.CommunityUser;
import org.slf4j.MDC;
/*ctrl+alt+O: 自动移除无用的import*/

import java.util.List;

/*mybatis的mapper*/
@Mapper
@Component
public interface IUserMapper {

    @Select("SELECT t.* FROM PUBLIC.USER t")
    List<CommunityUser> findAll();
}
