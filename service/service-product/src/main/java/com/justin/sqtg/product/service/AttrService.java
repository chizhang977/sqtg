package com.justin.sqtg.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.justin.ssyx.model.product.Attr;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author justin
 * @since 2023-06-18
 */
public interface AttrService extends IService<Attr> {

    List<Attr> getAttrList(Long groupId);
}
