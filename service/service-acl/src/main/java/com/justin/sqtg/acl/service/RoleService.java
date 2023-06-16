package com.justin.sqtg.acl.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.justin.ssyx.model.acl.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.vo.acl.RoleQueryVo;

import java.util.Map;

public interface RoleService extends IService<Role> {
    IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo);

    //根据用户获取角色
    Map<String, Object> findRoleByUserId(Long adminId);

    //添加用户角色
    void saveAdminRole(Long adminId, Long[] roleId);
}
