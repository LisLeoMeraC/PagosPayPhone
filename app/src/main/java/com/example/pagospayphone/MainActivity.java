package com.example.pagospayphone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText dni, celular;
    Button btnEnviarCobro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dni=(EditText)findViewById(R.id.txtDNI);
        celular=(EditText)findViewById(R.id.txtCelular);
        btnEnviarCobro=(Button) findViewById(R.id.btnEnviar);


        btnEnviarCobro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CobroServicioPayPhone();
            }
        });

    }
    public void CobroServicioPayPhone(){

        String url= "https://pay.payphonetodoesposible.com/api/Sale";
        String Token= "zVHmS2kwD-hNlf1ZzWZUQENM7K_Hm9wwLaXi_XB7H2f4vBGebgyZoHoILebpezmoooBYP2z8YsCPlbyiOpjDWyK5Mt83rVuj-7UKKKyuOncXJ-0LkwJzKXYcLZzaEMCgIv6BMHqrnh-DUd1_7XOavCjMkU_tkHB3iNgnFZbLwZdMLOzxT82aEqNb3ZKWYtvfrqyWOkNF6GIUFEC2np6F-g2sVVcIvGK_SRv-gVS9J3NCRX4rEZsKSJs9lYn0puNDsRCyznSaDIblG_6wmqrrASbOkgs9o56T2jIWlEvRxwZiJ5CDsALTKqT9yH_n-wnt53MUGQ";
        RequestQueue queue= Volley.newRequestQueue(this);

        JSONObject object= new JSONObject();
        try {
            object.put("phoneNumber",celular.getText().toString());
            object.put("countryCode","593");
            object.put("clientUserId",dni.getText().toString());
            object.put("reference","Ninguna");
            object.put("responseUrl","http://paystoreCZ.com/confirm.php");
            object.put("amount","1400");
            object.put("amountWithTax","1250");
            object.put("amountWithoutTax","0");
            object.put("tax","150");
            object.put("clientTransactionId","12345");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                error.printStackTrace();
            }
        })
        {
            @Override
            public Map<String, String>getHeaders()  throws AuthFailureError {
                Map<String, String>headers  = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Accept","application/json");
                headers.put("Authorization", "Bearer "+Token);
                return headers;
            }
        };
        queue.add(objectRequest);
    }
}