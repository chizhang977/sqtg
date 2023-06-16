package com.justin.sqtg.acl.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.justin.ssyx.model.acl.Role;
import com.justin.ssyx.vo.acl.AdminQueryVo;
import com.justin.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.justin.sqtg.acl.service.RoleService;
import com.justin.sqtg.common.result.Result;
import com.mysql.cj.log.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/admin/acl/role")
@CrossOrigin
public class RoleController {
    @Autowired
    private RoleService roleService;

    //1、角色列表
    @ApiOperation("获取角色列表带条件")
    @GetMapping("{page}/{limit}")
    public Result getRoleList(
            @PathVariable Long page,
            @PathVariable Long limit,
            RoleQueryVo roleQueryVo
    ){
        Page<Role> pageParam = new Page<>(page,limit);
        IPage<Role> roleIPage = roleService.selectPage(pageParam, roleQueryVo);
        return Result.ok(roleIPage);
    }
    //2、根据id查角色
    @ApiOperation("根据id获取角色")
    @GetMapping("{id}")
    public Result getRoleById(@PathVariable Long id){
        return Result.ok(roleService.getById(id));
    }
    //3、添加
    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result save(@RequestBody Role role){
         roleService.save(role);
         return Result.ok(null);
    }
    //4、修改
    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result update(@RequestBody Role role){
        roleService.updateById(role);
        return Result.ok(null);
    }

    //5、id删除
    @ApiOperation("删除角色")
    @DeleteMapping("remove/{id}")
    public Result delete(@PathVariable Long id){
        roleService.removeById(id);
        return Result.ok(null);
    }

    //6.批量删除角色
    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result deleteByIds(@RequestBody List<Long> ids){
        roleService.removeByIds(ids);
        return Result.ok(null);
    }


}
