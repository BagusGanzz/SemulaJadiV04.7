package semulajadi.v011;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Ryan on 21/02/2018.
 */

public class PackageContent extends AppCompatActivity {

    //register item2 yang akan direplace dilayout
    private ImageView pck_photo;
    private TextView pck_name, pck_name_isi, pck_price;
    Button bt_pck;
    private ProgressDialog pDialog;

    int pricePackage;
    String namePackage, photoPackage, idPackage, detailPackage;


//    public static final String EXTRA_NAME = "package_name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_content);

        bt_pck = (Button) findViewById(R.id.btn_pck);

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
        if (intent.hasExtra("idPackage")){
            idPackage = intent.getStringExtra("idPackage");
        }else {
            finish();
            Toast.makeText(getApplicationContext(), "please try again!", Toast.LENGTH_SHORT).show();
        }


        pDialog = new ProgressDialog(PackageContent.this);
        pDialog.setCancelable(false);

        bt_pck = findViewById(R.id.btn_pck);
        pck_photo = findViewById(R.id.pck_photo);
        pck_name = findViewById(R.id.pck_name);
        pck_name_isi = findViewById(R.id.pck_name_isi);
        pck_price = findViewById(R.id.pck_price);


//        Intent intent = getIntent();
//        final String packageName = intent.getStringExtra(EXTRA_NAME);
//
//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(packageName);
//
//        loadBackdrop();

//        Bundle bundle = getIntent().getExtras();
//        if (bundle !=null){
//            PckId = bundle.getString("idpackage");
//            PckName = bundle.getString("namapackage");
//            PckPhoto = bundle.getString("photopackage");
//            PckPrice = bundle.getShort("pricepackage");
//        }
//
//        TextView namepackage = (TextView) findViewById(R.id.pck_name);
//        ImageView photopackage = (ImageView) findViewById(R.id.pck_photo);
//        TextView pricepackage = (TextView) findViewById(R.id.pck_price);
//
//        namepackage.setText(PckName);
//        Picasso.with(getApplicationContext()).load("http://appsemulajadi.com/images/pg/" + PckPhoto).placeholder(R.drawable.bg).into(photopackage);
//        Log.d("Url kos", PckPhoto);
//        pricepackage.setText(PckPrice);


        loadPackage(idPackage);
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

//    private void loadBackdrop() {
//        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
//        Glide.with(this).load(image.getRandomDrawable()).centerCrop().into(imageView);
//    }


    public void pesan_package (View view){
        Intent Intent = new Intent(this,fblogin.class);
        //lempar variable
        Intent.putExtra("idYangDiPesan", idPackage);
        Intent.putExtra("OrderYgDiPesan", namePackage);
        Intent.putExtra("Harga", pricePackage);
        startActivity(Intent);
    }

    private void loadPackage(final String idPackage) {

        // Tag used to cancel the request
        String tag_string_req = "req_pck_data";

        pDialog.setMessage("Loading Package...");
        showDialog();

        //request data
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PCK_INFO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.v("Package Content", "Load package data Response: " + response.toString());
                hideDialog();

                //mereplace data dilayout dengan database
                try {
                    //JSON response -> array -> object
                    JSONObject jObj = new JSONObject(response);
                    JSONArray result = jObj.getJSONArray("result");
                    JSONObject packagee = result.getJSONObject(0);
                    pricePackage = packagee.getInt("cost_package");
                    namePackage = packagee.getString("package_name");
                    photoPackage = packagee.getString("nama_gambarpg");
                    detailPackage = packagee.getString("detail_package");

                    //Load img cafe
                    String photoUrl = AppConfig.URL_PHOTO_PCK+photoPackage;
                    Picasso.with(getApplicationContext()).load(photoUrl).placeholder(R.drawable.bg).into(pck_photo);

                    //Set data to layout
                    pck_name.setText(namePackage);
                    pck_name_isi.setText(detailPackage);
                    pck_price.setText("RM "+pricePackage+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Package", "Removing Session Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_package", idPackage+"");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}