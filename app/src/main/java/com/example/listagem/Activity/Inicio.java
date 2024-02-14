package com.example.listagem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.listagem.Activity.MainActivity;
import com.example.listagem.Autenticacao.LoginActivity;
import com.example.listagem.Helper.FirebaseHelper;
import com.example.listagem.R;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        new Handler(Looper.getMainLooper()).postDelayed(this::verificaAutenticado, 3000);

    }


    private void verificaAutenticado(){
        if(FirebaseHelper.getAutenticado()){
            startActivity(new Intent(this, MainActivity.class));
        }
        else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}