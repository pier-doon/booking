package com.ws.hotelsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Room implements AbstractEntity {

    @Id
    private UUID id;

    private Boolean canSmoke;

    private Boolean isAvailable;

    private BigDecimal price;
}
