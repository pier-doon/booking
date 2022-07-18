package com.ws.hotelsearch.dto.resp;

import com.ws.hotelsearch.dto.AbstractDto;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateHotelRespDto  implements AbstractDto {
    private UUID id;
    private String name;
    private String description;
    private String address;
    private List<CreateRoomRespDto> rooms;
    private String cover;
    private List<String> photos;
}
