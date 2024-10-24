package com.glimmer.service.impl;

import com.glimmer.constant.MessageConstant;
import com.glimmer.dto.*;
import com.glimmer.entity.Alert;
import com.glimmer.exception.AlertNotFoundException;
import com.glimmer.exception.BaseException;
import com.glimmer.mapper.AlertTransactionMapper;
import com.glimmer.service.AlertTransactionService;
import com.glimmer.vo.GetAlertVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * service层报警信息接口的实现类
 * 在这里完成与报警信息相关的具体的业务功能
 */
@Service
public class AlertTransactionServiceImpl implements AlertTransactionService {


    private final AlertTransactionMapper alertTransactionMapper;
    @Autowired
    public AlertTransactionServiceImpl(AlertTransactionMapper alertTransactionMapper) {
        this.alertTransactionMapper = alertTransactionMapper;
    }

    /*
     * 上传图片文件
     *
     * @param file
     * @param caId
     * @param alertTime
     * @param alertType
     */
    @Override
    public void uploadPhoto(AddAlertDTO addAlertDTO) {


        Alert alert = Alert.builder()
                .caId(addAlertDTO.getCaId())
                .alertTime(Long.valueOf(addAlertDTO.getAlertTime()))
                .type(addAlertDTO.getAlertType())
                .pathPhoto("E:\\javabackend-plus\\"+addAlertDTO.getAlertPhoto().getOriginalFilename())
                .build();
        alertTransactionMapper.insertAlertPhoto(alert);
        try {
            addAlertDTO.getAlertPhoto().transferTo(new File("E:\\javabackend-plus\\"+addAlertDTO.getAlertPhoto().getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /*
     * 上传视频文件
     *
     * @param file
     * @param caId
     * @param alertTime
     * @param alertType
     */
    @Override
    public void uploadVideo(AlertVideoDTO addAlertDTO) {

        alertTransactionMapper.updateAlertVideo("E:\\javabackend-plus\\"+addAlertDTO.getAlertVideo().getOriginalFilename(),addAlertDTO.getCaId());
        try {
            addAlertDTO.getAlertVideo().transferTo(new File("E:\\javabackend-plus\\"+addAlertDTO.getAlertVideo().getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
    @Override
    public List<GetAlertVO.AlertVO> getAlert(GetAlertDTO getAlertDTO) {
        if(getAlertDTO.getCaId()==null){
            throw new AlertNotFoundException("未发现对应的报警信息");
        }
        List<String> cameraCaId = Arrays.stream(getAlertDTO.getCaId()).toList();
        List<GetAlertVO.AlertVO> alertVOLists = new ArrayList<>();
        for(String caId : cameraCaId){
            List<GetAlertVO.AlertVO> alertVOList=alertTransactionMapper.getAlertByCaId(Integer.valueOf(caId));
            for (GetAlertVO.AlertVO alertVO : alertVOList) {

                alertVO.setPath_photo(String.valueOf(alertTransactionMapper.getPathPhotoByCaId(alertVO.getCaId())));
                alertVO.setPath_video(String.valueOf(alertTransactionMapper.getPathPhotoByCaId(alertVO.getCaId())));
            }


            alertVOLists.addAll(alertVOList);

        }
        return alertVOLists;




    }
    @Override

    public List<ResponseEntity<byte[]>> downloadFile(AlertVideoAndPhotoDTO alertVideoAndPhotoDTO){
        List<String> cameraCaId = Arrays.stream(alertVideoAndPhotoDTO.getCaId()).toList();
        List<String> path=new ArrayList<>();
        for(String caId : cameraCaId){
           List<String> videoPath=alertTransactionMapper.getPathPhotoByCaId(Integer.valueOf(caId));
           path.addAll(videoPath);
        }
        List<ResponseEntity<byte[]>> responseList=new ArrayList<>();
        for(String path1 : path){
            responseList.add(downloadVideo(path1));
        }
        return responseList;

    }

    /*
     * 获取报警信息
     */






    /*
     * 删除报警信息
     *
     * @param deleteAlertDTO
     */
    @Override
    public void deleteAlert(DeleteAlertDTO deleteAlertDTO) {
        //首先判断视频路径数组是否为空，如果不为空，那么就要删除视频文件和数据库中的记录
        String[] pathPhotos = deleteAlertDTO.getPathPhotos();
        String[] pathVideos = deleteAlertDTO.getPathVideos();
        if (pathVideos != null && pathVideos.length > 0) {
            //遍历视频路径数组，删除视频文件和数据库中的记录
            for (String pathVideo : pathVideos) {
                Alert alert = alertTransactionMapper.getByVideoPath(pathVideo);
                if (alert == null) throw new AlertNotFoundException("视频路径：" + MessageConstant.NOT_EXIST);
                alertTransactionMapper.deleteByVideoPath(pathVideo);
                File file = new File(pathVideo);
                if (!file.delete()) throw new BaseException(MessageConstant.UNKNOWN_ERROR);
            }
        }
        if (pathPhotos != null && pathPhotos.length > 0) {
            //遍历视频路径数组，删除视频文件和数据库中的记录
            for (String pathPhoto : pathPhotos) {
                Alert alert = alertTransactionMapper.getByPhotoPath(pathPhoto);
                if (alert == null)
                    throw new AlertNotFoundException("图片路径" + MessageConstant.NOT_EXIST + "或已删除");
                alertTransactionMapper.deleteByPhotoPath(pathPhoto);
                File file = new File(pathPhoto);
                if (!file.delete()) throw new BaseException(MessageConstant.UNKNOWN_ERROR);
            }
        }
    }
    public ResponseEntity<byte[]> downloadVideo(String path)  {
        File file = new File(path);
        try {
            InputStream in=new FileInputStream(file);
            byte[] fileContent = new byte[(in.available())];
            in.read(fileContent);
            in.close();
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getName()+"\"").contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileContent);
        }catch (IOException e){
            throw new RuntimeException(e);
        }




    }



}
