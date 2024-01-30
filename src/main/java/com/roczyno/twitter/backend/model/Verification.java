package com.roczyno.twitter.backend.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Verification {
    private boolean status=false;
    private LocalDateTime startsAt;
    private LocalDateTime endsTime;
    private String planType;
}
