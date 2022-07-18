package com.ws.hotelsearch.dto.resp;

import com.ws.hotelsearch.dto.AbstractDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateRoomRespDto implements AbstractDto {
    private UUID id;
    private Boolean canSmoke;
    private Boolean isAvailable;
    private BigDecimal price;
}
