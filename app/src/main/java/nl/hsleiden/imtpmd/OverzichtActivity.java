package nl.hsleiden.imtpmd;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

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

import nl.hsleiden.imtpmd.models.Modules;

/**
 * Created by Daan on 22-6-2017.
 */

public class OverzichtActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    public float[] yData = {25f, 75f};
    private String[] xData = {"onvoldoende", "voldoende"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overzicht);
        Log.d(TAG, "onCreate: starting to create chart");


        pieChart = (PieChart) findViewById(R.id.idPieChart);

        Description description = new Description();
        description.setTextColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        description.setText("");
        pieChart.setDescription(description);

        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Voortgang");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

        //haal uit de api
        getAllGrades get = new getAllGrades();
        get.execute();
   }

    public void gotoMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Aantal onvoldoendes en voldoendes");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#DD3E52"));
        colors.add(Color.parseColor("#6BC067"));

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public class getAllGrades extends AsyncTask<URL, Void, ArrayList<Modules>> {
        /** URL om de modules te laden */
        private String API_URL = "http://ronaldtoldevelopment.nl/api/modules/withGrades";
        public String LOG_TAG = OverzichtActivity.class.getSimpleName();

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

            int voldoende = 0;
            int onvoldoende = 0;

            for(int i = 0; i < module.size(); i++){
                if (Double.parseDouble(module.get(i).getCijfer()) < 5.5){
                    voldoende++;
                } else {
                    onvoldoende++;
                }
            }

            float totaalOnvoldoendes = (onvoldoende * 100.0f) / (voldoende + onvoldoende);
            float totaalVoldoendes = (voldoende * 100.0f) / (voldoende + onvoldoende);
            System.out.println(onvoldoende);
            System.out.println(voldoende);

            //update de data
            yData = new float[]{totaalOnvoldoendes, totaalVoldoendes};
            //vul de data in
            addDataSet();
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

                    for (int i = 0; i < jsonResponseArray.length(); i++) {
                        JSONObject module = jsonResponseArray.getJSONObject(i);

                        // Extract the id, name, ects en soort
                        String code = module.getString("id");
                        String naam = module.getString("name");
                        int ECTS = module.getInt("ects");
                        String soort = module.getString("soort");
                        String cijfer = module.getString("cijfer");

                        modules.add(new Modules(code, naam, ECTS, soort, cijfer));

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