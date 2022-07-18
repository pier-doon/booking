package com.ws.hotelsearch.dto.req;

import com.ws.hotelsearch.dto.AbstractDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateHotelReqDto implements AbstractDto {

    @NotNull
    private String name;
    private String description;
    @NotNull
    private String address;
    @NotNull
    private List<CreateRoomReqDto> rooms;
}
