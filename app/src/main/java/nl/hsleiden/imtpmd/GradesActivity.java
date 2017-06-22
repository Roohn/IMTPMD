package nl.hsleiden.imtpmd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import nl.hsleiden.imtpmd.models.Modules;

public class GradesActivity extends Activity{
    String URL_POST = "http://ronaldtoldevelopment.nl/api/insertGrade";
    TextView cijf, naam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scherm03);

        //haal de juiste code op (bijv. IARCH)
        Intent intent = getIntent();
        final Modules module = (Modules) intent.getExtras().getSerializable("module");
        final int position = intent.getIntExtra("position", 0);
        naam = (TextView) findViewById(R.id.cijferNaam);
        cijf = (TextView) findViewById(R.id.cijferInvoer);
        naam.setText(module.getCode());
        cijf.setText(module.getCijfer());

        Button save = (Button) findViewById(R.id.cijferSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCijfer = cijf.getText().toString();
                registerGrade(module.getCode(), newCijfer);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void registerGrade(final String id, final String cijfer) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(), "Cijfer opgeslagen!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GradesActivity.this, error+"", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("cijfer", cijfer);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
