package com.hamed.microserviceproduct.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document("Product")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String price;
}
