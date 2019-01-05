package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class TedarikciBilgileri extends AppCompatActivity {

    EditText eTextIsim;
    EditText eTextTelefon;
    EditText eTextMail;
    EditText eTextAdres;

    Uri selected;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tedarikci_bilgileri);

        eTextIsim = findViewById(R.id.editTextMasrafIsim);
        eTextTelefon = findViewById(R.id.editTextTelefon);
        eTextMail = findViewById(R.id.editTextMail);
        eTextAdres = findViewById(R.id.editTextAdres);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void kaydetButtonClick (View view) {


        String isim = eTextIsim.getText().toString();
        String telefon = eTextTelefon.getText().toString();
        String mail = eTextMail.getText().toString();
        String adres = eTextAdres.getText().toString();

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        myRef.child("Tedarikçiler").child(uuidString).child("isim").setValue(isim);
        myRef.child("Tedarikçiler").child(uuidString).child("telefon").setValue(telefon);
        myRef.child("Tedarikçiler").child(uuidString).child("mail").setValue(mail);
        myRef.child("Tedarikçiler").child(uuidString).child("adres").setValue(adres);

        myRef.child("TedarikciKasa").child(isim).child("borç").setValue("0");
        myRef.child("TedarikciKasa").child(isim).child("toplamciro").setValue("0");

        Toast.makeText(getApplicationContext(),"Tedarikçi eklendi",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), Tedarikciler.class);
        startActivity(intent);

    }
}
