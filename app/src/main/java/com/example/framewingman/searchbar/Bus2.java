package com.example.framewingman.searchbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Bus2 extends AppCompatActivity {

    TextView Response;
    EditText Lat;
    EditText Lng;
    String strlat;
    String strlng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus2);
        final Button Back = (Button) findViewById(R.id.Back);
        Lat = (EditText) findViewById(R.id.lat_text);
        Lng = (EditText) findViewById(R.id.lng_text);
        Response =(TextView) findViewById(R.id.textView2);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            Double lat = extras.getDouble("Lat");
            Double lng = extras.getDouble("Lng");
            Double Sum = extras.getDouble("Sum");
            String Summ = Sum.toString();
            strlat = lat.toString();
            strlng = lng.toString();
            Lat.setText(strlat);
            Lng.setText(strlng);
            Response.setText(Summ);
        }
        Back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open Form 2
                Intent newActivity = new Intent(Bus2.this,MainActivity.class);
                strlat = null;
                strlng = null;
                newActivity.putExtra("Lat",strlat);
                newActivity.putExtra("Lng",strlng);
                startActivity(newActivity);
            }
        });
    }
}
