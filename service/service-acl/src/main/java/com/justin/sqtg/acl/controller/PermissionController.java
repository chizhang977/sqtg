package com.justin.sqtg.acl.controller;

import com.justin.sqtg.acl.service.PermissionService;
import com.justin.sqtg.common.result.Result;
import com.justin.ssyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "权限接口")
@RequestMapping("/admin/acl/permission")
@RestController
@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /*
      获取权限(菜单/功能)列表
      */
    @ApiOperation("获取权限列表")
    @GetMapping
    public Result getPermissionList(){
        List<Permission>  permissionList = permissionService.getAllPermissionMenu();
        return Result.ok(permissionList);
    }

    /*
    保存一个权限项
    */
    @ApiOperation("保存权限")
    @PostMapping("save")
    public Result addPermission(@RequestBody Permission permission){
        permissionService.save(permission);
        return Result.ok(null);
    }

      /*
    更新一个权限项
    */

    @ApiOperation("更新菜单")
    @PutMapping("update")
    public Result  updatePermission(@RequestBody Permission permission){

        permissionService.updateById(permission);
        return Result.ok(null);
    }
    /*
    删除一个权限项
    */
    @ApiOperation("删除")
    @DeleteMapping("remove/{id}")
    public Result removePermission(@PathVariable Long id){
        permissionService.removePermission(id);
        return Result.ok(null);
    }

    @ApiOperation("查看某个角色的权限列表")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        Map<String,Object> maps =  permissionService.selectAssign(roleId);
        return Result.ok(maps);
    }



    /*
    给某个角色授权
    *//*
    doAssign(roleId, permissionId) {
        return request({
                url: `${api_name}/doAssign`,
        method: "post",
                params: {roleId, permissionId}
    })
    }*/

}
