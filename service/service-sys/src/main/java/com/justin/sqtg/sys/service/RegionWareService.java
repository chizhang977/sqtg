package com.justin.sqtg.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.model.sys.RegionWare;
import com.justin.ssyx.vo.sys.RegionWareQueryVo;

/**
 * <p>
 * 城市仓库关联表 服务类
 * </p>
 *
 * @author justin
 * @since 2023-06-16
 */
public interface RegionWareService extends IService<RegionWare> {

//    地区仓库列表
    IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);
    //新增
    void saveRegionWare(RegionWare regionWare);
    //修改
    void updateRegionWare(Long id, Integer status);
}
