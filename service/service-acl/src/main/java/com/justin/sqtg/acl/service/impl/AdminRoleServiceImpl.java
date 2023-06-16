package com.justin.sqtg.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.justin.sqtg.acl.mapper.AdminRoleMapper;
import com.justin.sqtg.acl.service.AdminRoleService;
import com.justin.ssyx.model.acl.AdminRole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {

}

