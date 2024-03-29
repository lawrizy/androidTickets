package rest;

import android.os.AsyncTask;
import android.util.Log;
import model.CategorieIncident;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 5/04/2014.
 */
public class WebServiceRest {


    ;

    public void getUser(String username, String password) {
    }

    public static String GET(String url) {
        List<CategorieIncident> listCat = new ArrayList<CategorieIncident>();
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
//
            // make Get request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null) {
                //result = convertInputStreamToString(inputStream);
                InputStreamReader reader = new InputStreamReader(inputStream);
            } else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException, JSONException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
//        JSONObject jsonObject = new JSONObject(result);
//        jsonObject.length();
        // JSONArray User= jsonObject.getJSONArray("User");
        // User.length();

        return result;
    }


    public class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.

    }


}
