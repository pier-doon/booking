package com.ws.hotelsearch.mapper.req;

import com.ws.hotelsearch.dto.req.CreateHotelReqDto;
import com.ws.hotelsearch.mapper.AbstractMapper;
import com.ws.hotelsearch.model.Hotel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class CreateHotelReqMapper extends AbstractMapper<Hotel, CreateHotelReqDto> {

    private final CreateRoomReqMapper roomReqMapper;

    public CreateHotelReqMapper(CreateRoomReqMapper roomReqMapper) {
        super(Hotel.class, CreateHotelReqDto.class);
        this.roomReqMapper = roomReqMapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(CreateHotelReqDto.class, Hotel.class)
                .addMappings(m -> m.skip(Hotel::setId))
                .addMappings(m -> m.skip(Hotel::setRooms))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(CreateHotelReqDto source, Hotel destination) {
        destination.setId(UUID.randomUUID());
        destination.setRooms(roomReqMapper.toEntity(source.getRooms()));
    }
}
