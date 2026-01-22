package com.example.Weather.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "favorite_cities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteCity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String cityName;
    
    @Column(nullable = false)
    private String country;
    
    private Double latitude;
    private Double longitude;
    
    @Column(name = "created_at")
    private Long createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = System.currentTimeMillis();
    }
}






