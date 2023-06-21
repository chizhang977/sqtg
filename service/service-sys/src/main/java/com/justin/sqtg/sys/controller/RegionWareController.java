package com.justin.sqtg.sys.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.justin.sqtg.common.result.Result;
import com.justin.sqtg.sys.service.RegionWareService;
import com.justin.ssyx.model.sys.RegionWare;
import com.justin.ssyx.vo.sys.RegionWareQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author justin
 * @since 2023-06-16
 */
@Api(tags = "地区仓库接口")
@CrossOrigin
@RestController
@RequestMapping("/admin/sys/regionWare")
public class RegionWareController {

    @Autowired
    private RegionWareService regionWareService;

    @ApiOperation("地区仓库列表")
    @GetMapping("{page}/{limit}")
    public Result getPageList(@PathVariable Long page,
                              @PathVariable Long limit,
                              RegionWareQueryVo regionWareQueryVo){
        Page<RegionWare> pageParam  = new Page<>(page,limit);
        IPage<RegionWare> pageInfo =  regionWareService.selectPage(pageParam,regionWareQueryVo);
        return Result.ok(pageInfo);
    }

    @ApiOperation("新增")
    @PostMapping("save")
    public Result save(@RequestBody RegionWare regionWare){
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }
    @ApiOperation("修改")
    @PostMapping("updateStatus/{id}/{status}")
    public Result update(@PathVariable Long id,@PathVariable Integer status){

        regionWareService.updateRegionWare(id,status);
        return Result.ok(null);
    }
    @ApiOperation("删除")
    @DeleteMapping("remove/{id}")
    public Result delete(@PathVariable Long id){
        regionWareService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result removeRows(@RequestBody List<Long> idList){
        regionWareService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public Result getRegionWareById(@PathVariable Long id){
        RegionWare regionWare = regionWareService.getById(id);
        return Result.ok(regionWare);
    }



}

