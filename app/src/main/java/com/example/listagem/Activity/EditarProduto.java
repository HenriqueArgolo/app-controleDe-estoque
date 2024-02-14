package com.example.listagem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listagem.Adapter.AdapterProduto;
import com.example.listagem.Model.Produto;
import com.example.listagem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EditarProduto extends AppCompatActivity {
    private EditText editar_nome;
    private EditText editar_estoque;

    private EditText editar_valor;

    private Button edit_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produto);
        iniciarElementos();

        Bundle bundle = getIntent().getExtras();
        String prodtudoId = getIntent().getStringExtra("produto_id");
        Produto produto = (Produto) bundle.getSerializable("produto");
        transferirDados(produto);

        edit_button.setOnClickListener(view -> {
            editarProduto(prodtudoId);
        });

    }

    public void editarProduto(String productId) {
        DatabaseReference produtosRef = FirebaseDatabase.getInstance().getReference().child("produtos").child(productId);

String nome = editar_nome.getText().toString();
String estoque = editar_estoque.getText().toString();
String valor =editar_valor.getText().toString();

        Map<String, Object> atualizacaoProduto = new HashMap<>();
        atualizacaoProduto.put("nome", nome);
        atualizacaoProduto.put("estoque", estoque);
        atualizacaoProduto.put("valor", valor);

        produtosRef.updateChildren(atualizacaoProduto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(getApplicationContext(), "Produto atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(), "Erro ao atualizar o produto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void iniciarElementos(){
        editar_nome = findViewById(R.id.edit_nome);
        editar_estoque = findViewById(R.id.edit_qtd);
        editar_valor = findViewById(R.id.edit_valor);
        edit_button = findViewById(R.id.edit_button);

    }

    private void transferirDados(Produto produto){
        editar_nome.setText(produto.getNome());
        editar_estoque.setText(String.valueOf(produto.getEstoque()));
        editar_valor.setText(String.valueOf(produto.getValor()));
    }
}