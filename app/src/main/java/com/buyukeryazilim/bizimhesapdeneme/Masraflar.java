package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Masraflar extends AppCompatActivity {

    EditText eTextMasrafIsim;
    EditText editTMasrafTutarı;
    EditText editTMasrafAcilma;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masraflar);

        eTextMasrafIsim = findViewById(R.id.editTextMasrafIsim);
        editTMasrafTutarı = findViewById(R.id.editTextMasrafTutarı);
        editTMasrafAcilma = findViewById(R.id.editTextMasrafAciklama);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void kaydetButtonClickk (View view) {

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        myRef.child("Masraflar").child(uuidString).child("masrafIsim").setValue(eTextMasrafIsim.getText().toString());
        myRef.child("Masraflar").child(uuidString).child("masrafTutarı").setValue(editTMasrafTutarı.getText().toString());
        myRef.child("Masraflar").child(uuidString).child("masrafAcıklama").setValue(editTMasrafAcilma.getText().toString());

        Toast.makeText(getApplicationContext(),"Masraf eklendi",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), Masraflar.class);
        startActivity(intent);

    }
}
