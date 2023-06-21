package com.justin.sqtg.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.justin.sqtg.common.result.Result;
import com.justin.sqtg.sys.service.WareService;
import com.justin.ssyx.model.sys.Region;
import com.justin.ssyx.model.sys.Ware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author justin
 * @since 2023-06-16
 */
@RestController
@RequestMapping("/admin/sys/ware")
@Api(tags = "仓库接口")
@CrossOrigin
public class WareController {
    @Autowired
    private WareService wareService;

    @ApiOperation("分页带条件")
    @GetMapping("{page}/{limit}")
    public Result getPageList(@PathVariable Long page, @PathVariable Long limit,Ware ware){
      Page<Ware> pageParam = new Page<>(page,limit);
        IPage<Ware> regionIPage = wareService.selectPage(pageParam, ware);
        return Result.ok(regionIPage);
    }

    @ApiOperation("根据id获取仓库")
    @GetMapping("get/{id}")
    public Result getWareById(@PathVariable Long id){
        Ware ware = wareService.getById(id);
        return Result.ok(ware);
    }

    @ApiOperation("新增")
    @PostMapping("save")
    public Result save(@RequestBody Ware ware){
        wareService.save(ware);
        return Result.ok(null);
    }

    @ApiOperation("修改")
    @PutMapping("update")
    public Result update(@RequestBody Ware ware){
        wareService.updateById(ware);
        return Result.ok(null);
    }

    @ApiOperation("删除")
    @DeleteMapping("remove/{id}")
    public Result delete(@PathVariable Long id){
        wareService.removeById(id);
        return Result.ok(null);

    }



    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        wareService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation("查询所有仓库列表")
    @GetMapping("findAllList")
    public Result findAllList() {
        List<Ware> list = wareService.list();
        return Result.ok(list);
    }
}

