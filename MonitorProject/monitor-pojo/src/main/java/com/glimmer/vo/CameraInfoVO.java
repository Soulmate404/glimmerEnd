package com.glimmer.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CameraInfoVO implements Serializable {
private Integer status;
private String msg;
private CameraInfo[] cameraInfos;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public static class CameraInfo {
    private Integer caId;
    private String startTime;
    private String endTime;
    private String inferClass;
    private String box;
}
}
