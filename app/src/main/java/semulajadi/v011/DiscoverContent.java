package semulajadi.v011;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryan on 25/02/2018.
 */

public class DiscoverContent extends AppCompatActivity {

    //register item2 yang akan direplace dilayout
    private ImageView dcr_image;
    private ImageView dcr_route_loc;
    private TextView dcr_name, dcr_name_isi, dcr_route, dcr_facilities_isi, dcr_operation_hours, dcr_price, dcr_sumber, dcr_location;
    Button bt_dcr;
    private ProgressDialog dDialog;

    int idTourism, priceTourism;
    String nameTourism, photoTourism, detailTourism, routeTourism, facilitiesTourism, locationTourism, operationHours, sumberTourism;
    String routeGambar;
    Double latitude, longitude;


//    public static final String EXTRA_NAME = "discover_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_content);

        bt_dcr = (Button) findViewById(R.id.btn_dcr);

        //collapsing toolbar
        FloatingActionButton cc = (FloatingActionButton)findViewById(R.id.cc);

        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CurrencyActivity.class);
                startActivity(intent);

            }
        });

        //nangkep variable sebelumnya
        Intent intent = getIntent();
        if (intent.hasExtra("idTourism")){
            idTourism = intent.getIntExtra("idTourism",0);
        }else {
            finish();
            Toast.makeText(getApplicationContext(), "please try again!", Toast.LENGTH_SHORT).show();
        }

        dDialog = new ProgressDialog(DiscoverContent.this);
        dDialog.setCancelable(false);

        //deklarasi item2 yang akan direplace dilayout
        bt_dcr = findViewById(R.id.btn_dcr);
        dcr_image = findViewById(R.id.dcr_image);
        dcr_name = findViewById(R.id.dcr_name);
        dcr_name_isi = findViewById(R.id.dcr_name_isi);
        dcr_route = findViewById(R.id.dcr_route);
        dcr_facilities_isi = findViewById(R.id.dcr_facilities_isi);
        dcr_operation_hours = findViewById(R.id.dcr_operation_hours);
        dcr_price = findViewById(R.id.dcr_price);
        dcr_sumber = findViewById(R.id.dcr_sumber);
        dcr_location = findViewById(R.id.dcr_location);
        dcr_route_loc = findViewById(R.id.dcr_route_loc);


        loadTourism(idTourism+"");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void pesan_tourism (View view){
        Intent Intent = new Intent(this,fblogin.class);
        //lempar variable
        Intent.putExtra("idYangDiPesan", idTourism+"");
        Intent.putExtra("OrderYgDiPesan", nameTourism);
        Intent.putExtra("Harga", priceTourism);
        startActivity(Intent);
    }

    public void check (View view){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("idMap", idTourism+"");
        intent.putExtra("latitudeMap", latitude);
        intent.putExtra("longitudeMap", longitude);
        startActivity(intent);
    }

    private void loadTourism(final String idTourism) {

        // Tag used to cancel the request
        String tag_string_req = "req_dcr_data";

        dDialog.setMessage("Loading Tourism...");
        showDialog();

        //request data
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DCR_INFO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.v("Tourism Content", "Load Tourism data Response: " + response.toString());
                hideDialog();

                //menarik data dari JSON
                try {
                    //JSON response -> array -> object
                    JSONObject jObj = new JSONObject(response);
                    JSONArray result = jObj.getJSONArray("result");
                    JSONObject tourisme = result.getJSONObject(0);
                    priceTourism = tourisme.getInt("price_tourism");
                    nameTourism = tourisme.getString("name_tourism");
                    photoTourism = tourisme.getString("namagambar");
                    detailTourism = tourisme.getString("detail_tourism");
                    routeTourism = tourisme.getString("route_tourism");
                    facilitiesTourism = tourisme.getString("facilities_tourism");
                    locationTourism = tourisme.getString("location_tourism");
                    operationHours = tourisme.getString("operation_hours");
                    sumberTourism = tourisme.getString("sumber");
                    routeGambar = tourisme.getString("route");
                    latitude = tourisme.getDouble("latitude");
                    longitude = tourisme.getDouble("longitude");

                    //Load img cafe
                    String photoUrl = AppConfig.URL_PHOTO_DCR+photoTourism;
                    Picasso.with(getApplicationContext()).load(photoUrl).placeholder(R.drawable.bg).into(dcr_image);

                    String photoRouteUrl = AppConfig.URL_PHOTO_DCR+routeGambar;
                    Picasso.with(getApplicationContext()).load(photoRouteUrl).placeholder(R.drawable.location).into(dcr_route_loc);

                    //Set data to layout
                    dcr_name.setText(nameTourism);
                    dcr_name_isi.setText(detailTourism);
                    dcr_price.setText("RM "+priceTourism+"");
                    dcr_route.setText(routeTourism);
                    dcr_facilities_isi.setText(facilitiesTourism);
                    dcr_operation_hours.setText(operationHours);
                    dcr_sumber.setText(sumberTourism);
                    dcr_location.setText(locationTourism);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Tourism", "Removing Session Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_tourism", idTourism+"");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!dDialog.isShowing())
            dDialog.show();
    }

    private void hideDialog() {
        if (dDialog.isShowing())
            dDialog.dismiss();
    }

//    private void loadBackdrop2() {
//        final ImageView imageView = (ImageView) findViewById(R.id.backdrop2);
//        Glide.with(this).load(image.getRandomDrawable()).centerCrop().into(imageView);
//    }

    public void moovit (View view){
        Intent openmoovit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://moovit.com/"));
        startActivity(openmoovit);
    }


}
