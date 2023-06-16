package com.justin.sqtg.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.justin.sqtg.acl.service.AdminRoleService;
import com.justin.ssyx.model.acl.Admin;
import com.justin.ssyx.model.acl.AdminRole;
import com.justin.ssyx.model.acl.Role;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justin.sqtg.acl.mapper.RoleMapper;
import com.justin.sqtg.acl.service.RoleService;
import com.justin.ssyx.vo.acl.RoleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private AdminRoleService adminRoleService;


    //角色分页列表
    @Override
    public IPage<Role> selectPage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {
        String roleName = roleQueryVo.getRoleName();
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper();
        if (!StringUtils.isEmpty(roleName)){
            wrapper.like(Role::getRoleName,roleName);
        }
        Page page = baseMapper.selectPage(pageParam, wrapper);
        return page;
    }

    //根据用户获取角色
    @Override
    public Map<String, Object> findRoleByUserId(Long adminId) {
        //查询所有的角色列表
        //SELECT id,role_name,remark,create_time,update_time,is_deleted FROM role WHERE is_deleted=0
        List<Role> allRolesList = baseMapper.selectList(null);

        //根据用户id查询已经分配的角色列表
        //TODO select r.role_name from role r join admin_role ar on r.id = ar.role_id where ar.admin_id = 1;
        //2、1 用户的角色id
        //SELECT role_id FROM admin_role WHERE is_deleted=0 AND (admin_id = ?)
        List<AdminRole> existUserRoleList = adminRoleService.list(new QueryWrapper<AdminRole>().eq("admin_id", adminId).select("role_id"));
        List<Long> existRoleList = existUserRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());

        //2.2对角色进行分类
        List<Role> assignRoles = new ArrayList<>();
        for (Role role:allRolesList) {
            //已分配
            if (existRoleList.contains(role.getId())){
                assignRoles.add(role);
            }
        }
        Map<String,Object> roleMap = new HashMap<>();
        roleMap.put("allRolesList",allRolesList);
        roleMap.put("assignRoles",assignRoles);
        return roleMap;
    }

    //添加用户角色
    @Override
    public void saveAdminRole(Long adminId, Long[] roleId) {
        //删除用户角色关系
        adminRoleService.remove(new LambdaQueryWrapper<AdminRole>().eq(AdminRole::getAdminId,adminId));
        //重新建立用户角色
        List<AdminRole> adminRoleList = new ArrayList<>();
        for (Long roleid : roleId){
            AdminRole adminRole = new AdminRole();
            adminRole.setRoleId(roleid);
            adminRole.setAdminId(adminId);
            adminRoleList.add(adminRole);
        }
        adminRoleService.saveBatch(adminRoleList);
    }
}
