package com.example.Weather.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {
    
    private String cod;
    private int message;
    private int cnt;
    private List<ForecastItem> list;
    private City city;
    
    public String getCod() { return cod; }
    public void setCod(String cod) { this.cod = cod; }
    
    public int getMessage() { return message; }
    public void setMessage(int message) { this.message = message; }
    
    public int getCnt() { return cnt; }
    public void setCnt(int cnt) { this.cnt = cnt; }
    
    public List<ForecastItem> getList() { return list; }
    public void setList(List<ForecastItem> list) { this.list = list; }
    
    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
    
    public static class ForecastItem {
        private long dt;
        private Main main;
        private List<Weather> weather;
        private Clouds clouds;
        private Wind wind;
        private int visibility;
        private double pop;
        private Rain rain;
        private Snow snow;
        private Sys sys;
        @JsonProperty("dt_txt")
        private String dtTxt;
        
        public long getDt() { return dt; }
        public void setDt(long dt) { this.dt = dt; }
        
        public Main getMain() { return main; }
        public void setMain(Main main) { this.main = main; }
        
        public List<Weather> getWeather() { return weather; }
        public void setWeather(List<Weather> weather) { this.weather = weather; }
        
        public Clouds getClouds() { return clouds; }
        public void setClouds(Clouds clouds) { this.clouds = clouds; }
        
        public Wind getWind() { return wind; }
        public void setWind(Wind wind) { this.wind = wind; }
        
        public int getVisibility() { return visibility; }
        public void setVisibility(int visibility) { this.visibility = visibility; }
        
        public double getPop() { return pop; }
        public void setPop(double pop) { this.pop = pop; }
        
        public Rain getRain() { return rain; }
        public void setRain(Rain rain) { this.rain = rain; }
        
        public Snow getSnow() { return snow; }
        public void setSnow(Snow snow) { this.snow = snow; }
        
        public Sys getSys() { return sys; }
        public void setSys(Sys sys) { this.sys = sys; }
        
        public String getDtTxt() { return dtTxt; }
        public void setDtTxt(String dtTxt) { this.dtTxt = dtTxt; }
    }
    
    public static class Main {
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        @JsonProperty("temp_min")
        private double tempMin;
        @JsonProperty("temp_max")
        private double tempMax;
        private int pressure;
        @JsonProperty("sea_level")
        private int seaLevel;
        @JsonProperty("grnd_level")
        private int grndLevel;
        private int humidity;
        @JsonProperty("temp_kf")
        private double tempKf;
        
        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }
        
        public double getFeelsLike() { return feelsLike; }
        public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }
        
        public double getTempMin() { return tempMin; }
        public void setTempMin(double tempMin) { this.tempMin = tempMin; }
        
        public double getTempMax() { return tempMax; }
        public void setTempMax(double tempMax) { this.tempMax = tempMax; }
        
        public int getPressure() { return pressure; }
        public void setPressure(int pressure) { this.pressure = pressure; }
        
        public int getSeaLevel() { return seaLevel; }
        public void setSeaLevel(int seaLevel) { this.seaLevel = seaLevel; }
        
        public int getGrndLevel() { return grndLevel; }
        public void setGrndLevel(int grndLevel) { this.grndLevel = grndLevel; }
        
        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }
        
        public double getTempKf() { return tempKf; }
        public void setTempKf(double tempKf) { this.tempKf = tempKf; }
    }
    
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;
        
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        
        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }
    
    public static class Clouds {
        private int all;
        
        public int getAll() { return all; }
        public void setAll(int all) { this.all = all; }
    }
    
    public static class Wind {
        private double speed;
        private int deg;
        private Double gust;
        
        public double getSpeed() { return speed; }
        public void setSpeed(double speed) { this.speed = speed; }
        
        public int getDeg() { return deg; }
        public void setDeg(int deg) { this.deg = deg; }
        
        public Double getGust() { return gust; }
        public void setGust(Double gust) { this.gust = gust; }
    }
    
    public static class Rain {
        @JsonProperty("3h")
        private double threeH;
        
        public double getThreeH() { return threeH; }
        public void setThreeH(double threeH) { this.threeH = threeH; }
    }
    
    public static class Snow {
        @JsonProperty("3h")
        private double threeH;
        
        public double getThreeH() { return threeH; }
        public void setThreeH(double threeH) { this.threeH = threeH; }
    }
    
    public static class Sys {
        private String pod;
        
        public String getPod() { return pod; }
        public void setPod(String pod) { this.pod = pod; }
    }
    
    public static class City {
        private long id;
        private String name;
        private Coord coord;
        private String country;
        private int population;
        private int timezone;
        private long sunrise;
        private long sunset;
        
        public long getId() { return id; }
        public void setId(long id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Coord getCoord() { return coord; }
        public void setCoord(Coord coord) { this.coord = coord; }
        
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        
        public int getPopulation() { return population; }
        public void setPopulation(int population) { this.population = population; }
        
        public int getTimezone() { return timezone; }
        public void setTimezone(int timezone) { this.timezone = timezone; }
        
        public long getSunrise() { return sunrise; }
        public void setSunrise(long sunrise) { this.sunrise = sunrise; }
        
        public long getSunset() { return sunset; }
        public void setSunset(long sunset) { this.sunset = sunset; }
    }
    
    public static class Coord {
        private double lat;
        private double lon;
        
        public double getLat() { return lat; }
        public void setLat(double lat) { this.lat = lat; }
        
        public double getLon() { return lon; }
        public void setLon(double lon) { this.lon = lon; }
    }
}






