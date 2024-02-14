package com.example.listagem.Model;

import com.example.listagem.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Produto implements Serializable {
    private String id;

    private String nome;
    private int estoque;
    private double valor;

    public Produto() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }

    public void SalvarProduto(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("produtos")
                .child(FirebaseHelper.getUserId())
                .child(this.id);
        reference.setValue(this);
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
