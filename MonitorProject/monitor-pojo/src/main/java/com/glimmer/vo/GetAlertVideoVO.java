package com.glimmer.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAlertVideoVO {
    private Integer status;
    private String message;
    private List<ResponseEntity<byte[]>> video;
}
