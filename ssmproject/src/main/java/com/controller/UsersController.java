package com.controller;

import com.domain.Usermenu;
import com.domain.Users;
import com.service.UsersService;
import com.service.UsersServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户相关的操作
 */
@Controller
public class UsersController {
    @Resource(name="usersServiceImpl")
    UsersService usersService;

    /**
     * 用户登陆
     * @return
     */
    @RequestMapping("/userlogin")
    public String usersLogin(HttpServletRequest request,Users users){
        users.setUsername(users.getWorknum());//用户名和工号是相同
        Users users1 = usersService.selectByWorknumOrUsername(users);//根据用户名和密码 或工号和密码进行验证是否存在
        if (users1!=null){//登陆成功
            request.getSession().setAttribute("user",users1);//把当前登陆的用户对象保存到session作用域里面，后面其他的请求都能够获取当前登陆的这个用户
            return "redirect:/userMain";//进入到后台的主菜单操作界面
        }else {//登陆失败
            return "redirect:/jsp/userlogin.jsp";
        }
    }

    /**
     * 登陆成功后，查询动态显示用户的后台主菜单页面
     * @return
     */
    @RequestMapping("/userMain")
    public String userMain(HttpServletRequest request){
        Users user = (Users) request.getSession().getAttribute("user");//获取当前登陆的用户对象
        List<Usermenu> usermenuList = usersService.selectUsersMenu(user);//根据用户的id获取用户的菜单权限树
        request.setAttribute("usermenuList",usermenuList);
        return "usermain";
    }
}
