package com.example.listagem.Autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listagem.Activity.MainActivity;
import com.example.listagem.Helper.FirebaseHelper;
import com.example.listagem.R;
import com.example.listagem.RecuperarSenha;
import com.google.android.material.badge.BadgeUtils;

public class LoginActivity extends AppCompatActivity {

    private TextView cadastro;
    private EditText emal_login;
    private EditText senha_login;
    private TextView esqueceu_senha;
    private Button botao_logar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        configClick();


        cadastro.setOnClickListener(View -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        });

        esqueceu_senha.setOnClickListener(view ->{
            startActivity(new Intent(this, RecuperarSenha.class));
        });

        botao_logar.setOnClickListener(view -> {
            Login();
        });
    }

    private void configClick() {
        progressBar = findViewById(R.id.progressBar);
        esqueceu_senha = findViewById(R.id.esqueceu_senha);
        emal_login = findViewById(R.id.text_email);
        senha_login = findViewById(R.id.text_senha);
        progressBar = findViewById(R.id.progressBar);
        botao_logar = findViewById(R.id.btn_logar);
        cadastro = findViewById(R.id.text_cadastro);
    }

    public void Login() {
        String email = emal_login.getText().toString().trim();
        String senha = senha_login.getText().toString();


        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                LoginAutentication(email, senha);
            } else {
                senha_login.requestFocus();
                senha_login.setError("Senha inválida");
            }
        } else {
            emal_login.requestFocus();
            emal_login.setError("Email inválido");

        }
    }

    public void LoginAutentication(String email, String senha){
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }else {
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

        });
    }
}