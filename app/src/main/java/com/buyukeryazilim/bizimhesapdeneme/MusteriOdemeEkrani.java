package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MusteriOdemeEkrani extends AppCompatActivity {

    EditText editTOdenecekTutar;
    TextView textVToplamBorc;
    TextView textVKalanBorc;
    TextView textVAlacak;

    String musteriName;
    String borc;
    String musteriKey;

    double odenecekTutarInt;
    double toplamBorcInt;

    DatabaseReference myRef;
    FirebaseDatabase database;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odeme_ekrani);

        editTOdenecekTutar = findViewById(R.id.editTextOdenecekTutar);
        textVToplamBorc = findViewById(R.id.textViewToplamBorc);
        textVKalanBorc = findViewById(R.id.textViewKalanBorc);
        textVAlacak = findViewById(R.id.textViewAlacak);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Intent intent = getIntent();
        borc = intent.getStringExtra("borç");
        musteriName = intent.getStringExtra("musteriName");
        musteriKey = intent.getStringExtra("musteriKey");



        addTextChangedListener();


    }

    public void odemeYapButtonClick(View view) {

        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Müşteriler").child(musteriKey).child("borç").setValue(Double.toString(toplamBorcInt-odenecekTutarInt));

        Toast.makeText(getApplicationContext(),"Ödeme İşlemi Başarılı",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), Musteriler.class);

        intent.putExtra("mod", "ödeme");

        startActivity(intent);

    }

    private void addTextChangedListener(){

        editTOdenecekTutar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                System.out.println("editTOdenecekTutar= "+editTOdenecekTutar.getText());


                if(editTOdenecekTutar.getText().toString().trim().equals("") )
                {
                    Toast.makeText(MusteriOdemeEkrani.this, "Lütfen ödenecek tutar giriniz", Toast.LENGTH_SHORT).show();
                }else{
                    odenecekTutarInt= Double.parseDouble(editTOdenecekTutar.getText().toString());
                    toplamBorcInt = Double.parseDouble(borc);

                    textVToplamBorc.setText(borc);
                    if (toplamBorcInt-odenecekTutarInt > 0){
                        textVKalanBorc.setText(Double.toString(toplamBorcInt-odenecekTutarInt));
                        textVAlacak.setText("0");
                    }else{
                        textVKalanBorc.setText("0");
                        textVAlacak.setText(Double.toString(odenecekTutarInt-toplamBorcInt));
                    }
                }


            }
        });

    }
}
