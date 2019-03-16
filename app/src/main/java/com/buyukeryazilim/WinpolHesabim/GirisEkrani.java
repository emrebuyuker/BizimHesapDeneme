package com.buyukeryazilim.WinpolHesabim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GirisEkrani extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText emailText;
    EditText passwordText;

    CheckBox chBxLogin;

    Context context;

    SharedPreferenc sharedPreferenc;

    DatabaseReference myRef;
    FirebaseDatabase database;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_ekrani);

        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        chBxLogin = findViewById(R.id.checkBoxLogin);

        context = this;

        sharedPreferenc = new SharedPreferenc();

        if(sharedPreferenc.getValueBoolean(context,"checkBox")){

            emailText.setText(sharedPreferenc.getValue(context,"email"));
            passwordText.setText(sharedPreferenc.getValue(context,"password"));
            chBxLogin.setChecked(true);
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void signUp (View view) {
        Intent intent = new Intent(getApplicationContext(),KayitEkrani.class);
        startActivity(intent);
    }

    public void signIn (View view) {

        try {

            mAuth.signInWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();

                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);

                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

                }
            });

        }catch (Exception e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        if(chBxLogin.isChecked()){

            sharedPreferenc.save(context,"email",emailText.getText().toString());
            sharedPreferenc.save(context,"password",passwordText.getText().toString());
            sharedPreferenc.saveBoolean(context,"checkBox",true);

        }else{

            sharedPreferenc.save(context,"email","");
            sharedPreferenc.save(context,"password","");
            sharedPreferenc.saveBoolean(context,"checkBox",false);

        }

    }
}
