package com.example.Weather.repository;

import com.example.Weather.model.FavoriteCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteCityRepository extends JpaRepository<FavoriteCity, Long> {
    Optional<FavoriteCity> findByCityNameIgnoreCase(String cityName);
    boolean existsByCityNameIgnoreCase(String cityName);
}
