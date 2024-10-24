package com.glimmer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CameraInfoDTO {
    private Integer caId;
    private String startTime;
    private String endTime;
    private String inferClass;
}
