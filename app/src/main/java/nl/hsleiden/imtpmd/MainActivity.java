package nl.hsleiden.imtpmd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import nl.hsleiden.imtpmd.models.Modules;

public class MainActivity extends AppCompatActivity {
    public TextView titleTextView, dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTextView = (TextView) findViewById(R.id.name);
        dateTextView = (TextView) findViewById(R.id.id);

        //haal de modules op vanuit de API
        getModules task = new getModules();
        task.execute();
    }

    /**
     * Als de API alles opgehaald heeft spreekt het deze methode aan om alles in de lijst te laden
     * @param modules
     */
    public void updateUi(ArrayList<Modules> modules) {
        for(int i = 0; i < modules.size(); i++) {
            //TODO hier alle lijsten vullen met bijv. modules.get(i).getNaam();
        }
    }
}
