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

/**
 * Created by Ryan on 03/04/2018.
 */

public final class PWordListUtils {

    private PWordListUtils(){

    }

    private static List<PWord> extractDataFromJson(String PWordJSON){
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(PWordJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding session to
        List<PWord> pwords = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try{
            // Create a JSONObject from the JSON response string
            JSONObject pbaseJsonResponse = new JSONObject(PWordJSON);

            // Extract the JSONArray
            JSONArray PWordArray = pbaseJsonResponse.getJSONArray("result");

            // For each Word in the WordArray, create an {@link Word} object
            for (int i = 0; i < PWordArray.length(); i++) {
                // Get a single session at position i within the list of Word
                JSONObject pcurrentWord = PWordArray.getJSONObject(i);

                // Extract the value for the key called "username"
                String idPackge = pcurrentWord.getString("id_package");

                // Extract the value for the key called "name"
                String namePackage = pcurrentWord.getString("package_name");

                // Extract the value for the key called "img_name"
                String namaGambarpg = pcurrentWord.getString("nama_gambarpg");

                // Extract the value for the key called "name"
                int costPackage = pcurrentWord.getInt("cost_package");

                // awdawwwwwwwwwwwwwwwwwwwwwww
                String detailPackage = pcurrentWord.getString("detail_package");

                // Create a new {@link Word} object
                PWord word = new PWord(idPackge, costPackage, namePackage, namaGambarpg, detailPackage);

                // Add the new {@link PlacePickModel} to the list of PlacePickModels.
                pwords.add(word);
            }

        }catch (JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("PWordListUtils", "Problem parsing the Word JSON results", e);
        }

        return pwords;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("PWordUtils", "Problem building the URL ", e);
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
                Log.e("PWordUtils", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("PWordUtils", "Problem retrieving the EarthquakeModel JSON results.", e);
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
     * Query the USGS dataset and return a list of {@link PWord} objects.
     */
    public static List<PWord> fetchData(String requestUrl) {
        Log.i("PWordUtils", "Test: fetchPlacePick() called .....");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("PWordUtils", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link PlacePick}s
        List<PWord> pword = extractDataFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return pword;
    }
}
