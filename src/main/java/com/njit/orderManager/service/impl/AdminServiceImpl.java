package com.njit.orderManager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njit.orderManager.domain.Admin;
import com.njit.orderManager.mapper.AdminMapper;
import com.njit.orderManager.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {


}

