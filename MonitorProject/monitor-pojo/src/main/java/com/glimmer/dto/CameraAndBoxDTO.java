package com.glimmer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CameraAndBoxDTO {
    private Integer caID;
    private String startTime;
    private String endTime;
    private String inferClass;
    private String box;
}
