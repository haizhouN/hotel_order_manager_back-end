package com.njit.orderManager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njit.orderManager.domain.User;
import com.njit.orderManager.mapper.UserMapper;
import com.njit.orderManager.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

