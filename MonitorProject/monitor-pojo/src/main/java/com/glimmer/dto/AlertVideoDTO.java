package com.glimmer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertVideoDTO {
    private MultipartFile alertVideo;
    private Integer caId;
    private Integer alertTime;
    private String alertType;
}
