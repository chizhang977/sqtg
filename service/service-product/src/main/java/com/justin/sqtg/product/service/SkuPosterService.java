package com.justin.sqtg.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.model.product.SkuPoster;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务类
 * </p>
 *
 * @author justin
 * @since 2023-06-18
 */
public interface SkuPosterService extends IService<SkuPoster> {

    List<SkuPoster> getPosterListBySkuId(Long id);
}
