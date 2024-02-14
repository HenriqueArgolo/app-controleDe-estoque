package com.example.listagem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.listagem.Adapter.AdapterProduto;
import com.example.listagem.Autenticacao.LoginActivity;
import com.example.listagem.Helper.FirebaseHelper;
import com.example.listagem.Model.Produto;
import com.example.listagem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterProduto.OnClick {
    private AdapterProduto adapterProduto;
    private ImageButton addButton;
    private ImageButton optionMenu;
    private SwipeableRecyclerView recyclerView;
    protected List<Produto> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.add_product);
        optionMenu = findViewById(R.id.meu_options);
        recyclerView = findViewById(R.id.recycler_view);

        configRecyclerView();
        eventoDeClick();

    }

    @Override
    protected void onStart() {
        super.onStart();
        RecuperarDados();
    }

    private void configRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapterProduto = new AdapterProduto(productList, this);
        recyclerView.setAdapter(adapterProduto);

        recyclerView.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
            }

            @Override
            public void onSwipedRight(int position) {
            }
        });
    }

    private void RecuperarDados() {
        DatabaseReference produtosReference = FirebaseHelper.getDatabaseReference()
                .child("produtos")
                .child(FirebaseHelper.getUserId());
        produtosReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Produto produto = snap.getValue(Produto.class);
                    productList.add(produto);
                }
                Collections.reverse(productList);
                adapterProduto.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void eventoDeClick() {
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, Form.class);
            startActivity(intent);
        });
        optionMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, optionMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_options, popupMenu.getMenu());


            popupMenu.setOnMenuItemClickListener(menuItem -> {
                        if (menuItem.getItemId() == R.id.sair) {
                            FirebaseHelper.getAuth().signOut();
                            startActivity(new Intent(this, LoginActivity.class));
                        }

                        return true;
                    }
            );

            popupMenu.show();
        });
    }

    @Override
    public boolean onCreateOptionmenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onCreateNewMneu(Menu menu) {
        return false;
    }

    @Override
    public void onClickListner(Produto produto) {
        Intent intent = new Intent(this, EditarProduto.class);
        intent.putExtra("produto_id", produto.getId());
        intent.putExtra("produto", produto);
        startActivity(intent);

    }



}