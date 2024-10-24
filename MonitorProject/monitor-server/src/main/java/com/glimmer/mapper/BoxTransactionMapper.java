package com.glimmer.mapper;

import com.glimmer.dto.BoxDTO;
import com.glimmer.dto.GetBoxDTO;
import com.glimmer.entity.Box;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * mapper层接口，用于与数据库进行交互
 */
@Mapper
public interface BoxTransactionMapper {

    //检查对应数据框是否存在
    @Select("select count(*) from box where ca_id=#{caId} and left_up=#{leftUp} and right_down=#{rightDown}")
    int checkBoxExits(@Param("caId")Integer caId, @Param("leftUp")String leftUp, @Param("rightDown")String rightDown);



    /*
     * 插入检测框数据
     * @param
     */
    @Insert("INSERT INTO box(ca_id, left_up, right_down) VALUES (#{caId}, #{leftUp}, #{rightDown})")

    void insertBox(@Param("caId") Integer caId,
                   @Param("leftUp") String leftUp,
                   @Param("rightDown") String rightDown);



    /*
     * 根据caId删除检测框数据
     * @param caId
     */
    @Delete("delete from box where ca_id = #{caId}")
    void deleteByCaId(Integer caId);

    /*
     * 根据caId获取检测框数据
     * @param caId
     * @return
     */
    @Select("select * from box where ca_id = #{caId}")
    List<GetBoxDTO> getByCaId(Integer caId);
    @Select("select * from box where ca_id=#{caId}")
    List<GetBoxDTO> getBoxByCaId(@Param("caId") Integer caId);

}
