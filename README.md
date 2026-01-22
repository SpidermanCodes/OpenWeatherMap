# Advanced Weather Dashboard - OpenWeatherMap Spring Boot Application

![Weather Dashboard](https://img.shields.io/badge/Spring%20Boot-3.3.3-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)
![License](https://img.shields.io/badge/License-MIT-blue)

A comprehensive, graduate-level weather application built with Spring Boot that extensively utilizes the OpenWeatherMap API to provide real-time weather data, forecasts, air quality information, and interactive features.

## 🌟 Features

### Core Weather Features
- **Current Weather**: Real-time weather conditions for any city worldwide
  - Temperature (current, feels like, min/max)
  - Humidity, pressure, wind speed and direction
  - Cloudiness and visibility
  - Sunrise and sunset times
  - Weather icons and descriptions

- **5-Day Weather Forecast**: Detailed forecast with hourly breakdowns
  - Temperature trends with interactive charts
  - Precipitation predictions (rain/snow)
  - Wind and humidity forecasts
  - Visual timeline of weather conditions

- **Air Quality Index (AQI)**: Comprehensive air pollution data
  - Real-time AQI ratings (1-5 scale)
  - Detailed component breakdown (CO, NO₂, O₃, SO₂, PM2.5, PM10)
  - Color-coded health indicators

- **UV Index**: Current UV radiation levels with safety recommendations

### Advanced Features
- **City Comparison**: Compare weather conditions across multiple cities side-by-side
- **Favorite Cities**: Save and quickly access your frequently searched cities
- **Interactive Weather Map**: Click on any location to view weather data
- **Geolocation Support**: Automatic location detection for personalized weather
- **Responsive Design**: Beautiful, modern UI that works on all devices

### Technical Features
- **Caching**: Efficient API response caching to reduce API calls
- **Database Integration**: H2 in-memory database for favorites persistence
- **RESTful Architecture**: Clean separation of concerns with service layer
- **Error Handling**: Comprehensive error handling and user feedback
- **Modern Frontend**: Bootstrap 5, Chart.js, Leaflet maps, Font Awesome icons

## 🛠️ Technologies Used

### Backend
- **Spring Boot 3.3.3**: Modern Java framework
- **Java 21**: Latest LTS version with modern features
- **Spring Data JPA**: Database abstraction layer
- **H2 Database**: In-memory database for development
- **Spring Cache**: Response caching mechanism
- **RestTemplate**: HTTP client for API calls
- **Jackson**: JSON processing
- **Lombok**: Reduced boilerplate code
- **Maven**: Dependency management

### Frontend
- **Thymeleaf**: Server-side templating engine
- **Bootstrap 5**: Responsive CSS framework
- **Chart.js**: Interactive charts and graphs
- **Leaflet.js**: Interactive maps
- **Font Awesome 6**: Icon library
- **Custom CSS**: Modern animations and transitions

### APIs
- **OpenWeatherMap API**: 
  - Current Weather Data
  - 5-Day Weather Forecast
  - Air Pollution API
  - UV Index API
  - Geocoding API

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+ 
- OpenWeatherMap API Key ([Get one here](https://openweathermap.org/api))

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd OpenWeatherMap
```

### 2. Configure API Key
Edit `src/main/resources/application.properties` and add your OpenWeatherMap API key:
```properties
api.key=YOUR_API_KEY_HERE
```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

Or run the JAR file:
```bash
java -jar target/Weather-0.0.1-SNAPSHOT.jar
```

### 5. Access the Application
Open your browser and navigate to:
```
http://localhost:9595
```

## 📖 Usage Guide

### Searching for Weather
1. Enter a city name in the search box on the homepage
2. Click "Search" or press Enter
3. View comprehensive weather details including:
   - Current conditions
   - Temperature ranges
   - Air quality
   - UV index
   - Wind and pressure data

### Viewing Forecast
1. Search for a city
2. Click "5-Day Forecast" button
3. View hourly breakdowns and temperature charts

### Comparing Cities
1. Navigate to "Compare Cities" from the menu
2. Enter multiple city names separated by commas (e.g., "London, Paris, New York")
3. View side-by-side comparison of weather conditions

### Adding Favorites
1. Search for a city
2. Click "Add to Favorites" on the weather details page
3. Access your favorites quickly from the homepage

### Using the Weather Map
1. Click "Weather Map" from the navigation
2. Click anywhere on the map to view weather for that location
3. Use "My Location" button for automatic geolocation
4. Search for specific cities on the map

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/example/Weather/
│   │   ├── config/
│   │   │   └── AppConfig.java          # Configuration beans
│   │   ├── controller/
│   │   │   └── WeatherController.java  # REST endpoints
│   │   ├── model/
│   │   │   ├── CurrentWeatherResponse.java
│   │   │   ├── ForecastResponse.java
│   │   │   ├── AirPollutionResponse.java
│   │   │   ├── UVIndexResponse.java
│   │   │   ├── WeatherAlert.java
│   │   │   └── FavoriteCity.java       # JPA entity
│   │   ├── repository/
│   │   │   └── FavoriteCityRepository.java
│   │   ├── service/
│   │   │   └── WeatherService.java     # Business logic
│   │   └── WeatherApplication.java
│   └── resources/
│       ├── static/
│       │   └── css/                     # Stylesheets
│       ├── templates/                   # Thymeleaf templates
│       └── application.properties       # Configuration
└── test/
```

## 🎨 UI Features

- **Modern Gradient Backgrounds**: Eye-catching color schemes
- **Smooth Animations**: Slide-up effects and hover transitions
- **Responsive Cards**: Beautiful card-based layouts
- **Interactive Charts**: Temperature trends visualization
- **Color-Coded Indicators**: AQI and UV index with visual feedback
- **Mobile-First Design**: Optimized for all screen sizes

## 🔧 Configuration

### Application Properties
```properties
# Server Configuration
server.port=9595

# API Configuration
api.key=YOUR_API_KEY

# Database Configuration
spring.datasource.url=jdbc:h2:mem:weatherdb
spring.jpa.hibernate.ddl-auto=update

# Cache Configuration
spring.cache.type=simple
```

### H2 Console
Access the H2 database console at:
```
http://localhost:9595/h2-console
```
- JDBC URL: `jdbc:h2:mem:weatherdb`
- Username: `sa`
- Password: (leave empty)

## 📊 API Endpoints

- `GET /` - Homepage with search and favorites
- `GET /weather?city={city}` - Current weather details
- `GET /forecast?city={city}` - 5-day weather forecast
- `GET /compare?cities={city1,city2,...}` - Compare multiple cities
- `GET /map` - Interactive weather map
- `POST /favorites/add` - Add city to favorites
- `POST /favorites/remove` - Remove city from favorites

## 🎓 Graduate Project Features

This application demonstrates:

1. **Advanced API Integration**: Multiple OpenWeatherMap endpoints
2. **Database Design**: JPA entities and repositories
3. **Caching Strategy**: Performance optimization
4. **Service Layer Architecture**: Clean code principles
5. **Modern Frontend**: Responsive, interactive UI
6. **Error Handling**: Comprehensive exception management
7. **RESTful Design**: Proper HTTP methods and status codes
8. **Data Visualization**: Charts and interactive maps

## 🐛 Troubleshooting

### API Key Issues
- Ensure your API key is valid and active
- Check API key in `application.properties`
- Verify API key has necessary permissions

### Database Issues
- H2 database is in-memory, data resets on restart
- Access H2 console to verify data
- Check `application.properties` for database configuration

### Build Issues
- Ensure Java 21 is installed: `java -version`
- Clean and rebuild: `mvn clean install`
- Check Maven version: `mvn -version`

## 📝 License

This project is open source and available under the MIT License.

## 🙏 Acknowledgments

- [OpenWeatherMap](https://openweathermap.org/) for providing comprehensive weather APIs
- Spring Boot team for the excellent framework
- All open-source contributors

## 📧 Contact

For questions or suggestions, please open an issue in the repository.

---

**Built with ❤️ using Spring Boot and OpenWeatherMap API**
