package com.ws.hotelsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class City {
    @Id
    private Long id;
    private String name;

    @DBRef(lazy = true)
    private Country country;
}
