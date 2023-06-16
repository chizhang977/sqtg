package com.justin.sqtg.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justin.sqtg.acl.mapper.PermissionMapper;
import com.justin.sqtg.acl.service.PermissionService;
import com.justin.sqtg.acl.service.RolePermissionService;
import com.justin.sqtg.acl.utils.PermissionHelper;
import com.justin.ssyx.model.acl.Permission;
import com.justin.ssyx.model.acl.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    /*
     获取权限(菜单/功能)列表
  */
    @Override
    public List<Permission> getAllPermissionMenu() {
        //1、首先应该获取pid = 0数据 的id，之后获取pid = id 的数据，则为二级菜单
        //获取全部权限数据
        List<Permission> allPermissionList = baseMapper.selectList(null);
        List<Permission> result = PermissionHelper.buildPermission(allPermissionList);
        return result;
    }

    /*
        删除一个权限项
    */
    @Override
    public void removePermission(Long id) {
        //封装索要删除的菜单id
        List<Long> idList = new ArrayList<>();

        //根据此id，获取下级列表(下下级列表)
        //采用递归
        this.getAllPermissionId(id,idList);
        idList.add(id);

        //批量删除
        baseMapper.deleteBatchIds(idList);
    }

    //查看某个角色的权限列表
    @Override
    public Map<String,Object> selectAssign(Long roleId) {
        //查看所有的权限列表
        List<Permission> permissionList = baseMapper.selectList(null);
        //根据角色id查出角色分配权限列表
        //根据角色id查出 角色权限管理表role_permission 查询角色分配权限id列表
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId,roleId);
        List<RolePermission> rolePermissions = rolePermissionService.list(wrapper);

        //通过第一步返回集合，获取所有权限列表List<RolePermission>---->List<Long>
        List<Long>  permissionIdsList
                = rolePermissions.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        //创建新的list集合，用户存储角色配置权限
        List<Permission> assignPermissionList = new ArrayList<>();
        //遍历所有权限列表，得到每个角色
        //判断所有角色里面是否包含已经分配的权限，封装到新的list集合中
        for (Permission permission : permissionList){
            if (permissionIdsList.contains(permission.getId())){
                assignPermissionList.add(permission);
            }
        }
        Map<String,Object> result = new HashMap<>();
        result.put("allPermissions",permissionList);
        result.put("assignPermissions",assignPermissionList);


        return result;
    }

    //递归查询当前菜单下面的所有子菜单
    private void getAllPermissionId(Long id, List<Long> idList) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPid,id);
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        //使用递归继续查询
        permissionList.stream().forEach(item -> {
            //封装菜单id到idList集合里面
            idList.add(item.getId());
            //递归
            this.getAllPermissionId(item.getId(),idList);
        });
    }
}
