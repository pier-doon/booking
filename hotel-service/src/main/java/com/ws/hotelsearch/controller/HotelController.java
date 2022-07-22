package com.ws.hotelsearch.controller;

import com.ws.hotelsearch.dto.req.CreateHotelReqDto;
import com.ws.hotelsearch.dto.resp.CreateHotelRespDto;
import com.ws.hotelsearch.dto.resp.SearchFetchHotelRespDto;
import com.ws.hotelsearch.exception.EmptyPhotoRequestException;
import com.ws.hotelsearch.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "Create hotel in db without uploading photo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added hotel in db", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not available", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CreateHotelRespDto> addHotel(@RequestBody CreateHotelReqDto reqDto) {
        CreateHotelRespDto hotelResponse = hotelService.addHotel(reqDto);
        return ResponseEntity.ok().body(hotelResponse);
    }

    @PutMapping(value = "/{id}/image/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<String>> addPhotosToHotel(@PathVariable UUID id, @RequestPart List<MultipartFile> photos) {
        if (!photos.isEmpty()) {
            List<String> photoNames = hotelService.addPhotos(id, photos);
            return ResponseEntity.ok().body(photoNames);
        } else {
            throw new EmptyPhotoRequestException("Images not attached to request.");
        }
    }

    @PutMapping("/{id}/image/cover/{name}")
    public ResponseEntity<String> setCoverForHotel(@PathVariable UUID id, @PathVariable String name) {
        hotelService.setCover(id, name);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SearchFetchHotelRespDto>> getAllHotels() {
        System.out.println("oioiio");
        List<SearchFetchHotelRespDto> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok().body(hotels);
    }
}
