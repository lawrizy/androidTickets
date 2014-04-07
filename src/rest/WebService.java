package rest;

import android.graphics.Point;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by User on 5/04/2014.
 */
public class WebService {

   private final String URL = "urn:AndroidControllerwsdl#getCategorie";

    Gson gson;

    public WebService() {
        gson = new Gson();
    }

    public static InputStream sendRequest(URL url) throws Exception {

        try {
            // Ouverture de la connexion
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Connexion à l'URL
            urlConnection.connect();

            // Si le serveur nous répond avec un code OK
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return urlConnection.getInputStream();
            }
        } catch (Exception e) {
            throw new Exception("");
        }
        return null;
    }

    public List<Point> getPoints() {

        try {
            // Envoi de la requête
            InputStream inputStream = sendRequest(new URL(URL));

            // Vérification de l'inputStream
            if(inputStream != null) {
                // Lecture de l'inputStream dans un reader
                InputStreamReader reader = new InputStreamReader(inputStream);

                // Retourne la liste désérialisée par le moteur GSON
                return gson.fromJson(reader, new TypeToken<List<Point>>(){}.getType());
            }

        } catch (Exception e) {
            Log.e("WebService", "Impossible de rapatrier les données :(");
        }
        return null;
    }

}
