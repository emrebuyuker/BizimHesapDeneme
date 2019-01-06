package com.buyukeryazilim.bizimhesapdeneme;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Raporlar extends AppCompatActivity {

    Button btnTarihSec;
    EditText etTarih;
    Button btnTarihSec2;
    Button btnSatislar;
    EditText etTarih2;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raporlar);

        btnTarihSec = (Button) findViewById(R.id.button_tarih_sec);
        etTarih = (EditText) findViewById(R.id.edittext_tarih);
        btnTarihSec2 = (Button) findViewById(R.id.button_tarih_sec2);
        btnSatislar = (Button) findViewById(R.id.buttonSatislar);
        etTarih2 = (EditText) findViewById(R.id.edittext_tarih2);

        btnTarihSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar takvim = Calendar.getInstance();
                int yil = takvim.get(Calendar.YEAR);
                int ay = takvim.get(Calendar.MONTH);
                int gun = takvim.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                month += 1;

                                if(month < 10 && dayOfMonth < 10){
                                    etTarih.setText(year + "/" + "0"+month + "/" + "0"+dayOfMonth);
                                }else if(month < 10){
                                    etTarih.setText(year + "/" + "0"+month + "/" + dayOfMonth);
                                }else if (dayOfMonth < 10){
                                    etTarih.setText(year + "/" + month + "/" + "0"+dayOfMonth);
                                }else{
                                    etTarih.setText(year + "/" + month + "/" + dayOfMonth);
                                }

                            }
                        }, yil, ay, gun);

                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                dpd.show();
            }
        });

        btnTarihSec2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar takvim = Calendar.getInstance();
                int yil = takvim.get(Calendar.YEAR);
                int ay = takvim.get(Calendar.MONTH);
                int gun = takvim.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                month += 1;

                                if(month < 10 && dayOfMonth < 10){
                                    etTarih2.setText(year + "/" + "0"+month + "/" + "0"+dayOfMonth);
                                }else if(month < 10){
                                    etTarih2.setText(year + "/" + "0"+month + "/" + dayOfMonth);
                                }else if (dayOfMonth < 10){
                                    etTarih2.setText(year + "/" + month + "/" + "0"+dayOfMonth);
                                }else{
                                    etTarih2.setText(year + "/" + month + "/" + dayOfMonth);
                                }

                            }
                        }, yil, ay, gun);

                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                dpd.show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void satisRaporlarıButtonClick(View view) {


        Intent intent = new Intent(getApplicationContext(), SatisRaporu.class);

        intent.putExtra("başlangıçTarihi",etTarih.getText().toString());
        intent.putExtra("bitişTarihi",etTarih2.getText().toString());

        startActivity(intent);

    }
}
