package com.justin.sqtg.acl.utils;

import com.justin.ssyx.model.acl.Permission;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {

    public static List<Permission> buildPermission(List<Permission> allPermissionList) {
        //创建最终数据封装到List集合
        List<Permission> trees = new ArrayList<>();
        //遍历所有菜单list集合，得到第一层数据,pid = 0;
        for (Permission permission : allPermissionList){
            //pid = 0 数据，第一层数据
            if (permission.getPid() == 0){
                permission.setLevel(1);
                //调用方法,从第一层开始往下找
                trees.add(findChildren(permission,allPermissionList));
            }
        }
        return trees;
    }

    //递归往下找,找子节点
    //permission 上层节点
    private static Permission findChildren(Permission permission, List<Permission> allPermissionList) {
        //遍历所有菜单数据
        //判断：当前节点 id = pid是否一样，封装，递归往下找
        for (Permission it:allPermissionList) {
            if (permission.getId().longValue() == it.getPid().longValue()){
                int level = permission.getLevel() + 1;
                it.setLevel(level);
                if (permission.getChildren() == null){
                    permission.setChildren(new ArrayList<>());
                }
                //封装到下一层数据
                permission.getChildren().add(findChildren(it,allPermissionList));
            }
        }
        return permission;
    }
}
