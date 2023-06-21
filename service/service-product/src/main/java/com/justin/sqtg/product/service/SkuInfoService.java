package com.justin.sqtg.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.model.product.SkuInfo;
import com.justin.ssyx.vo.product.SkuInfoQueryVo;
import com.justin.ssyx.vo.product.SkuInfoVo;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author justin
 * @since 2023-06-18
 */
public interface SkuInfoService extends IService<SkuInfo> {

    IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    //新增sku
    void saveSkuInfo(SkuInfoVo skuInfoVo);
    //获取sku信息
    SkuInfoVo getSkuInfo(Long id);
    //修改
    void udpateSkuInfo(SkuInfoVo skuInfoVo);
    //上架
    void publish(Long id, Integer status);
    //审核
    void check(Long id, Integer status);
    //新人专项
    void isNewPerson(Long id, Integer status);
}
