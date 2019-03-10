package com.buyukeryazilim.WinpolHesabim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class MusteriBilgileri extends AppCompatActivity {

    EditText eTextIsim;
    EditText eTextTelefon;
    EditText eTextMail;
    EditText eTextAdres;

    Uri selected;

    FirebaseDatabase database;
    DatabaseReference myRef;

    String kod;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_bilgileri);

        eTextIsim = findViewById(R.id.editTextMasrafIsim);
        eTextTelefon = findViewById(R.id.editTextTelefon);
        eTextMail = findViewById(R.id.editTextMail);
        eTextAdres = findViewById(R.id.editTextAdres);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        getDataFirebase();



    }

    public void kaydetButtonClick (View view) {


        String isim = eTextIsim.getText().toString();
        String telefon = eTextTelefon.getText().toString();
        String mail = eTextMail.getText().toString();
        String adres = eTextAdres.getText().toString();

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        int kodInt = Integer.parseInt(kod);
        kodInt++;

        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Müşteriler").child(uuidString).child("isim").setValue(isim);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Müşteriler").child(uuidString).child("telefon").setValue(telefon);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Müşteriler").child(uuidString).child("mail").setValue(mail);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Müşteriler").child(uuidString).child("adres").setValue(adres);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Müşteriler").child(uuidString).child("borç").setValue("0");
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Müşteriler").child(uuidString).child("toplamciro").setValue("0");
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Müşteriler").child(uuidString).child("kod").setValue("MT-"+kodInt);

        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Kod").child("müşterilerKod").setValue(kodInt);


        Toast.makeText(getApplicationContext(),"Müşteri eklendi",Toast.LENGTH_LONG).show();


        String mod = " ";
        Intent intent = new Intent(getApplicationContext(), Musteriler.class);
        intent.putExtra("mod",mod);
        startActivity(intent);

    }
     private void getDataFirebase() {

        DatabaseReference newReference = database.getReference(firebaseAuth.getCurrentUser().getUid()).child("Kod");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("dataSnapshot5 = " + dataSnapshot);


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("ds2222.getValue() "+ds.getValue());

                    kod=ds.getValue().toString();

                    //HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    //kod= hashMap.get("müşterilerKod").toString();
                    //kodFB.add((String) hashMap.get("müşterilerKod"));

                    System.out.println("dataSnapshot5 = "+kod);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
