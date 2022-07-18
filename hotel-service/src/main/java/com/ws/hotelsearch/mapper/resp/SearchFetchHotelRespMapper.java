package com.ws.hotelsearch.mapper.resp;

import com.ws.hotelsearch.dto.resp.SearchFetchHotelRespDto;
import com.ws.hotelsearch.mapper.AbstractMapper;
import com.ws.hotelsearch.model.Hotel;
import com.ws.hotelsearch.model.Room;
import com.ws.hotelsearch.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SearchFetchHotelRespMapper extends AbstractMapper<Hotel, SearchFetchHotelRespDto> {

    private final FileStorageService storageService;

    @Autowired
    public SearchFetchHotelRespMapper(FileStorageService storageService) {
        super(Hotel.class, SearchFetchHotelRespDto.class);
        this.storageService = storageService;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Hotel.class, SearchFetchHotelRespDto.class)
                .addMappings(m -> m.skip(SearchFetchHotelRespDto::setAvailableRooms))
                .addMappings(m -> m.skip(SearchFetchHotelRespDto::setMinimalPrice))
                .addMappings(m -> m.skip(SearchFetchHotelRespDto::setHasSmokingApartments))
                .addMappings(m -> m.skip(SearchFetchHotelRespDto::setCoverUrl))
                .setPostConverter(toDtoConverter());
    }

    @Override
    public void mapSpecificFields(Hotel source, SearchFetchHotelRespDto destination) {
        String name = source.getCover();
        if (Objects.nonNull(name)) {
            String url = storageService.getDownloadUrl(name);
            destination.setCoverUrl(url);
        }
        List<Room> availableRooms = source.getRooms().stream()
                .filter(Room::getIsAvailable)
                .collect(Collectors.toList());
        BigDecimal minimalPrice = availableRooms.stream()
                .min(Comparator.comparing(Room::getPrice))
                .map(Room::getPrice)
                .orElse(null);

        boolean smokingApartment = availableRooms.stream().anyMatch(Room::getCanSmoke);


        destination.setHasSmokingApartments(smokingApartment);
        destination.setMinimalPrice(minimalPrice);
        destination.setAvailableRooms(availableRooms.size());
    }
}
