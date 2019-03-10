package com.buyukeryazilim.WinpolHesabim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class SatisIslemleriDetay extends AppCompatActivity {

    TextView urunIsmi;
    TextView textVBirimFiyat;
    TextView tutar;
    TextView textVKDVOrani;
    TextView KDVTL;
    TextView textViewToplam;

    EditText miktar;

    FirebaseDatabase database;

    String musteriName;
    String birimFiyat;
    String kdvOrani;
    String urunKey;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satis_islemleri_detay);

        urunIsmi = findViewById(R.id.textViewUrunIsmi);
        textVBirimFiyat = findViewById(R.id.textViewBirimFiyat);
        tutar = findViewById(R.id.textViewTutar);
        textVKDVOrani = findViewById(R.id.textViewKDVOrani);
        KDVTL = findViewById(R.id.textViewKDVTL);
        textViewToplam = findViewById(R.id.textViewToplam);
        miktar = findViewById(R.id.editTextMiktar);

        Intent intent = getIntent();
        final String urunName = intent.getStringExtra("urunName");
        musteriName = intent.getStringExtra("musteriName");
        birimFiyat = intent.getStringExtra("birimFiyat");
        kdvOrani = intent.getStringExtra("kdvOrani");
        urunKey = intent.getStringExtra("urunKey");

        urunIsmi.setText(urunName);
        textVBirimFiyat.setText(birimFiyat);
        textVKDVOrani.setText(kdvOrani);

        database = FirebaseDatabase.getInstance();


        addTextChangedListener();

    }

    private void addTextChangedListener(){

        miktar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {


                if(miktar.getText().toString().trim().equals("") )
                {
                    Toast.makeText(context, "Lütfen miktar giriniz", Toast.LENGTH_SHORT).show();
                }else{

                    int ifade1 = Integer.parseInt(birimFiyat);
                    int ifade2 = Integer.parseInt(miktar.getText().toString());
                    int ifade3 = (ifade2*ifade1);
                    tutar.setText(Integer.toString(ifade3));
                    int kdvtl = Integer.parseInt(kdvOrani);
                    KDVTL.setText(Integer.toString(ifade3*kdvtl/100));
                    textViewToplam.setText(Integer.toString(ifade3+ifade3*kdvtl/100));
                }


            }
        });

    }

    public void kaydetButtonClick (View view) {


        String isim = urunIsmi.getText().toString();
        String adet = miktar.getText().toString();
        String netTutar = tutar.getText().toString();
        String kdv = KDVTL.getText().toString();
        String toplam = textViewToplam.getText().toString();

        Intent intent = new Intent(getApplicationContext(), SatisIslemleri.class);

        intent.putExtra("musteriName", musteriName);
        intent.putExtra("isim", isim);
        intent.putExtra("adet", adet);
        intent.putExtra("netTutar", netTutar);
        intent.putExtra("kdv", kdv);
        intent.putExtra("toplam", toplam);
        intent.putExtra("urunKey", urunKey);

        startActivity(intent);

    }
}
