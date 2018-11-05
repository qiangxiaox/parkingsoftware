package com.jk.miaosha.dao;

import com.jk.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName : IUserDAO
 * @Author : xiaoqiang
 * @Date : 2018/11/2 14:34:34
 * @Description :
 * @Version ： 1.0
 */
@Mapper
public interface IUserDAO {

    @Select("SELECT * from miaosha_user WHERE id = #{id}")
    public MiaoshaUser getById(@Param("id") long id);

}
