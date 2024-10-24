package com.glimmer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBoxDTO {
    private Integer id;
    private Integer caId;
    private String rightDown;
    private String leftUP;
}
