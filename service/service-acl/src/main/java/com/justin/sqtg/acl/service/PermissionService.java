package com.justin.sqtg.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.model.acl.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends IService<Permission> {
    /*
  获取权限(菜单/功能)列表
  */
    List<Permission> getAllPermissionMenu();

    /*
  删除一个权限项
  */
    void removePermission(Long id);

    //查看某个角色的权限列表
    Map<String,Object> selectAssign(Long roleId);
}
