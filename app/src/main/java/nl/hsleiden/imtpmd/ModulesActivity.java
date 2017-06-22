package nl.hsleiden.imtpmd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

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

import nl.hsleiden.imtpmd.models.Modules;

public class ModulesActivity extends Activity {
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scherm02);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        //haal jaar en semester uit vorige schermen
        Intent intent = getIntent();
        int jaar = intent.getIntExtra("jaar", 0);
        int semester = intent.getIntExtra("semester", 0);
        int keuze = intent.getIntExtra("keuze", 0);

        getModules getmodules = new getModules(jaar, semester, keuze);
        getmodules.execute();
    }

    /*
     * Haal de modules uit de API
     */
    public class getModules extends AsyncTask<URL, Void, ArrayList<Modules>> {
        /** URL om de modules te laden */
        private String API_URL = "http://ronaldtoldevelopment.nl/api/modules";
        public String LOG_TAG = MainActivity.class.getSimpleName();
        private int keuze;

        public getModules(int jaar, int semester, int keuze) {
            this.API_URL = API_URL + "/year/" + jaar + "/semester/" + semester;
            this.keuze = keuze;
        }

        @Override
        protected ArrayList<Modules> doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(API_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            ArrayList<Modules> modules = extractModulesFromJson(jsonResponse);

            // Return the {@link Event} object as the result of the {@link ModulesAsyncTask}
            return modules;
        }

        /**
         * Update the screen with the given modules
         */
        @Override
        protected void onPostExecute(ArrayList<Modules> module) {
            if (module == null) {
                return;
            }

            ModulesArrayAdapter mAdapter = new ModulesArrayAdapter(module);
            recyclerView.setAdapter(mAdapter);
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
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
         * Return an {@link Modules} object by parsing out information
         * about the first module from the input modulesJSON string.
         */
        private ArrayList<Modules> extractModulesFromJson(String modulesJSON) {
            try {
                JSONArray jsonResponseArray = new JSONArray(modulesJSON);
                ArrayList<Modules> modules = new ArrayList<>();

                // If there are results in the features array
                if (jsonResponseArray.length() > 0) {

                    //haal jaar en semester op
                    JSONObject date = jsonResponseArray.getJSONObject(0);
                    int jaar = date.getInt("year");
                    int semester = date.getInt("semester");
                    JSONArray modulesArray = date.getJSONArray("modules");

                    for (int i = 0; i < modulesArray.length(); i++) {
                        JSONObject module = modulesArray.getJSONObject(i);

                        // Extract the id, name, ects en soort
                        String code = module.getString("id");
                        String naam = module.getString("name");
                        int ECTS = module.getInt("ects");
                        String soort = module.getString("soort");
                        String cijfer = module.getString("cijfer");

                        if (keuze == 1 && soort.equals("hoofdvak")) {
                            modules.add(new Modules(code, naam, ECTS, jaar, semester, soort, cijfer));
                        }
                        if (keuze == 2 && soort.equals("keuzevak")) {
                            modules.add(new Modules(code, naam, ECTS, jaar, semester, soort, cijfer));
                        }

                    }
                }
                return modules;

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the modules JSON results", e);
            }
            return null;
        }
    }
}