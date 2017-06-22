package nl.hsleiden.imtpmd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SemesterActivity extends Activity {
    public int jaar;
    public int semester = 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scherm01);
        Intent intent = getIntent();
        jaar = intent.getIntExtra("jaar", 0);
    }

    public void gotoScherm2(View v) {
        Intent intent = new Intent(this, KeuzeActivity.class);
        switch (v.getId()) {
            case R.id.s1:
                semester = 1;
                break;
            case R.id.s2:
                semester = 2;
                break;
            case R.id.s3:
                semester = 3;
                break;
            case R.id.s4:
                semester = 4;
                break;
        }

        intent.putExtra("jaar", jaar);
        intent.putExtra("semester", semester);
        startActivity(intent);
    }
}
