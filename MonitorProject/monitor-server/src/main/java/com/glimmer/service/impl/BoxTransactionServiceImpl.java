package com.glimmer.service.impl;

import com.glimmer.constant.MessageConstant;
import com.glimmer.dto.AddBoxDTO;
import com.glimmer.dto.BoxDTO;
import com.glimmer.dto.GetBoxDTO;
import com.glimmer.exception.BoxExistException;
import com.glimmer.exception.FormatException;
import com.glimmer.exception.GetnullInfoException;
import com.glimmer.mapper.BoxTransactionMapper;
import com.glimmer.service.BoxTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service层检测框接口的实现类
 * 在这里完成检测框相关的具体的业务功能
 */
@Service
public class BoxTransactionServiceImpl implements BoxTransactionService {


    private final BoxTransactionMapper boxTransactionMapper;
    @Autowired
    public BoxTransactionServiceImpl(BoxTransactionMapper boxTransactionMapper) {
        this.boxTransactionMapper = boxTransactionMapper;
    }
    /*
     * 添加检测框
     * @param addBoxDTO
     */
    @Override
    public void addBox(AddBoxDTO addBoxDTO) {
        Integer caId=addBoxDTO.getCaId();
        String leftUp= addBoxDTO.getLeftUp();
        String rightDown= addBoxDTO.getRightDown();//获取addBoxDTO相关参数(caId、rightDown、leftUp)




        if(caId==0||leftUp==null||rightDown==null){
            throw new GetnullInfoException("传入信息为空");
        }//校验caId、leftUp、rightDown是否为空，为空的话返回参数格式错误


        int checkResult=boxTransactionMapper.checkBoxExits(caId,leftUp,rightDown);
        if(checkResult>0){
            throw new BoxExistException("对应的检测框已存在");
        }//校验caId与leftUP 和 rightDown的组合是否已经在数据库的box表里面，防止重复添加，这里应该是caId和leftUp和rightDown的组合不能重复
        //校验数据库里面是否已经有了这个数据


        boxTransactionMapper.insertBox(caId,leftUp,rightDown);//没有则向数据库添加检测框数据

    }

    /*
     * 删除检测框业务
     * @param boxDTO
     */
    @Override
    public void deleteBox(BoxDTO boxDTO) {
        //获取相关参数，判空
        Integer caId = boxDTO.getCaId();
        if (caId == null) throw new FormatException(MessageConstant.FORMAT_ERROR);
        //删除box.CaId对应的box数据
        boxTransactionMapper.deleteByCaId(caId);
    }

    /*
     * 获取检测框业务
     * @param boxDTO
     * @return
     */
    @Override
    public String getBox(BoxDTO boxDTO) {
        Integer caId = boxDTO.getCaId();
        if (caId == null) throw new FormatException(MessageConstant.FORMAT_ERROR);
        List<GetBoxDTO> getBoxDTOs=boxTransactionMapper.getByCaId(caId);
        StringBuilder getBoxResult=new StringBuilder();
        for(GetBoxDTO getBoxDTO:getBoxDTOs){
            getBoxResult.append(getBoxDTO.getLeftUP()).append(" , ").append(getBoxDTO.getRightDown()).append("|");

        }
        return getBoxResult.toString();
    }



}
