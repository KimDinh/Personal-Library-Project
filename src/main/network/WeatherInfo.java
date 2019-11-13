package network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherInfo {
    public void displayWeather() {
        try {
            String theURL = "https://api.openweathermap.org/data/2.5/weather?q=Vancouver,can&APPID="
                    + "4bd116e459f665bd30d6efada1d88ca5";
            URL url = new URL(theURL);
            String jsonData = readSource(url);
            parseWeather(jsonData);
        } catch (MalformedURLException e) {
            System.out.println("Error getting URL");
        } catch (IOException e) {
            System.out.println("Error getting URL");
        } catch (JSONException e) {
            System.out.println("Error parsing JSON data");
        }
    }

    private String readSource(URL url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }
        br.close();
        return sb.toString();
    }

    private void parseWeather(String jsonData) throws JSONException {
        JSONObject weather = new JSONObject(jsonData);
        System.out.println("The weather at Vancouver today is:");
        JSONArray description = weather.getJSONArray("weather");
        for (int i = 0; i < description.length(); i++) {
            System.out.print(description.getJSONObject(i).get("main"));
            if (i < description.length() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        JSONObject measurement = weather.getJSONObject("main");
        System.out.println("Temperature: " + measurement.get("temp") + "K");
        System.out.println("Pressure: " + measurement.get("pressure") + "mb");
        System.out.println("Humidity: " + measurement.get("humidity") + "%\n");
    }
}
