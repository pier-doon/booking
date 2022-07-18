package com.ws.hotelsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "hotel")
@AllArgsConstructor
@NoArgsConstructor
public class Hotel implements AbstractEntity {

    @Id
    private UUID id;
    private String name;
    private String description;
    private String address;

    //    @DBRef(lazy = true)
    private List<Room> rooms;
    private String cover;
    private List<String> photos;
}
