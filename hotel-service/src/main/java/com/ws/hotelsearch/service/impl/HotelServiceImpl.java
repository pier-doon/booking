package com.ws.hotelsearch.service.impl;

import com.ws.hotelsearch.dto.req.CreateHotelReqDto;
import com.ws.hotelsearch.dto.resp.CreateHotelRespDto;
import com.ws.hotelsearch.dto.resp.SearchFetchHotelRespDto;
import com.ws.hotelsearch.exception.NoSuchHotelException;
import com.ws.hotelsearch.exception.NoSuchPhotoException;
import com.ws.hotelsearch.mapper.req.CreateHotelReqMapper;
import com.ws.hotelsearch.mapper.resp.CreateHotelRespMapper;
import com.ws.hotelsearch.mapper.resp.SearchFetchHotelRespMapper;
import com.ws.hotelsearch.model.Hotel;
import com.ws.hotelsearch.repository.HotelRepository;
import com.ws.hotelsearch.service.FileStorageService;
import com.ws.hotelsearch.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final FileStorageService storageService;
    private final CreateHotelReqMapper createHotelReqMapper;
    private final CreateHotelRespMapper createHotelRespMapper;
    private final SearchFetchHotelRespMapper searchFetchHotelRespMapper;

    @Override
    public List<SearchFetchHotelRespDto> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return searchFetchHotelRespMapper.toDto(hotels);
    }

    @Override
    public CreateHotelRespDto addHotel(CreateHotelReqDto hotelDto) {
        Hotel hotel = createHotelReqMapper.toEntity(hotelDto);

        Hotel hotelFromDb = hotelRepository.save(hotel);

        return createHotelRespMapper.toDto(hotelFromDb);
    }

    @Override
    public List<String> addPhotos(UUID id, List<MultipartFile> photos) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(id);
        Hotel hotel = hotelOpt.orElseThrow(() -> new NoSuchHotelException("Cannot find hotel with this id."));

        List<String> uploadedPhoto = storageService.uploadImages(photos);
        if (Objects.isNull(hotel.getPhotos())) {
            hotel.setPhotos(Collections.emptyList());
        }
        hotel.getPhotos().addAll(uploadedPhoto);
        if (Objects.isNull(hotel.getCover()) && !uploadedPhoto.isEmpty()) {
            setCover(hotel, uploadedPhoto.get(0));
        }
        hotelRepository.save(hotel);

        return uploadedPhoto;
    }

    @Override
    public void setCover(UUID id, String name) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(id);
        Hotel hotel = hotelOpt.orElseThrow(() -> new NoSuchHotelException("Cannot find hotel with id: " + id));
        setCover(hotel, name);
        hotelRepository.save(hotel);
    }

    private void setCover(Hotel hotel, String name) {
        if (storageService.isImageExist(name) && hotel.getPhotos().contains(name)) {
            hotel.setCover(name);
        } else {
            throw new NoSuchPhotoException("No such photo in hotel with id: " + hotel.getId());
        }
    }


}
