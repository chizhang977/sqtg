package com.justin.sqtg.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justin.sqtg.acl.mapper.RolePermissionMapper;
import com.justin.sqtg.acl.service.RolePermissionService;
import com.justin.ssyx.model.acl.RolePermission;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}
