package com.ws.hotelsearch.dto.resp;

import com.ws.hotelsearch.dto.AbstractDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class SearchFetchHotelRespDto implements AbstractDto {
    private UUID id;
    private String name;
    private String address;
    private Boolean hasSmokingApartments;
    private BigDecimal minimalPrice;
    private Integer availableRooms;
    private String coverUrl;
}
