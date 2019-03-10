package com.buyukeryazilim.WinpolHesabim;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class SatisIslemleri extends AppCompatActivity {

    TextView satısYapılanKisi;
    TextView islemTarihi;
    TextView textVToplam;

    String musteriName;
    String isim;
    String adet;
    String netTutar;
    String kdv;
    String toplam;
    String urunKey;
    String adetFB;

    ArrayList<String> borçFB;
    ArrayList<String> toplamciroFB;
    ArrayList<String> nakitFB;
    ArrayList<String> kredikartiFB;

    DatabaseReference myRef;
    FirebaseDatabase database;

    SimpleDateFormat bugun ;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satis_islemleri);

        satısYapılanKisi = findViewById(R.id.textViewSatısYapılanKisi);
        islemTarihi = findViewById(R.id.textViewIslemTarihi);
        textVToplam = findViewById(R.id.textViewToplam);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Intent intent = getIntent();
        musteriName = intent.getStringExtra("musteriName");
        isim = intent.getStringExtra("isim");
        adet = intent.getStringExtra("adet");
        netTutar = intent.getStringExtra("netTutar");
        kdv = intent.getStringExtra("kdv");
        toplam = intent.getStringExtra("toplam");
        urunKey = intent.getStringExtra("urunKey");


        textVToplam.setText(toplam);
        satısYapılanKisi.setText(musteriName);
        Date tarih = new Date();
        bugun = new SimpleDateFormat("dd/MM/yyyy");
        islemTarihi.setText(bugun.format(tarih));

        borçFB = new ArrayList<String>();
        toplamciroFB = new ArrayList<String>();
        nakitFB = new ArrayList<String>();
        kredikartiFB = new ArrayList<String>();

        getDataFirebase();
    }

    public void urunEkleButtonClick(View view) {

        String mod = "satıs";
        Intent intent = new Intent(getApplicationContext(), Urunler.class);
        intent.putExtra("mod",mod);
        intent.putExtra("musteriName",musteriName);
        startActivity(intent);

    }

    public void satisYapButtonClick(View view) {

        try{
            AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Ödeme Türünü Seciniz").setCancelable(false).setPositiveButton("Nakit", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int id) {
                    UUID uuid = UUID.randomUUID();
                    String uuidString = uuid.toString();

                    String tarih = islemTarihi.getText().toString();

                    Date tarih2 = new Date();
                    bugun = new SimpleDateFormat("yyyy/MM/dd");



                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("islemTarihi").setValue(tarih);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("islemTarihi2").setValue(bugun.format(tarih2));
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("adet").setValue(adet);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("musteriName").setValue(musteriName);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("isim").setValue(isim);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("netTutar").setValue(netTutar);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("kdv").setValue(kdv);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("toplam").setValue(toplam);

                    int adetIntFB = Integer.parseInt(adetFB.toString());
                    int adetInt = Integer.parseInt(adet);

                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(urunKey).child("adet").setValue(Integer.toString(adetIntFB-adetInt));

                    int borçInt = Integer.parseInt(borçFB.get(0));
                    int toplamciroInt = Integer.parseInt(toplamciroFB.get(0));
                    int toplamInt = Integer.parseInt(toplam);

                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Kasa").child(musteriName).child("borç").setValue(Integer.toString(borçInt+toplamInt));
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Kasa").child(musteriName).child("toplamciro").setValue(Integer.toString(toplamciroInt+toplamInt));

                    int nakit = Integer.parseInt(nakitFB.get(0));

                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("KasaHesabı").child("kredikartı").child("nakit").setValue(Integer.toString(nakit+toplamInt));

                    Toast.makeText(getApplicationContext(),"Satış İşlemi Başarılı",Toast.LENGTH_LONG).show();

                    String isim = "";
                    String adet = "0";
                    String netTutar = "";
                    String kdv = "";
                    String toplam = "0";
                    String musteriName=" ";
                    Intent intent = new Intent(getApplicationContext(), Satislar.class);

                    intent.putExtra("musteriName", musteriName);
                    intent.putExtra("isim", isim);
                    intent.putExtra("adet", adet);
                    intent.putExtra("netTutar", netTutar);
                    intent.putExtra("kdv", kdv);
                    intent.putExtra("toplam", toplam);

                    startActivity(intent);
                }

            }).setNegativeButton("Kredi Kartı", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UUID uuid = UUID.randomUUID();
                    String uuidString = uuid.toString();

                    String tarih = islemTarihi.getText().toString();

                    Date tarih2 = new Date();
                    bugun = new SimpleDateFormat("yyyy/MM/dd");

                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("islemTarihi").setValue(tarih);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("islemTarihi2").setValue(bugun.format(tarih2));
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("adet").setValue(adet);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("musteriName").setValue(musteriName);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("isim").setValue(isim);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("netTutar").setValue(netTutar);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("kdv").setValue(kdv);
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Satış").child(uuidString).child("toplam").setValue(toplam);

                    int adetIntFB = Integer.parseInt(adetFB.toString());
                    int adetInt = Integer.parseInt(adet);

                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(urunKey).child("adet").setValue(Integer.toString(adetIntFB-adetInt));

                    int borçInt = Integer.parseInt(borçFB.get(0));
                    int toplamciroInt = Integer.parseInt(toplamciroFB.get(0));
                    int toplamInt = Integer.parseInt(toplam);

                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Kasa").child(musteriName).child("borç").setValue(Integer.toString(borçInt+toplamInt));
                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Kasa").child(musteriName).child("toplamciro").setValue(Integer.toString(toplamciroInt+toplamInt));

                    int krediKartı = Integer.parseInt(kredikartiFB.get(0));

                    myRef.child(firebaseAuth.getCurrentUser().getUid()).child("KasaHesabı").child("kredikartı").child("kredikartı").setValue(Integer.toString(krediKartı+toplamInt));

                    Toast.makeText(getApplicationContext(),"Satış İşlemi Başarılı",Toast.LENGTH_LONG).show();

                    String isim = "";
                    String adet = "0";
                    String netTutar = "";
                    String kdv = "";
                    String toplam = "0";
                    String musteriName=" ";
                    Intent intent = new Intent(getApplicationContext(), Satislar.class);

                    intent.putExtra("musteriName", musteriName);
                    intent.putExtra("isim", isim);
                    intent.putExtra("adet", adet);
                    intent.putExtra("netTutar", netTutar);
                    intent.putExtra("kdv", kdv);
                    intent.putExtra("toplam", toplam);

                    startActivity(intent);
                }
            });

            alertDialogBuilder.create().show();
        }
        catch(IllegalStateException e){
            e.printStackTrace();
        }

    }

    private void getDataFirebase() {

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        System.out.println("urunKey= "+urunKey);

        DatabaseReference newReference3 = database.getReference(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(urunKey).child("adet");
        newReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                adetFB=dataSnapshot.getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
