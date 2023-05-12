package com.njit.orderManager.controller.admin;

import com.njit.orderManager.common.CommonResult;
import com.njit.orderManager.common.StatusCode;
import com.njit.orderManager.domain.Comment;
import com.njit.orderManager.domain.User;
import com.njit.orderManager.dto.ReturnCommentDTO;
import com.njit.orderManager.dto.ReturnUserDTO;
import com.njit.orderManager.service.CommentService;
import com.njit.orderManager.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RestController("adminCommentController")
@RequestMapping("/admin")
public class CommentController {

    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;

    @GetMapping("/listComment")
    public CommonResult<List<ReturnCommentDTO>> listComment() {
        CommonResult<List<ReturnCommentDTO>> commonResult = new CommonResult<>();
        List<ReturnCommentDTO> returnCommentList = new ArrayList<>();

        List<Comment> commentList = commentService.list();

        for (Comment comment : commentList) {
            ReturnCommentDTO commentDTO = new ReturnCommentDTO();
            User user = userService.getById(comment.getUserId());
            ReturnUserDTO returnUserDTO = new ReturnUserDTO();
            BeanUtils.copyProperties(user, returnUserDTO);

            commentDTO.setComment(comment);
            commentDTO.setUser(returnUserDTO);

            returnCommentList.add(commentDTO);
        }

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData(returnCommentList);

        return commonResult;
    }
}
