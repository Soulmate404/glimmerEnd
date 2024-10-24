package com.glimmer.mapper;

import com.glimmer.dto.CameraInfoDTO;
import com.glimmer.entity.Camera;
import com.glimmer.vo.GetCameraVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * mapper层接口，用于与数据库进行交互
 */
@Mapper
public interface CameraTransactionMapper {

    /*
     * 通过用户名获取摄像头信息
     * @param name
     * @return
     */
    @Select("SELECT Count(*) FROM camera WHERE name= #{name}")
    int checkCameraExist(String name);


    /*
     * 插入摄像头数据
     * @param camera
     */
    @Insert("INSERT INTO camera(name,ip,port,user,passwd,channel,area,start_time,end_time,infer_class) " +
            "VALUES (#{name},#{ip},#{port},#{user},#{passwd},#{channel},#{area},#{startTime},#{endTime},#{inferClass})")
    void insertCamera(@Param("name") String name,@Param("ip") String ip,@Param("port")String port,@Param("user") String user,@Param("passwd") String passwd,@Param("channel") long channel,@Param("area") String area,@Param("startTime") long startTime,@Param("endTime") long endTime,@Param("inferClass") String inferClass);


    @Delete("DELETE FROM camera WHERE name=#{name}")
    void deleteCamera(@Param("name") String name);


    /*
     * 查询返回所有摄像头数据
     * @return
     */
    @Select("select * from camera")
    GetCameraVO.Url[] getAllCamera();
    @Select("select * from camera where name =#{name}")
    GetCameraVO.Url getCameraByName(@Param("name") String name);
    @Update("UPDATE camera SET name=#{name},start_time=#{startTime},end_time=#{endTime}")
    void updateCamera(@Param("name") String name,@Param("startTime")String startTime,@Param("endTime") String endTime);
    @Select("select ca_id,start_time,end_time,infer_class from camera")
    List<CameraInfoDTO> getCameraInfoList();



    //根据名称查询摄像头

    /*
     * 动态修改摄像头数据
     * @param camera
     */


    /*
     * 根据名称删除摄像头
     * @param name
     */


}
