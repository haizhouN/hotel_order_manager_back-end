package com.njit.orderManager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njit.orderManager.domain.Order;
import com.njit.orderManager.mapper.OrderMapper;
import com.njit.orderManager.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


}

