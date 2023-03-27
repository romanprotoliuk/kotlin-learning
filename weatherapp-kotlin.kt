// Define the Weather interface
interface Weather {
    fun getTemperature(): Double
    fun getHumidity(): Double
}

// Define the WeatherService interface
interface WeatherService {
    fun getCurrentWeather(): Weather
}

// Implement the WeatherService interface using an external weather API
class OpenWeatherMapService(private val apiKey: String) : WeatherService {
    override fun getCurrentWeather(): Weather {
        // Make an HTTP request to the OpenWeatherMap API to get the current weather data
        val url = "https://api.openweathermap.org/data/2.5/weather?q=New+York&appid=$apiKey"
        val json = URL(url).readText()

        // Parse the JSON response to create a Weather object
        val temperature = JSONObject(json).getJSONObject("main").getDouble("temp")
        val humidity = JSONObject(json).getJSONObject("main").getDouble("humidity")
        return OpenWeatherMapWeather(temperature, humidity)
    }
}

// Implement the Weather interface using data from the OpenWeatherMap API
class OpenWeatherMapWeather(private val temperature: Double, private val humidity: Double) : Weather {
    override fun getTemperature() = temperature - 273.15 // Convert from Kelvin to Celsius
    override fun getHumidity() = humidity
}

// Display the weather data in a console application
fun main() {
    val weatherService: WeatherService = OpenWeatherMapService("your-api-key")
    val currentWeather: Weather = weatherService.getCurrentWeather()
    println("Current temperature: ${currentWeather.getTemperature()}°C")
    println("Current humidity: ${currentWeather.getHumidity()}%")
}
