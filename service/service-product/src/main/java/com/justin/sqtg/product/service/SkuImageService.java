package com.justin.sqtg.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.model.product.SkuImage;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author justin
 * @since 2023-06-18
 */
public interface SkuImageService extends IService<SkuImage> {
    //
    List<SkuImage> getImageListBySkuId(Long id);
}
