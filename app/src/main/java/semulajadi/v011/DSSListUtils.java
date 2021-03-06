package semulajadi.v011;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class DSSListUtils {

    private DSSListUtils(){

    }

    private static List<Word> extractDataFromJson(String DSSJSON){
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(DSSJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding session to
        List<Word> dss = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try{
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(DSSJSON);

            // Extract the JSONArray
            JSONArray DSSARray = baseJsonResponse.getJSONArray("result");

            // For each Word in the DSSARray, create an {@link Word} object
            for (int i = 0; i < DSSARray.length(); i++) {
                // Get a single session at position i within the list of Word
                JSONObject currentItem = DSSARray.getJSONObject(i);

                // Extract the value for the key called "username"
                int idTourism = currentItem.getInt("id_tourism");

                // Extract the value for the key called "name"
                String nameTourism = currentItem.getString("name_tourism");

                // Extract the value for the key called "img_name"
                String namaGambar = currentItem.getString("namagambar");

                // Extract the value for the key called "name"
                int priceTourism = currentItem.getInt("price_tourism");

                String detailTourism = currentItem.getString("detail_tourism");

                String routeTourism = currentItem.getString("route_tourism");

                String facilitiesTourism = currentItem.getString("facilities_tourism");

                String locationTourism = currentItem.getString("location_tourism");

                String operationHours = currentItem.getString("operation_hours");

                String sumberTourism = currentItem.getString("sumber");

                String routeGambar = currentItem.getString("route");

                Double longitude = currentItem.getDouble("longitude");

                Double latitude = currentItem.getDouble("latitude");

                // Create a new {@link Word} object
                Word word = new Word(idTourism, priceTourism, nameTourism, namaGambar, detailTourism, routeTourism, facilitiesTourism, locationTourism, operationHours, sumberTourism, routeGambar, longitude, latitude);

                // Add the new {@link PlacePickModel} to the list of PlacePickModels.
                dss.add(word);
            }

        }catch (JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("WordListUtils", "Problem parsing the Word JSON results", e);
        }

        return dss;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("DSSUtils", "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("DSSUtils", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("DSSUtils", "Problem retrieving the EarthquakeModel JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the USGS dataset and return a list of {@link Word} objects.
     */
    public static List<Word> fetchData(String requestUrl) {
        Log.i("DSSUtils", "Test: fetchPlacePick() called .....");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("DSSUtils", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link PlacePick}s
        List<Word> dss = extractDataFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return dss;
    }
}
