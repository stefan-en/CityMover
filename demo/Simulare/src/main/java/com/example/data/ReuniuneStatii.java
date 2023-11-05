package com.example.data;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document(collection = "reuniunestatii")
public class ReuniuneStatii {
    private String tripId;
    private List<String> statii;
}
