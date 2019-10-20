package xyz.murasakichigo.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IIpMapper {

    @Select("select count from ip_table where ip = #{ip}")
    Integer findCountByIp(String ip);

    @Insert("insert into ip_table (ip,count,gmt_first_access) value (#{ipAddr},1,#{date})")
    void createIp(String ipAddr,String date);

    @Update("UPDATE ip_table SET count = #{maxCount} WHERE ip = #{ipAddr}")
    void updateIp(String ipAddr, Integer maxCount);
}
