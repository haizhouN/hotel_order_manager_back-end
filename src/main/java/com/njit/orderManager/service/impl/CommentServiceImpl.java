package com.njit.orderManager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njit.orderManager.domain.Comment;
import com.njit.orderManager.mapper.CommentMapper;
import com.njit.orderManager.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


}

