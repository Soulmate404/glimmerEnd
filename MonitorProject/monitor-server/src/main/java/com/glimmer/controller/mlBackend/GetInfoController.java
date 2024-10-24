package com.glimmer.controller.mlBackend;

import com.glimmer.constant.MessageConstant;
import com.glimmer.result.Result;
import com.glimmer.service.CameraInfoService;
import com.glimmer.service.CameraTransactionService;
import com.glimmer.vo.CameraInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
Controller控制层
getInfoController 这里是给后端返回所有摄像头的相关信息
当然检测框的信息不存在是正常的，因为用户可能没有划定检测框的范围
*/
@RestController
@RequestMapping("/backend/camera")
@Slf4j
public class GetInfoController {
    //依赖注入service接口
    @Autowired
    private CameraInfoService cameraInfoService;
    @Autowired
    private CameraTransactionService cameraTransactionService;
@GetMapping
    public Result<CameraInfoVO> getCameraInfo() {
        CameraInfoVO cameraInfoVO = CameraInfoVO.builder()
                .status(0)
                .msg(MessageConstant.SUCCESS)
                .cameraInfos(cameraInfoService.getAllCameraInfo())
                .build();
        return Result.success(cameraInfoVO,MessageConstant.SUCCESS);
    }

    /*
    GetInfo 这里是给后端返回所有摄像头的检测时间---startTime和endTime、摄像头的检测类型inferClass、摄像头的编号caId、摄像头caId对应的检测框的信息。
    每个信息都是data的一个键值对。
    status为0表示成功，为1表示失败，为9表示内部错误。
    message返回对应的错误描述。
    */
    //这部分需要你自己定义方法和返回类型,自己完成




}
