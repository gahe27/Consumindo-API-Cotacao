package com.example.appmoveisp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

public class ApresentationActivity extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtValorReal;
    private ImageView imgRelogio;
    private TextView txtValorEstrangeiro;
    private Button btnDolar;
    private Button btnLibra;
    private Button btnEuro;
    private Button btnSair;
    private TextView txtMoeda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apresentation_activity);

        txtNome = findViewById(R.id.txtNome);
        txtValorReal = findViewById(R.id.txtValorReal);
        imgRelogio = findViewById(R.id.imgRelogio);
        btnDolar = findViewById(R.id.btnDolar);
        btnLibra = findViewById(R.id.btnLibra);
        btnEuro = findViewById(R.id.btnEuro);
        btnSair = findViewById(R.id.btnSair);
        txtValorEstrangeiro = findViewById(R.id.txtValorEstrangeiro);
        txtMoeda = findViewById(R.id.txtMoeda);

        Intent intent = getIntent();
        txtNome.setText(intent.getStringExtra("texto"));
        txtValorReal.setText(intent.getStringExtra("valor"));
        int id = intent.getIntExtra("imageResId", 0);
        if (id == 0) {
            imgRelogio.setImageResource(R.drawable.op1);
        } else if (id == 1) {
            imgRelogio.setImageResource(R.drawable.op2);
        } else if (id == 2) {
            imgRelogio.setImageResource(R.drawable.op3);
        }

        btnDolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url[] = {"https://economia.awesomeapi.com.br/last/USD-BRL", "USDBRL"};
                new FetchJsonTask().execute(url);
                txtMoeda.setText("Cotação US$");
                txtValorEstrangeiro.setText(" Preço em US$");
            }
        });

        btnLibra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url[] = {"https://economia.awesomeapi.com.br/last/GBP-BRL", "GBPBRL"};
                new FetchJsonTask().execute(url);
                txtMoeda.setText("Cotação £");
                txtValorEstrangeiro.setText("Preço em £");
            }
        });

        btnEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url[] = {"https://economia.awesomeapi.com.br/last/EUR-BRL", "EURBRL"};
                new FetchJsonTask().execute(url);
                txtMoeda.setText("Cotação €");
                txtValorEstrangeiro.setText("Preço em €");
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApresentationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private class FetchJsonTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                // URL da API
                URL url = new URL(strings[0]);

                // Abra uma conexão HTTP
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Leia a resposta da API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Analise a resposta JSON
                JSONObject jsonObject = new JSONObject(response.toString());
                String bid = jsonObject.getJSONObject(strings[1]).getString("bid");

                return bid;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(String json) {
            if (json == null) {
                Toast.makeText(ApresentationActivity.this, "Erro ao buscar os dados JSON.", Toast.LENGTH_LONG).show();
            } else {
                Float valorEmReal = Float.parseFloat(txtValorReal.getText().toString());
                Float valorConvertido = valorEmReal / Float.parseFloat(json);
                txtMoeda.append(String.format("%.2f",Float.parseFloat(json)));
                txtValorEstrangeiro.append(String.format("%.2f", valorConvertido));
                //Toast.makeText(ApresentationActivity.this, json, Toast.LENGTH_LONG).show();

            }
        }
    }
}

