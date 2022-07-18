package com.ws.hotelsearch.controller;

import com.ws.hotelsearch.dto.req.CreateHotelReqDto;
import com.ws.hotelsearch.dto.resp.CreateHotelRespDto;
import com.ws.hotelsearch.dto.resp.SearchFetchHotelRespDto;
import com.ws.hotelsearch.exception.EmptyPhotoRequestException;
import com.ws.hotelsearch.service.HotelService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
@ApiResponse(description = "Api for work with hotels")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<CreateHotelRespDto> addHotel(@RequestBody CreateHotelReqDto reqDto) {
        CreateHotelRespDto hotelResponse = hotelService.addHotel(reqDto);
        return ResponseEntity.ok().body(hotelResponse);
    }
//TODO return photos ids that recently added
    @PutMapping(value = "{id}/image/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<String>> addPhotosToHotel(@PathVariable UUID id, @RequestPart List<MultipartFile> photos) {
        if (!photos.isEmpty()) {
            List<String> photoNames = hotelService.addPhotos(id, photos);
            return ResponseEntity.ok().body(photoNames);
        } else {
            throw new EmptyPhotoRequestException("Images not attached to request.");
        }
    }

    @PutMapping("{id}/image/cover/{name}")
    public ResponseEntity<String> setCoverForHotel(@PathVariable UUID id, @PathVariable String name) {
        hotelService.setCover(id, name);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<SearchFetchHotelRespDto>> getAllHotels() {
        List<SearchFetchHotelRespDto> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok().body(hotels);
    }
}
