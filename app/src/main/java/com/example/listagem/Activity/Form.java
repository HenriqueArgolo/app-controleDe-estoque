package com.example.listagem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.listagem.Model.Produto;
import com.example.listagem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Form extends AppCompatActivity implements Serializable {
    private ImageButton voltar;
    private EditText nomeProduto;
    private EditText qtdEstoque;

    private Button cadastroButton;

    private EditText valor_produto;

    Produto produto = new Produto();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);



        iniciaComponentes();
        voltaMain();
        cadastrarProduto();


    }
    public void iniciaComponentes() {
        voltar = findViewById(R.id.button_voltar);
        nomeProduto = findViewById(R.id.form_nome);
        qtdEstoque = findViewById(R.id.form_qtd);
        valor_produto = findViewById(R.id.form_valor);
        cadastroButton = findViewById(R.id.btn_cadastrar);
    }

    public void cadastrarProduto() {

        cadastroButton.setOnClickListener(View -> {
            String nome = nomeProduto.getText().toString();
            String quantidade = qtdEstoque.getText().toString();
            String valor = valor_produto.getText().toString();

            if (!nome.isEmpty()) {
                if (!quantidade.isEmpty()) {
                    int qtd = Integer.parseInt(quantidade);
                    if (qtd >= 1) {
                        if (!valor.isEmpty()) {
                            double preco = Double.parseDouble(valor);
                            if (preco >= 1) {
                                Produto produto = new Produto();
                                produto.setNome(nome);
                                produto.setEstoque(qtd);
                                produto.setValor(Double.parseDouble(valor));

                                produto.SalvarProduto();
                                finish();
                            } else {
                                valor_produto.requestFocus();
                                valor_produto.setError("O valor não pode ser igual a zero");
                            }

                        } else {
                            valor_produto.requestFocus();
                            valor_produto.setError("Digite o valor");
                        }

                    } else {
                        qtdEstoque.requestFocus();
                        qtdEstoque.setError("A quantidade tem que ser igual ou maior que 1");
                    }
                } else {
                    qtdEstoque.requestFocus();
                    qtdEstoque.setError("Digite a quantidade");
                }
            } else {
                nomeProduto.requestFocus();
                nomeProduto.setError("Dige um nome");

            }
        });

    }

    public void voltaMain() {
        voltar.setOnClickListener(View -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        });
    }



}