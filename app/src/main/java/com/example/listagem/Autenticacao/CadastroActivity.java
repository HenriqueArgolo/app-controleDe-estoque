package com.example.listagem.Autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import com.example.listagem.Activity.MainActivity;
import com.example.listagem.Helper.FirebaseHelper;
import com.example.listagem.Model.Usuario;
import com.example.listagem.R;

import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {
    private EditText nome, email, senha;

    private Button btn_cadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

       iniciaElementos();

       btn_cadastro.setOnClickListener(view -> {
           validaDados();
       });


    }


    public void iniciaElementos(){
        nome = findViewById(R.id.text_criar_nome);
        email = findViewById(R.id.text_criar_email);
        senha = findViewById(R.id.text_criar_senha);
        btn_cadastro = findViewById(R.id.btn_cadastrar_user);

    }

    public void validaDados(){
        String text_nome = nome.getText().toString();
        String text_email = email.getText().toString();
        String text_senha = senha.getText().toString();


            if(!text_nome.isEmpty()){
                if(!text_email.isEmpty()){
                    if(!text_senha.isEmpty()){

                        Usuario usuario = new Usuario();
                        usuario.setNome(text_nome);
                        usuario.setEmail(text_email);
                        usuario.setSenha(text_senha);

                        SalvarCdastro(usuario);

                    }else {
                        senha.requestFocus();
                        senha.setError("Digite uma senha");
                    }

                }else{
                    email.requestFocus();
                    email.setError("digite seu email");
                }
            }else {
                nome.requestFocus();
                nome.setError("digite seu nome");
            }
        }


    private  void SalvarCdastro(Usuario usuario){
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                usuario.setId(id);

                finish();
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
    }


    public void backToLogin2(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
