package com.lia.system.service;


import com.lia.system.entity.SysDict;
import com.lia.system.entity.SysPower;
import com.lia.system.entity.SysRouter;
import com.lia.system.mapper.SysDictMapper;
import com.lia.system.mapper.SysPowerMapper;
import com.lia.system.mapper.SysRouterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SysDictService {


    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private SysRouterMapper sysRouterMapper;


    /**
     * 获取sys_dict字典表内的字典
     * @param type 字典类型
     */
    public List<SysDict> getSysDict(String type){
        return sysDictMapper.getSysDict(type);
    }


    /**
     * 获取角色字典表
     */
    public List<SysDict> getSysRoleDict(){
        return sysDictMapper.getSysRoleDict();
    }


    /**
     * 获取用户字典表
     */
    public List<SysDict> getSysUserDict() {
        return sysDictMapper.getSysUserDict();
    }
    /**
     * 获取权限字典表
     */
    public List<SysDict> getSysPowerDict() {
        return sysDictMapper.getSysPowerDict();
    }


}
