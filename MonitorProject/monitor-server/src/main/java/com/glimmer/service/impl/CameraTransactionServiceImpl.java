package com.glimmer.service.impl;

import com.glimmer.dto.AddCameraDTO;
import com.glimmer.dto.DeleteCameraDTO;
import com.glimmer.dto.GetCameraDTO;
import com.glimmer.dto.UpdateCameraDTO;
import com.glimmer.exception.CameraExistException;
import com.glimmer.exception.CameraNotFoundException;
import com.glimmer.exception.GetnullInfoException;
import com.glimmer.mapper.CameraTransactionMapper;
import com.glimmer.result.Result;
import com.glimmer.service.CameraTransactionService;
import com.glimmer.vo.GetCameraVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * service层摄像头接口的实现类
 * 在这里完成摄像头相关的具体的业务功能
 */
@Service
public class CameraTransactionServiceImpl implements CameraTransactionService {

    //依赖注入Mapper层相关接口
    private final CameraTransactionMapper cameraTransactionMapper;
@Autowired
public CameraTransactionServiceImpl(CameraTransactionMapper cameraTransactionMapper) {
    this.cameraTransactionMapper = cameraTransactionMapper;
}
    /*
     * 添加摄像头业务功能
     *
     * @param addCameraDTO
     */
    @Override
    public void AddCamera(AddCameraDTO addCameraDTO) {
        /*
        对addCameraDTO进行参数处理：
        1.这里的Channel字段需要从Integer类型转为Long类型
        2.这里对于参数的startTime和endTime需要特殊处理,因为他们传入的是如"8:00"这样的字符串,需要转为一个以纳秒为单位的时间戳对应的int64大小的值
        3.这里对于inferClass字段也需要进行特殊处理，将其String数组类型转为String类型的json字符串
         */
        long startTime = convertTimeToTimestamp(addCameraDTO.getStartTime());
        long endTime = convertTimeToTimestamp(addCameraDTO.getEndTime());
        String inferClass= Arrays.toString(addCameraDTO.getInferClass());
        long channel=addCameraDTO.getChannel();




        int check=cameraTransactionMapper.checkCameraExist(addCameraDTO.getName());
        if(check<0){
            throw new CameraExistException("摄像头已存在");
        }


        //camera的数据库里面name是唯一的，所以我们需要先判断该摄像头是否已经存在
        //包括异常处理


        cameraTransactionMapper.insertCamera(addCameraDTO.getName(),addCameraDTO.getIp(),addCameraDTO.getPort(),addCameraDTO.getUser(),addCameraDTO.getPasswd(),
                channel,addCameraDTO.getArea(),startTime,endTime,inferClass);//构造摄像头对象,注意检查要填入所有请求参数


        //调用mapper层接口插入数据


    }

    /*
     * 根据名称查询摄像头数据
     *
     * @param getCameraDTO
     * @return 返回摄像头数据的urls列
     */
    @Override
    public GetCameraVO.Url[] GetCamera(GetCameraDTO getCameraDTO)  {
        if(getCameraDTO.getCam_names()[0]==null){
            throw new GetnullInfoException("未接收到要查询的摄像头名称");
        }/*对参数进行处理，查询数据
        判定参数是否为空,不为空才进行处理(注意要将startTime、endTime转为响应的时间格式字符串)
        */
        if("all".equals(getCameraDTO.getCam_names()[0])){
            return cameraTransactionMapper.getAllCamera();
        }//先判断name字段数值是否为"all"，是就返回所有摄像头数据
        else{
            List<GetCameraVO.Url> urls = new ArrayList<>();

            for (String cameraName : getCameraDTO.getCam_names()) {
                urls.add(cameraTransactionMapper.getCameraByName(cameraName));



            }
            return urls.toArray(new GetCameraVO.Url[0]);
        }/*
          将查询到的每一个摄像头信息封装在一个类中，
          包括ip,port,user,passwd,channel,name,caId,startTime,endTime
        */







        //如果为空则返回所有摄像头的url，也就是与names[0] == "all"相同






    }

    /*
     * 修改摄像头数据
     *
     * @param updateCameraDTO
     */
    @Override
    public void updateCamera(UpdateCameraDTO updateCameraDTO) {
        int checkCameraExist = cameraTransactionMapper.checkCameraExist(updateCameraDTO.getName());
        if(checkCameraExist<=0){
            throw new CameraNotFoundException("未发现对应的摄像头");
        }//先根据传入名称判断是否存在该摄像头数据,不存在则抛出异常进行处理



        String name= updateCameraDTO.getName();
        String startTime= String.valueOf(convertTimeToTimestamp(updateCameraDTO.getStartTime()));
        String endTime=String.valueOf(convertTimeToTimestamp(updateCameraDTO.getEndTime()));
        cameraTransactionMapper.updateCamera(name,startTime,endTime);//存在则处理相关参数，将时间转换为可存储的时间戳形式






        //修改更新Camera数据对象




        //调用mapper接口进行数据的更新


    }

    /*
     * 删除摄像头
     *
     * @param deleteCameraDTO
     */
    @Override
    public void deleteCamera(DeleteCameraDTO deleteCameraDTO) {
       if(deleteCameraDTO==null){
           throw new GetnullInfoException("传入的参数为空");

       } //先判断传入的参数是否为空


        cameraTransactionMapper.deleteCamera(deleteCameraDTO.getName());//调用mapper层接口进行删除


    }
    public long convertTimeToTimestamp(String date) {
        // 假设时间字符串的格式是 "HH:mm"
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // 解析时间字符串为 LocalTime 对象
        LocalTime localTime = LocalTime.parse(date, timeFormatter);

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 将日期和时间结合，形成 LocalDateTime 对象
        LocalDateTime localDateTime = LocalDateTime.of(currentDate, localTime);

        // 将 LocalDateTime 转换为特定时区的 ZonedDateTime 对象
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        // 将 ZonedDateTime 转换为 UTC 时间的 Instant 对象
        Instant instant = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toInstant();

        // 将 Instant 对象转换为纳秒时间戳
        return instant.toEpochMilli() * 1_000_000;
    }
    }




