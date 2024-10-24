package com.glimmer.mapper;

import com.glimmer.entity.Alert;
import com.glimmer.vo.GetAlertVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * mapper层接口，用于与数据库进行交互
 */
@Mapper
public interface AlertTransactionMapper {


    /*
     * 查询报警视频信息
     * @param pathVideo
     * @return
     */
    @Select("select * from alert where path_video = #{pathVideo}")
    Alert getByVideoPath(String pathVideo);
    //添加报警信息图片
    @Insert("insert into alert (id,ca_id,alert_time,type,path_photo) values (#{id},#{caId},#{alertTime},#{type},#{pathPhoto})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertAlertPhoto(Alert alert);
    //添加报警视频
    @Insert("insert into alert (id,ca_id,alert_time,type,path_photo) values (#{id},#{caId},#{alertTime},#{type},#{pathPhoto})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertAlertVideo(Alert alert);
    @Update("update alert set path_video=#{pathVideo} where ca_id=#{caId}")
    void updateAlertVideo(String pathVideo, Integer caId);
    /*
     * 查询报警图片信息
     * @param pathPhoto
     * @return
     */
    @Select("select * from alert where path_photo = #{pathPhoto}")
    Alert getByPhotoPath(String pathPhoto);

    @Select("select ca_id,alert_time,type,path_video,path_photo from alert where ca_id=#{caId}")
    List<GetAlertVO.AlertVO> getAlertByCaId(Integer caId);
    @Select("select path_photo,path_video from alert where ca_id=#{caId}")
    List<String> getPathPhotoByCaId(Integer caId);


    /*
     * 删除相应报警视频
     * @param pathVideo
     */
    @Delete("delete from alert where path_video = #{pathVideo}")
    void deleteByVideoPath(String pathVideo);

    /*
     *删除相应报警图片
     * @param pathPhoto
     */
    @Delete("delete from alert where path_photo = #{pathPhoto}")
    void deleteByPhotoPath(String pathPhoto);

    //其他
}
