package com.glimmer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddAlertDTO {
    private MultipartFile alertPhoto;
    private Integer caId;
    private Integer alertTime;
    private String alertType;
}
