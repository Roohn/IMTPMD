package nl.hsleiden.imtpmd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import nl.hsleiden.imtpmd.models.Modules;
import nl.hsleiden.imtpmd.models.Semester;

public class MainActivity extends AppCompatActivity {
    int jaar = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoScherm(View v) {
        Intent intent = new Intent(this, SemesterActivity.class);
        switch (v.getId()) {
            case R.id.j1:
                jaar = 1;
                break;
            case R.id.j2:
                jaar = 2;
                break;
            case R.id.j3:
                jaar = 3;
                break;
            case R.id.j4:
                jaar = 4;
                break;
        }
        intent.putExtra("jaar", jaar);
        Log.d("hoi", ""+jaar);
        startActivity(intent);
    }
}
