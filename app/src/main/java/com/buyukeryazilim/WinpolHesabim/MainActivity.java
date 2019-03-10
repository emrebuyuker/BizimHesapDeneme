package com.buyukeryazilim.WinpolHesabim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void musterilerButtonClick(View view) {

        String mod = "müsteri";
        Intent intent = new Intent(getApplicationContext(), Musteriler.class);
        intent.putExtra("mod",mod);
        startActivity(intent);

    }

    public void tedarikcilerButtonClick(View view) {

        String mod = "tedarikci";
        Intent intent = new Intent(getApplicationContext(), Tedarikciler.class);
        intent.putExtra("mod",mod);
        startActivity(intent);

    }

    public void urunlerButtonClick(View view) {

        String mod = "ürün";
        Intent intent = new Intent(getApplicationContext(), Urunler.class);
        intent.putExtra("mod",mod);
        startActivity(intent);

    }

    public void satislarButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), Satislar.class);
        startActivity(intent);

    }

    public void masraflarButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), Masraflar.class);
        startActivity(intent);

    }

    public void alislarButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), Alislar.class);
        startActivity(intent);

    }

    public void hesaplarButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), Hesaplar.class);
        startActivity(intent);

    }

    public void stoklarButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), Stoklar.class);
        startActivity(intent);

    }

    public void raporlarButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), Raporlar.class);
        startActivity(intent);

    }
}

