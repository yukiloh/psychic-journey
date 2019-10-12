package xyz.murasakichigo.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IIpMapper {

    @Select("select count from ip_table where ip = #{ip}")
    Integer findCountByIp(String ip);

    @Insert("insert into ip_table (ip,count) value (#{ipAddr},1)")
    void createIp(String ipAddr);

    @Update("UPDATE ip_table SET count = #{maxCount} WHERE ip = #{ipAddr}")
    void updateIp(String ipAddr, Integer maxCount);
}
