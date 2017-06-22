package nl.hsleiden.imtpmd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Daan on 21-6-2017.
 */

public class SemesterActivity extends AppCompatActivity {
    public int jaar;
    public int semester = 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scherm01);
        Intent intent = getIntent();
        jaar = intent.getIntExtra("jaar", 0);
if (getSupportActionBar() != null){
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
}
    }

    public void gotoScherm2(View v) {
        Intent intent = new Intent(this, ModulesActivity.class);
        switch (v.getId()) {
            case R.id.s1:
                semester = 1;
                break;
            case R.id.s2:
                semester = 2;
                break;
        }

        jaar = this.jaar;
        intent.putExtra("jaar", jaar);
        intent.putExtra("semester", semester);
        startActivity(intent);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
