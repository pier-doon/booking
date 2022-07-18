package com.ws.hotelsearch.mapper.resp;

import com.ws.hotelsearch.dto.resp.CreateRoomRespDto;
import com.ws.hotelsearch.mapper.AbstractMapper;
import com.ws.hotelsearch.model.Room;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomRespMapper extends AbstractMapper<Room, CreateRoomRespDto> {
    public CreateRoomRespMapper() {
        super(Room.class, CreateRoomRespDto.class);
    }
}
