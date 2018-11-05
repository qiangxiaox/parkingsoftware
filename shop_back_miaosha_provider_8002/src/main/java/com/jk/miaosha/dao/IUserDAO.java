package com.jk.miaosha.dao;

import com.jk.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @ClassName : IUserDAO
 * @Author : xiaoqiang
 * @Date : 2018/11/2 14:34:34
 * @Description :
 * @Version ： 1.0
 */
@Mapper
public interface IUserDAO {

    /**
     * 根据用户ID获取用户
     * @param id
     * @return
     */
    @Select("SELECT * from miaosha_user WHERE id = #{id}")
    public MiaoshaUser getById(@Param("id") long id);

    /**
     * 修改用户密码，修改什么传什么，减少binlog
     * @param userId
     * @param userPass
     * @return
     */
    @Update("UPDATE miaosha_user SET password=#{userPass} WHERE id=#{userId}")
    public int doUpdateMiaoshaUserPassword(@Param("userId")long userId,@Param("userPass") String userPass);
}
