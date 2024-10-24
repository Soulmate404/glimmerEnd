package com.glimmer.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 获取报警信息数据传输类
 */
@Data
public class GetAlertDTO implements Serializable {
    private String startDate;
    private String endDate;
    String[] caId;
    String[] type;
}
