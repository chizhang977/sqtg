package com.justin.sqtg.acl.controller;

import com.justin.sqtg.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "登录接口")
@RequestMapping("/admin/acl/index")
@RestController
@CrossOrigin
public class IndexController {

    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(){
        Map<String,String> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map);
    }

    @ApiOperation("用户信息")
    @GetMapping("info")
    public Result info(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","justin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    @ApiOperation("退出")
    @PostMapping("logout")
    public Result logout(){
        return Result.ok(null);
    }

}
