package com.mapper;

import com.domain.Usermenu;
import com.domain.Users;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UsersMapper extends Mapper<Users> {

    Users selectByWorknumOrUsername(@Param("users") Users users);

    //根据用户的id获取用户的菜单权限树
    List<Usermenu> selectUsersMenu(@Param("user") Users user);
}