package nl.hsleiden.imtpmd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import nl.hsleiden.imtpmd.models.Modules;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoScherm(View v) {
        Intent intent = new Intent(this, ModulesActivity.class);
        startActivity(intent);
    }
}
