package com.ws.hotelsearch.service;

import com.ws.hotelsearch.dto.req.CreateHotelReqDto;
import com.ws.hotelsearch.dto.resp.CreateHotelRespDto;
import com.ws.hotelsearch.dto.resp.SearchFetchHotelRespDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public interface HotelService {

    CreateHotelRespDto addHotel(CreateHotelReqDto hotelDto);

    List<String> addPhotos(UUID id, List<MultipartFile> photos);

    void setCover(UUID id, String name);

    List<SearchFetchHotelRespDto> getAllHotels();

//    List<SearchFetchHotelRespDto> getAllHotels();
}
