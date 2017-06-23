package nl.hsleiden.imtpmd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class KeuzeActivity extends AppCompatActivity {
    public int jaar;
    public int semester;
    public int keuze = 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keuze);
        Intent intent = getIntent();
        jaar = intent.getIntExtra("jaar", 0);
        semester = intent.getIntExtra("semester", 0);
if (getSupportActionBar() != null){
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
}
    }

    public void gotoScherm2(View v) {
        Intent intent = new Intent(this, ModulesActivity.class);
        switch (v.getId()) {
            case R.id.s1:
                keuze = 1;
                break;
            case R.id.s2:
                keuze = 2;
                break;
        }

        intent.putExtra("jaar", jaar);
        intent.putExtra("semester", semester);
        intent.putExtra("keuze", keuze);
        startActivity(intent);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
