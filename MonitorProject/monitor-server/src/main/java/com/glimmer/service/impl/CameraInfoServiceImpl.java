package com.glimmer.service.impl;

import com.glimmer.dto.CameraInfoDTO;
import com.glimmer.entity.Box;
import com.glimmer.entity.Camera;
import com.glimmer.mapper.BoxTransactionMapper;
import com.glimmer.mapper.CameraTransactionMapper;
import com.glimmer.service.CameraInfoService;
import com.glimmer.vo.CameraInfoVO;
import com.glimmer.vo.InfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * service层摄像头监测信息接口的实现类
 * 在这里完成摄像头相关的具体的业务功能
 */
@Service
public class CameraInfoServiceImpl implements CameraInfoService {

    //依赖注入Mapper层相关接口

    private final CameraTransactionMapper cameraTransactionMapper;
@Autowired
public CameraInfoServiceImpl(CameraTransactionMapper cameraTransactionMapper) {
    this.cameraTransactionMapper = cameraTransactionMapper;
}
   @Autowired
    private BoxTransactionMapper boxTransactionMapper;
public CameraInfoVO.CameraInfo[] getAllCameraInfo() {
    List<CameraInfoDTO> cameraInfoDTOS=cameraTransactionMapper.getCameraInfoList();
    List<CameraInfoVO.CameraInfo> cameraInfoVOS=new ArrayList<CameraInfoVO.CameraInfo>();
    for (CameraInfoDTO cameraInfoDTO : cameraInfoDTOS) {
        CameraInfoVO.CameraInfo cameraInfo=CameraInfoVO.CameraInfo.builder()
                .caId(cameraInfoDTO.getCaId())
                .inferClass(cameraInfoDTO.getInferClass())
                .box(boxTransactionMapper.getBoxByCaId(cameraInfoDTO.getCaId()).toString())
                .startTime(convertTimerStampToTime(cameraInfoDTO.getStartTime()))
                .endTime(convertTimerStampToTime(cameraInfoDTO.getEndTime()))
                .build();
        cameraInfoVOS.add(cameraInfo);
    }
    return cameraInfoVOS.toArray(new CameraInfoVO.CameraInfo[0]);


}

public String convertTimerStampToTime(String timerStamp) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date(Long.parseLong(timerStamp));
    return sdf.format(date);
}
    //请你根据相应数据格式实现摄像头检测区域信息获取业务

}
