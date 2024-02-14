package com.example.listagem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.listagem.Autenticacao.LoginActivity;
import com.example.listagem.Helper.FirebaseHelper;

public class RecuperarSenha extends AppCompatActivity {
    private EditText email_recuperar;
    private ImageButton voltar_login;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        email_recuperar = findViewById(R.id.text_email_recuperarSenha);
        progressBar = findViewById(R.id.progress_recuperar_senha);

    }

    public void ValidaDado(View view){
        String email = email_recuperar.getText().toString().trim();

        if(!email.isEmpty()){

            progressBar.setVisibility(View.VISIBLE);
            enviarEmailDeRecuperacao(email);
        }else {
        email_recuperar.requestFocus();
        email_recuperar.setError("Digite seu email");
        }
    }

    private void enviarEmailDeRecuperacao(String email){
        FirebaseHelper.getAuth().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Email enviado com sucesso", Toast.LENGTH_LONG).show();
            }else {
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

        });
    }


    public void backToLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

}