package com.lia.system.mapper;


import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    /**
     * 查询角色列表
     * @param role 查询的参数信息
     * @return 角色列表
     */
    List<SysRole> findSysRole(SysRole role);


    /**
     * 新增
     * @param role
     * @return
     */
    int addSysRole(SysRole role);

    /**
     * 编辑
     * @param role
     * @return
     */
    int editSysRole(SysRole role);


    /**
     * 批量删除
     * @param roleIds 角色的id列表
     * @return 删除成功的数量
     */
    int deleteRoles(List<Integer> roleIds);


    /**
     * 删除角色的全部权限
     */
    int deletePowersOfRole(Integer roleId);


    /**
     * 给角色添加权限
     */
    int addPowersToRole(List<Integer> powerIds, Integer roleId);


    /**
     * 删除角色的全部路由
     */
    int deleteRoutersOfRole(Integer roleId);


    /**
     * 给角色添加路由
     */
    int addRoutersToRole(List<Integer> routerIds, Integer roleId);


}
