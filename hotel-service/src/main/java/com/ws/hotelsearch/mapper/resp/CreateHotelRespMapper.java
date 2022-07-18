package com.ws.hotelsearch.mapper.resp;

import com.ws.hotelsearch.dto.resp.CreateHotelRespDto;
import com.ws.hotelsearch.mapper.AbstractMapper;
import com.ws.hotelsearch.model.Hotel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CreateHotelRespMapper extends AbstractMapper<Hotel, CreateHotelRespDto> {

    private final CreateRoomRespMapper roomMapper;

    public CreateHotelRespMapper(CreateRoomRespMapper roomMapper) {
        super(Hotel.class, CreateHotelRespDto.class);
        this.roomMapper = roomMapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Hotel.class, CreateHotelRespDto.class)
                .addMappings(m -> m.skip(CreateHotelRespDto::setRooms))
                .setPostConverter(toDtoConverter());
    }

    @Override
    public void mapSpecificFields(Hotel source, CreateHotelRespDto destination) {
        destination.setRooms(roomMapper.toDto(source.getRooms()));
    }
}
