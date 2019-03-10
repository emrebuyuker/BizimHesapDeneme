package com.buyukeryazilim.WinpolHesabim;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Masraflar extends AppCompatActivity {

    EditText eTextMasrafIsim;
    EditText editTMasrafTutarı;
    EditText editTMasrafAcilma;

    ArrayList<String> nakitFB;
    ArrayList<String> kredikartiFB;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masraflar);

        eTextMasrafIsim = findViewById(R.id.editTextMasrafIsim);
        editTMasrafTutarı = findViewById(R.id.editTextMasrafTutarı);
        editTMasrafAcilma = findViewById(R.id.editTextMasrafAciklama);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        nakitFB = new ArrayList<String>();
        kredikartiFB = new ArrayList<String>();

        getDataFirebase();


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

        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Masraflar").child(uuidString).child("masrafIsim").setValue(eTextMasrafIsim.getText().toString());
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Masraflar").child(uuidString).child("masrafTutarı").setValue(editTMasrafTutarı.getText().toString());
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Masraflar").child(uuidString).child("masrafAcıklama").setValue(editTMasrafAcilma.getText().toString());

        int nakit = Integer.parseInt(nakitFB.get(0));
        int toplamInt = Integer.parseInt(editTMasrafTutarı.getText().toString());

        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("KasaHesabı").child("kredikartı").child("nakit").setValue(Integer.toString(nakit-toplamInt));

        Toast.makeText(getApplicationContext(),"Masraf eklendi",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), Masraflar.class);
        startActivity(intent);

    }

    private void getDataFirebase() {

        DatabaseReference newReference2 = database.getReference(firebaseAuth.getCurrentUser().getUid()).child("KasaHesabı");
        newReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap2 = (HashMap<String, Object>) ds.getValue();
                    nakitFB.add((String) hashMap2.get("nakit"));
                    kredikartiFB.add((String) hashMap2.get("kredikartı"));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
