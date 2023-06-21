package com.justin.sqtg.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.model.sys.Region;
import com.justin.ssyx.model.sys.Ware;
import com.justin.ssyx.vo.sys.RegionVo;

/**
 * <p>
 * 仓库表 服务类
 * </p>
 *
 * @author justin
 * @since 2023-06-16
 */
public interface WareService extends IService<Ware> {

    //条件带分页
    IPage<Ware> selectPage(Page<Ware> pageParam, Ware ware);
}
