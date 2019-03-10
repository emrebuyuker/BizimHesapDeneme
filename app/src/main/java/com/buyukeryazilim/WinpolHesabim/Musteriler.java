package com.buyukeryazilim.WinpolHesabim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Musteriler extends AppCompatActivity {

    FirebaseDatabase database;

    ListView listViewMusteri;

    ArrayList<String> musteriNameFB;
    ArrayList<String> musteriAdresFB;
    ArrayList<String> musteriMailFB;
    ArrayList<String> musteriTelefonFB;
    ArrayList<String> urunKeyFB;
    ArrayList<String> musteriKeyFB;
    ArrayList<String> borcFB;
    ArrayList<String> kodFB;
    ArrayList<String> toplamciroFB;

    String musteriName;
    String musteriAdres;
    String musteriMail;
    String musteriTelefon;
    String mod="";

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteriler);

        Intent intent = getIntent();
        mod = intent.getStringExtra("mod");


        listViewMusteri = findViewById(R.id.listView);
        listViewMusteri.clearAnimation();

        database = FirebaseDatabase.getInstance();

        musteriNameFB = new ArrayList<String>();
        musteriAdresFB = new ArrayList<String>();
        musteriMailFB = new ArrayList<String>();
        musteriTelefonFB = new ArrayList<String>();
        urunKeyFB = new ArrayList<String>();
        musteriKeyFB = new ArrayList<String>();
        borcFB = new ArrayList<String>();
        kodFB = new ArrayList<String>();
        toplamciroFB = new ArrayList<String>();


        getDataFirebase();
        listViewOnClick();
    }

    public void musteriBilgileriButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MusteriBilgileri.class);
        startActivity(intent);

    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference(firebaseAuth.getCurrentUser().getUid()).child("Müşteriler");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    musteriNameFB.add((String) hashMap.get("isim"));
                    musteriAdresFB.add((String) hashMap.get("adres"));
                    musteriMailFB.add((String) hashMap.get("mail"));
                    musteriTelefonFB.add((String) hashMap.get("telefon"));
                    borcFB.add((String) hashMap.get("borç"));
                    kodFB.add((String) hashMap.get("kod"));
                    toplamciroFB.add((String) hashMap.get("toplamciro"));
                    musteriKeyFB.add(ds.getKey());
                }

                System.out.println("musteriKeyFB= "+musteriKeyFB);

                //ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(Musteriler.this, android.R.layout.simple_list_item_1, android.R.id.text1, musteriNameFB);
                //listViewMusteri.setAdapter(veriAdaptoru);

                listViewAdapter();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        DatabaseReference newReference2 = database.getReference(firebaseAuth.getCurrentUser().getUid()).child("Ürünler");
        newReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("dataSnapshot2 = "+dataSnapshot.getChildren());

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();

                    urunKeyFB.add(ds.getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listViewOnClick() {

        System.out.println("mod = "+mod);

        if (mod.equals("satış")) {


            listViewMusteri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    musteriName = musteriNameFB.get(position);

                    String isim = "";
                    String adet = "0";
                    String netTutar = "";
                    String kdv = "";
                    String toplam = "0";
                    Intent intent = new Intent(getApplicationContext(), SatisIslemleri.class);

                    intent.putExtra("musteriName", musteriName);
                    intent.putExtra("isim", isim);
                    intent.putExtra("adet", adet);
                    intent.putExtra("netTutar", netTutar);
                    intent.putExtra("kdv", kdv);
                    intent.putExtra("toplam", toplam);
                    intent.putExtra("urunKey", urunKeyFB.get(position));


                    startActivity(intent);

                }
            });
        }else{

            listViewMusteri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    musteriName = musteriNameFB.get(position);
                    musteriAdres = musteriAdresFB.get(position);
                    musteriMail= musteriMailFB.get(position);
                    musteriTelefon = musteriTelefonFB.get(position);

                    Intent intent = new Intent(getApplicationContext(), MusteriBilgileriDetay.class);

                    intent.putExtra("musteriName", musteriName);
                    intent.putExtra("musteriAdres", musteriAdres);
                    intent.putExtra("musteriMail", musteriMail);
                    intent.putExtra("musteriTelefon", musteriTelefon);
                    intent.putExtra("musteriBorcu", borcFB.get(position));
                    intent.putExtra("musteriToplamCiro", toplamciroFB.get(position));
                    intent.putExtra("musteriKey", musteriKeyFB.get(position));

                    startActivity(intent);

                }
            });

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void listViewAdapter() {

        final List<ListViewItem> users = new ArrayList<>();

        for (int i = 0; i<musteriNameFB.size() ;i++){

            users.add(new ListViewItem(kodFB.get(i),musteriNameFB.get(i),borcFB.get(i)));

        }

        final ListView listView = (ListView) findViewById(R.id.listView);

        CustomAdapter adapter = new CustomAdapter(Musteriler.this, users);
        listView.setAdapter(adapter);

    }
}
