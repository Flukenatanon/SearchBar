package com.example.framewingman.searchbar;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.common.api.Status;
import android.os.StrictMode;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Double sum;
    String in1;
    String in2;
    LatLng latLng;
    Double Lat;
    Double Lng;
    private static final String TAG = "MainActivity";
    GoogleApiClient mGoogleApiClient;
    protected String NAMESPACE = "http://tempuri.org/";
    protected String URL = "http://servicetest0362.somee.com/calservice.asmx"; // WSDL
    protected String SOAP_ACTION = "http://tempuri.org/Plus";
    protected String METHOD_NAME = "Plus"; // Method on web service
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Button btnsearch = (Button) findViewById(R.id.search_button);
        // Create the LocationRequest object
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
/*
* The following code example shows setting an AutocompleteFilter on a PlaceAutocompleteFragment to
* set a filter returning only results with a precise address.
*/
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE)
                .setCountry("TH")
                .build();
        autocompleteFragment.setFilter(typeFilter);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                in1 = Lat.toString();
                in2 = Lng.toString();
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("input1", in1);
                request.addProperty("input2", in2);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE soap =new HttpTransportSE( URL);
                try {
                    soap.call(SOAP_ACTION, envelope);
                    //Get data
                    SoapPrimitive output = (SoapPrimitive) envelope.getResponse();
                    output.toString();
                    sum = new Double(String.valueOf(output));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }


                // Open Form 2
                Intent newActivity = new Intent(MainActivity.this,Bus2.class);
                newActivity.putExtra("Lat",Lat);
                newActivity.putExtra("Lng",Lng);
                newActivity.putExtra("Sum",sum);
                startActivity(newActivity);


            }
        });
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                latLng = new LatLng(place.getLatLng().latitude,place.getLatLng().longitude);
                Log.i(TAG, "Place: " + place.getName());//get place details here
                Lat = latLng.latitude;
                Lng = latLng.longitude;
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }




}
