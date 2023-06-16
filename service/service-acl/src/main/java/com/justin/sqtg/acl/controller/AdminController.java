package com.justin.sqtg.acl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.justin.sqtg.acl.service.AdminRoleService;
import com.justin.sqtg.acl.service.AdminService;
import com.justin.sqtg.acl.service.RoleService;
import com.justin.sqtg.common.result.Result;
import com.justin.sqtg.common.utils.MD5;
import com.justin.ssyx.model.acl.Admin;
import com.justin.ssyx.vo.acl.AdminQueryVo;
import io.github.classgraph.utils.LogNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/admin/acl/user")
@CrossOrigin
public class AdminController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRoleService adminRoleService;

    //1、获取用户的列表
    @ApiOperation("查询用户列表")
    @GetMapping("{page}/{limit}")
    public Result getUsers(@PathVariable Long page, @PathVariable Long limit , AdminQueryVo userQuery){
        Page<Admin> pageParam = new Page<>(page,limit);
        IPage<Admin> iPage = adminService.selectPage(pageParam,userQuery);
        return Result.ok(iPage);
    }
    //2.根据id获取用户
    @ApiOperation("根据id获取用户")
    @GetMapping("get/{id}")
    public Result getUserById(@PathVariable Long id){
        return Result.ok(adminService.getById(id));
    }

    @ApiOperation("保存")
    @PostMapping("save")
    public Result save(@RequestBody Admin admin){
        //密码加密
        String password = admin.getPassword();
        String passwordMD5 = MD5.encrypt(password);
        admin.setPassword(passwordMD5);
        adminService.save(admin);
        return Result.ok(null);
    }
    @ApiOperation("修改")
    @PutMapping("update")
    public Result update(@RequestBody Admin admin){
        adminService.updateById(admin);
        return Result.ok(null);
    }
    @ApiOperation("删除")
    @DeleteMapping("remove/{id}")
    public Result removeById(@PathVariable Long id){
        adminService.removeById(id);
        return Result.ok(null);
    }
    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result remove(@RequestBody List<Long> ids){
        adminService.removeByIds(ids);
        return Result.ok(null);
    }



    @GetMapping("toAssign/{adminId}")
    @ApiOperation("根据用户获取角色")
    public Result getRoleByAdminId(@PathVariable Long adminId){
        Map<String, Object> roleMap = roleService.findRoleByUserId(adminId);
        return Result.ok(roleMap);
    }

    @PostMapping("doAssign")
    @ApiOperation("为用户分配角色")
    public Result doAssign(Long adminId,Long[] roleId){
        roleService.saveAdminRole(adminId,roleId);
        return Result.ok(null);
    }

}
