package com.buyukeryazilim.WinpolHesabim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MusteriBilgileriDetay extends AppCompatActivity {

    TextView textViewMusteriName;
    TextView textViTelefonNumarasi;
    TextView textVMail;
    TextView textVAdres;
    TextView textVToplamCiro;
    TextView textVBorc;
    TextView textVAlacagi;

    String musteriName;
    String musteriBorcu;
    String musteriToplamCiro;
    String musteriKey;

    FirebaseDatabase database;

    ArrayList<String> borçFB;
    ArrayList<String> toplamciroFB;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_bilgileri_detay);

        textViewMusteriName = findViewById(R.id.textViewUrunName);
        textViTelefonNumarasi = findViewById(R.id.textViewToplamStok);
        textVMail = findViewById(R.id.textViewAlısFiyati);
        textVAdres = findViewById(R.id.textViewAlısKDVOrani);
        textVToplamCiro = findViewById(R.id.textViewSatisFiyati);
        textVBorc = findViewById(R.id.textViewBorc);
        textVAlacagi = findViewById(R.id.textViewSatisKDVOrani);

        Intent intent = getIntent();
        musteriName = intent.getStringExtra("musteriName");
        final String musteriAdres = intent.getStringExtra("musteriAdres");
        final String musteriMail = intent.getStringExtra("musteriMail");
        final String musteriTelefon = intent.getStringExtra("musteriTelefon");
        musteriBorcu = intent.getStringExtra("musteriBorcu");
        musteriToplamCiro = intent.getStringExtra("musteriToplamCiro");
        musteriKey = intent.getStringExtra("musteriKey");

        textViewMusteriName.setText(musteriName);
        textViTelefonNumarasi.setText(musteriTelefon);
        textVMail.setText(musteriMail);
        textVAdres.setText(musteriAdres);

        database = FirebaseDatabase.getInstance();

        borçFB = new ArrayList<String>();
        toplamciroFB = new ArrayList<String>();

        getDataFirebase();
    }

    private void getDataFirebase() {

        /*System.out.println("musteriName "+musteriName);

        DatabaseReference newReference = database.getReference(firebaseAuth.getCurrentUser().getUid()).child("Kasa");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.getKey().equals(musteriName)) {

                        HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                        borçFB.add((String) hashMap.get("borç"));
                        toplamciroFB.add((String) hashMap.get("toplamciro"));
                    }

                }

                int borcInt = Integer.parseInt(borçFB.get(0));
                textVToplamCiro.setText(toplamciroFB.get(0));

                if (borcInt > 0){
                    textVBorc.setText(borçFB.get(0));
                    textVAlacagi.setText("0");
                }else if (borcInt < 0){
                    textVBorc.setText("0");
                    textVAlacagi.setText(Integer.toString(Math.abs(borcInt)));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        //int borcInt = Integer.parseInt(musteriBorcu);
        double borcDouble = Double.parseDouble(musteriBorcu);
        textVToplamCiro.setText(musteriToplamCiro);

        if (borcDouble > 0){
            textVBorc.setText(musteriBorcu);
            textVAlacagi.setText("0");
        }else if (borcDouble < 0){
            textVBorc.setText("0");
            textVAlacagi.setText(Double.toString(Math.abs(borcDouble)));
        }
    }

    public void odemeButtonClick (View view) {

        Intent intent = new Intent(getApplicationContext(), MusteriOdemeEkrani.class);

        intent.putExtra("borç", musteriBorcu);
        intent.putExtra("musteriName", musteriName);
        intent.putExtra("musteriKey", musteriKey);

        startActivity(intent);

    }
}
