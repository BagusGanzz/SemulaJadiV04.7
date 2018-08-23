package semulajadi.v011;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;



public class CurrencyActivity extends AppCompatActivity {

    EditText edt_angka;
    Button bt_inr, bt_usd, bt_aud, bt_cny, bt_idr, bt_sgd;
    TextView tv_hasil;
    String url = "https://api.fixer.io/latest";
    double angka;

    final ArrayList<Double> currency = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_converter);

        edt_angka = (EditText) findViewById(R.id.Input);
        bt_inr = (Button) findViewById(R.id.inr);
        bt_usd = (Button) findViewById(R.id.usd);
        bt_aud = (Button) findViewById(R.id.aud);
        bt_cny = (Button) findViewById(R.id.cny);
        bt_idr = (Button) findViewById(R.id.idr);
        bt_sgd = (Button) findViewById(R.id.sgd);
        tv_hasil = (TextView) findViewById(R.id.Output);
        acces_data(url);


    }

    //object respone untuk acces data
    public void acces_data(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("rates");
                            currency.add(obj.getDouble("MYR"));
                            currency.add(obj.getDouble("INR"));
                            currency.add(obj.getDouble("USD"));
                            currency.add(obj.getDouble("AUD"));
                            currency.add(obj.getDouble("CNY"));
                            currency.add(obj.getDouble("IDR"));
                            currency.add(obj.getDouble("SGD"));
                            Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("eror baca", error.toString());
            }
        });
        requestQueue.add(jsonObjReq);
    }

    public boolean cek(){
        if (edt_angka.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"angka " + currency.get(0),Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }

    //1. Rupee Currency
    public void toINR(View v){
        if (!cek()){
            return;
        }
        try{
            angka = Double.parseDouble(edt_angka.getText().toString());
        }catch(Exception e){
            //Toast.makeText(this, "Masukkan angka", Toast.LENGTH_SHORT).show();
        }

        double hasil = angka / currency.get(0) * currency.get(1);
//        tv_hasil.setText(NumberFormat.getCurrencyInstance(Locale.US).format(hasil));
        tv_hasil.setText("INR " + (hasil));
        Toast.makeText(getApplicationContext(),"1 RM = " + 1 / currency.get(0) * currency.get(1) + " INR",Toast.LENGTH_LONG).show();
    }

    //2. USD Currency
    public void toUSD(View v){
        if (!cek()){
            return;
        }
        try{
            angka = Double.parseDouble(edt_angka.getText().toString());
        }catch(Exception e){
            //Toast.makeText(this, "Masukkan angka", Toast.LENGTH_SHORT).show();
        }

        double hasil = angka / currency.get(0) * currency.get(2);
//        tv_hasil.setText(NumberFormat.getCurrencyInstance(Locale.US).format(hasil));
        tv_hasil.setText("USD " + (hasil));
        Toast.makeText(getApplicationContext(),"1 RM = " + 1 / currency.get(0) * currency.get(2) + " USD",Toast.LENGTH_LONG).show();
    }

    //3. Australian Dollar Currency
    public void toAUD(View v){
        if (!cek()){
            return;
        }
        try{
            angka = Double.parseDouble(edt_angka.getText().toString());
        }catch(Exception e){
            //Toast.makeText(this, "Masukkan angka", Toast.LENGTH_SHORT).show();
        }

        double hasil = angka / currency.get(0) * currency.get(3);
//        tv_hasil.setText(NumberFormat.getCurrencyInstance(Locale.US).format(hasil));
        tv_hasil.setText("AUD " + (hasil));
        Toast.makeText(getApplicationContext(),"1 RM = " + 1 / currency.get(0) * currency.get(3) + " AUD",Toast.LENGTH_LONG).show();
    }

    //4. Yuan Currency
    public void toCNY(View v){
        if (!cek()){
            return;
        }
        try{
            angka = Double.parseDouble(edt_angka.getText().toString());
        }catch(Exception e){
            //Toast.makeText(this, "Masukkan angka", Toast.LENGTH_SHORT).show();
        }

        double hasil = angka / currency.get(0) * currency.get(4);
//        tv_hasil.setText(NumberFormat.getCurrencyInstance(Locale.US).format(hasil));
        tv_hasil.setText("CNY " + (hasil));
        Toast.makeText(getApplicationContext(),"1 RM = " + 1 / currency.get(0) * currency.get(4) + " CNY",Toast.LENGTH_LONG).show();
    }

    //5. Rupiah Currency
    public void toIDR(View v){
        if (!cek()){
            return;
        }
        try{
            angka = Double.parseDouble(edt_angka.getText().toString());
        }catch(Exception e){
            //Toast.makeText(this, "Masukkan angka", Toast.LENGTH_SHORT).show();
        }

        double hasil = angka / currency.get(0) * currency.get(5);
//        tv_hasil.setText(NumberFormat.getCurrencyInstance(Locale.US).format(hasil));
        tv_hasil.setText("IDR " + (hasil));
        Toast.makeText(getApplicationContext(),"1 RM = " + 1 / currency.get(0) * currency.get(5) + " IDR",Toast.LENGTH_LONG).show();
    }

    //6. Singapore Dollar Currency
    public void toSGD(View v){
        if (!cek()){
            return;
        }
        try{
            angka = Double.parseDouble(edt_angka.getText().toString());
        }catch(Exception e){
            //Toast.makeText(this, "Masukkan angka", Toast.LENGTH_SHORT).show();
        }

        double hasil = angka / currency.get(0) * currency.get(6);
//        tv_hasil.setText(NumberFormat.getCurrencyInstance(Locale.US).format(hasil));
        tv_hasil.setText("SGD " + (hasil));
        Toast.makeText(getApplicationContext(),"1 RM = " + 1 / currency.get(0) * currency.get(6) + " SGD",Toast.LENGTH_LONG).show();
    }

    //======================================================================================================================================================


}
