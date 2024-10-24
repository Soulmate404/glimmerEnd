package com.glimmer.dto;

import lombok.Data;

@Data
public class AlertVideoAndPhotoDTO {
    private String startDate;
    private String endDate;
    String[] caId;
    String[] type;
}
