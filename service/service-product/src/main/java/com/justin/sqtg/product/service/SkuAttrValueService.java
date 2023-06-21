package com.justin.sqtg.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.model.product.SkuAttrValue;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author justin
 * @since 2023-06-18
 */
public interface SkuAttrValueService extends IService<SkuAttrValue> {

    List<SkuAttrValue> getAttrValueListBySkuId(Long id);
}
