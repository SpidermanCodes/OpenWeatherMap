package com.example.Weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UVIndexResponse {
    
    private double lat;
    private double lon;
    @JsonProperty("date_iso")
    private String dateIso;
    private long date;
    private double value;
    
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }
    
    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }
    
    public String getDateIso() { return dateIso; }
    public void setDateIso(String dateIso) { this.dateIso = dateIso; }
    
    public long getDate() { return date; }
    public void setDate(long date) { this.date = date; }
    
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}






