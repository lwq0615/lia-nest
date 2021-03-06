package com.lia.system.service;


import com.lia.system.entity.SysPower;
import com.lia.system.entity.SysRole;
import com.lia.system.mapper.SysPowerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysPowerService {


    @Autowired
    private SysPowerMapper sysPowerMapper;


    /**
     * 根据角色ID查询该角色拥有的权限
     *
     * @param roleId 角色ID
     * @return 权限集合
     */
    public List<SysPower> findSysPowerByRoleId(Integer roleId) {
        return sysPowerMapper.findSysPowerByRoleId(roleId);
    }


    /**
     * 查询权限列表
     *
     * @param power
     * @return
     */
    public List<SysPower> findSysPower(SysPower power) {
        return sysPowerMapper.findSysPower(power);
    }


    /**
     * 新增或编辑
     *
     * @param power
     * @return
     */
    public String savePower(SysPower power) {
        int success = 0;
        try {
            if (power.getPowerId() == null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = dateFormat.format(new Date());
                power.setCreateTime(date);
                success = sysPowerMapper.addSysPower(power);
            } else {
                success = sysPowerMapper.editSysPower(power);
            }
        } catch (DuplicateKeyException e) {
            String[] split = e.getCause().getMessage().split(" ");
            String replace = split[split.length - 1].replace("'", "");
            String name = replace.split("\\.")[1].split("-")[1];
            switch (name) {
                case "key":
                    return "重复的标识符";
                case "url":
                    return "重复的接口路径";
            }
        }
        return success > 0 ? "success" : "error";
    }


    /**
     * 批量删除
     *
     * @param powerIds id列表
     * @return 删除成功的数量
     */
    public int deletePowers(List<Integer> powerIds) {
        if (powerIds.size() == 0) {
            return 0;
        }
        return sysPowerMapper.deleteSysPowers(powerIds);
    }

}
