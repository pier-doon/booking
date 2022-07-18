package com.ws.hotelsearch.mapper.req;

import com.ws.hotelsearch.dto.req.CreateRoomReqDto;
import com.ws.hotelsearch.mapper.AbstractMapper;
import com.ws.hotelsearch.model.Room;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class CreateRoomReqMapper extends AbstractMapper<Room, CreateRoomReqDto> {
    public CreateRoomReqMapper() {
        super(Room.class, CreateRoomReqDto.class);
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(CreateRoomReqDto.class, Room.class)
                .addMappings(m -> m.skip(Room::setId))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(CreateRoomReqDto source, Room destination) {
        destination.setId(UUID.randomUUID());
    }
}
