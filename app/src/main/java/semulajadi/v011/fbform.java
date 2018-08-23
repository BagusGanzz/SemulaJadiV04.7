package semulajadi.v011;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.xw.repo.BubbleSeekBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class fbform extends AppCompatActivity {

    ImageView imageView;
    TextView textname, textemail, ordername, orderprice, qtyorder;
    Button order, logout, plusqty, minqty, btn_order;
    int qtyValue = 0;
    String nameOrder, idOrder;
    EditText input_phone, input_ic, input_add, input_email ;
    int priceOrder;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbform);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //nangkep variable
        Intent intent = getIntent();
        if (intent.hasExtra("idYangDiPesan")){
            idOrder = intent.getStringExtra("idYangDiPesan");
            nameOrder = intent.getStringExtra("OrderYgDiPesan");
            priceOrder = intent.getIntExtra("Harga", 0);
        }else {
            finish();
            Toast.makeText(getApplicationContext(), "please try again!", Toast.LENGTH_SHORT).show();
        }

        imageView = (ImageView)findViewById(R.id.imageView);
        ordername = (TextView)findViewById(R.id.ordername);
        orderprice = (TextView)findViewById(R.id.orderprice);
        textname = (TextView)findViewById(R.id.textView);
//        textemail = (TextView)findViewById(R.id.textView2);
        logout = (Button)findViewById(R.id.btn_logout);
        order = (Button)findViewById(R.id.btn_order);
//        minqty = (Button)findViewById(R.id.quantiti_minus);
//        plusqty = (Button)findViewById(R.id.quantiti_plus);
        input_phone = (EditText) findViewById(R.id.Input_phone);
        input_add = (EditText) findViewById(R.id.Input_add);
        input_ic = (EditText) findViewById(R.id.Input_ic);
        input_email = (EditText) findViewById(R.id.Input_email);
        qtyorder = (TextView) findViewById(R.id.qty);


        ordername.setText(nameOrder);
        orderprice.setText("RM "+priceOrder+"");

        BubbleSeekBar bubbleSeekBar = (BubbleSeekBar)findViewById(R.id.seekbar);
        final TextView textView = (TextView)findViewById(R.id.qty);

        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
                textView.setText(String.format("Your Order : %d Ticket",progress));
            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });

//        minqty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                qtyValue = qtyValue-1;
//                if(qtyValue <= 1){
//                    qtyValue=1;
//                }
//                qtyorder.setText(""+qtyValue);
//            }
//        });
//
//        plusqty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                qtyValue = qtyValue + 1;
//                qtyorder.setText(""+qtyValue);
//            }
//        });

        //=null&&email! gua apus
        String firstname=getIntent().getExtras().getString("firstname");
        String lastname=getIntent().getExtras().getString("lastname");
//        final String email=getIntent().getExtras().getString("email");
        String image=getIntent().getExtras().getString("image");
        if(firstname!=null&&lastname!=null&&image!=null)
        {
            textname.setText(firstname+" "+lastname);
//            textemail.setText(email);

            Glide.with(fbform.this).load(image).into(imageView);
        }
        else {
            Toast.makeText(getApplicationContext(),"Data Not Found",Toast.LENGTH_SHORT).show();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                Intent login = new Intent(fbform.this, fblogin.class);
                startActivity(login);
                finish();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(input_email.getText())) {
                    input_email.setError("Input Your Email");
                } else if (TextUtils.isEmpty(input_phone.getText())) {
                    input_phone.setError("Input Your Phone Number");
                } else if (TextUtils.isEmpty(input_ic.getText())) {
                    input_ic.setError("Input Your IC");
                } else if (TextUtils.isEmpty(input_add.getText())) {
                    input_add.setError("Input Your Address");
                } else if (TextUtils.isEmpty(qtyorder.getText())) {
                    qtyorder.setError("Input Your Quantity");
                } else {

                        String email = input_email.getText().toString().trim();
                        String phone = input_phone.getText().toString().trim();
                        String address = input_add.getText().toString().trim();
                        String ic = input_ic.getText().toString().trim();
                        String qty = qtyorder.getText().toString().trim();
                        String fullname = textname.getText().toString();

                        insertOrder(email, fullname, phone, ic, address, qty, idOrder);
                    }
                }
            });
    }



    private void insertOrder(final String email, final String name, final String phonenumber, final String icnumber,
                         final String address, final String quantity, final String eventID) {

        // Tag used to cancel the request
        String tag_string_req = "req_booking";

        progressDialog.setMessage("Booking ...");
        showDialog();

        Log.v("Check Param : ", email+", "+name+", "+phonenumber+", "+icnumber+", "+address+", "+quantity+", "+eventID);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DCR_ORDER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        Toast.makeText(getApplicationContext(), "Pemesanan Berhasil!",
                                Toast.LENGTH_LONG).show();

                        // Launch Main activity
                        Intent intent = new Intent(
                                getApplicationContext(),
                                confirmation.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("name", name);
                params.put("phonenumber", phonenumber);
                params.put("icnumber", icnumber);
                params.put("address", address);
                params.put("quantity", quantity);
                params.put("eventID", eventID);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    public void order (View view){
        Intent intent = new Intent(this, confirmation.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Thanks for Ordering",Toast.LENGTH_SHORT).show();
    }
}
