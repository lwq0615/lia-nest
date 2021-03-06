package com.lia.system.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysUser;
import com.lia.system.exception.HttpException;
import com.lia.system.security.LoginUser;
import com.lia.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    /**
     * 获取当前登录用户的信息
     */
    @GetMapping("/getInfo")
    public SysUser getInfo(){
        return LoginUser.getLoginUser();
    }


    /**
     * 用户登录，登录成功后返回加密的token字符串
     * 之后的请求携带token在header中校验身份
     * @param user 用户信息
     * @return token字符串
     */
    @PostMapping("/login")
    public String sysUserLogin(@RequestBody SysUser user){
        //判断是否合法用户
        if(user.getUsername() == null || user.getUsername().equals("")
                || user.getPassword() == null || user.getPassword().equals("")){
            return "less param";
        }
        return sysUserService.getAuthorization(user);
    }


    /**
     * 分页查询用户列表
     * @param user 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:user:getPage')")
    public PageInfo<SysUser> getSysUserPage(@RequestBody SysUser user, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysUserService.findSysUser(user));
    }


    /**
     * 新增和编辑用户
     * @param user 用户数据，每条数据如果有userId则为修改，userId为null则为新增
     * @return 操作成功的数量
     */
    @PostMapping("/saveUser")
    @PreAuthorize("hasAuthority('system:user:saveUser')")
    public String saveUser(@RequestBody SysUser user){
        if(user.getUsername() == null || user.getUsername().equals("")){
            throw new HttpException(400,"缺少参数username");
        }
        if(user.getNick() == null || user.getNick().equals("")){
            throw new HttpException(400,"缺少参数nick");
        }
        if(user.getRoleId() == null){
            throw new HttpException(400,"缺少参数roleId");
        }
        // 新增的用户必须要有password
        if(user.getUserId() == null && (user.getPassword() == null || user.getPassword().equals(""))){
            throw new HttpException(400,"缺少参数password");
        }
        // 新增的用户createBy为当前用户
        if(user.getUserId() == null){
            SysUser loginSysUser = LoginUser.getLoginUser();
            user.setCreateBy(loginSysUser.getUserId());
        }
        return sysUserService.saveUser(user);
    }


    /**
     * 批量删除用户
     * @param userIds 用户的id列表
     * @return 删除成功的数量
     */
    @PostMapping("/deleteUsers")
    @PreAuthorize("hasAuthority('system:user:deleteUsers')")
    public int deleteUsers(@RequestBody List<Integer> userIds){
        return sysUserService.deleteUsers(userIds);
    }


}
