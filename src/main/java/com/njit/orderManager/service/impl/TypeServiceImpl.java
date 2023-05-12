package com.njit.orderManager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njit.orderManager.domain.Type;
import com.njit.orderManager.mapper.TypeMapper;
import com.njit.orderManager.service.TypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {


}

