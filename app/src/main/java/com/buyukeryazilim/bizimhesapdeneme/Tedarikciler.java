package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Tedarikciler extends AppCompatActivity {

    FirebaseDatabase database;

    ListView listViewTedarikci;

    ArrayList<String> tedarikciNameFB;
    ArrayList<String> tedarikciAdresFB;
    ArrayList<String> tedarikciMailFB;
    ArrayList<String> tedarikciTelefonFB;
    ArrayList<String> urunKeyFB;

    Context context = this;

    String mod="";

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tedarikciler);

        listViewTedarikci = findViewById(R.id.listView);
        listViewTedarikci.clearAnimation();

        database = FirebaseDatabase.getInstance();

        tedarikciNameFB = new ArrayList<String>();
        tedarikciAdresFB = new ArrayList<String>();
        tedarikciMailFB = new ArrayList<String>();
        tedarikciTelefonFB = new ArrayList<String>();
        urunKeyFB = new ArrayList<String>();

        Intent intent = getIntent();
        mod = intent.getStringExtra("mod");

        getDataFirebase();
        listViewOnClick();
    }

    public void tedarikciBilgileriButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), TedarikciBilgileri.class);
        startActivity(intent);

    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference(firebaseAuth.getCurrentUser().getUid()).child("Tedarikçiler");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("dataSnapshot = "+dataSnapshot);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    tedarikciNameFB.add((String) hashMap.get("isim"));
                    tedarikciAdresFB.add((String) hashMap.get("adres"));
                    tedarikciMailFB.add((String) hashMap.get("mail"));
                    tedarikciTelefonFB.add((String) hashMap.get("telefon"));
                }

                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(Tedarikciler.this, android.R.layout.simple_list_item_1, android.R.id.text1, tedarikciNameFB);
                listViewTedarikci.setAdapter(veriAdaptoru);

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

        if (mod.equals("alış")) {

            listViewTedarikci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), AlisIslemleri.class);

                    System.out.println("tedarikciAdresFB.get(position)= "+tedarikciAdresFB.get(position));

                    intent.putExtra("tedarikciName", tedarikciNameFB.get(position));
                    intent.putExtra("tedarikciAdres", tedarikciAdresFB.get(position));
                    intent.putExtra("tedarikciMail", tedarikciMailFB.get(position));
                    intent.putExtra("tedarikciTelefon", tedarikciTelefonFB.get(position));
                    intent.putExtra("urunKey", urunKeyFB.get(position));

                    startActivity(intent);

            }
        });
        }else{

            listViewTedarikci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), TedarikciBilgileriDetay.class);

                    System.out.println("tedarikciAdresFB.get(position)= "+tedarikciAdresFB.get(position));

                    intent.putExtra("tedarikciName", tedarikciNameFB.get(position));
                    intent.putExtra("tedarikciAdres", tedarikciAdresFB.get(position));
                    intent.putExtra("tedarikciMail", tedarikciMailFB.get(position));
                    intent.putExtra("tedarikciTelefon", tedarikciTelefonFB.get(position));

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
}
