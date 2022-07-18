package com.ws.hotelsearch.dto.req;

import com.ws.hotelsearch.dto.AbstractDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateRoomReqDto implements AbstractDto {
    private Boolean canSmoke;
    private Boolean isAvailable;
    private BigDecimal price;
}
