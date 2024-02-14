package com.example.listagem.Adapter;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listagem.Model.Produto;
import com.example.listagem.R;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder> {

    private List<Produto> listaProdutos;
    private OnClick onClick;


    public AdapterProduto(List<Produto> listaProdutos, OnClick onClick) {
        this.listaProdutos = listaProdutos;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_product, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Produto produto = listaProdutos.get(position);
        holder.bind(produto);
        holder.itemView.setOnClickListener(view -> onClick.onClickListner(produto));
    }

    @Override
    public int getItemCount() {

        return listaProdutos.size();
    }

    public interface OnClick{
        boolean onCreateOptionmenu(Menu menu);

        boolean onCreateNewMneu(Menu menu);

        void onClickListner(Produto produto);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeProduto;
        private TextView estoque;
        private TextView valor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.nome_produto);
            estoque = itemView.findViewById(R.id.estoque);
            valor = itemView.findViewById(R.id.valor);
        }


        public void bind(Produto produto) {
            nomeProduto.setText(produto.getNome());
            estoque.setText("Estoque " + produto.getEstoque());
            valor.setText("R$ " + produto.getValor());
        }
    }
}
