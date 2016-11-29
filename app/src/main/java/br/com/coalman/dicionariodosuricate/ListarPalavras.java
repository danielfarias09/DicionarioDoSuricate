package br.com.coalman.dicionariodosuricate;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.coalman.dicionariodosuricate.dao.DicionarioDAO;
import br.com.coalman.dicionariodosuricate.model.Dicionario;

public class ListarPalavras extends AppCompatActivity {

    private DicionarioDAO dao;
    private List<Dicionario> dicionarios;
    //adapter mostra os objetos na tela
    private DicionarioAdapter adapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_palavras);

        Toolbar toolbar = (Toolbar) findViewById(R.id.meuToolbar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_18dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dao = new DicionarioDAO(this);
        dao.open();

        listView = (ListView) findViewById(R.id.lista);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String palavra =  dicionarios.get(position).getPalavra();

                Intent intent = new Intent(getApplicationContext(), Significado.class);
                intent.putExtra("EXTRA_PALAVRA", palavra);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onResume(){
        dao.open();
        carregarPalavras();
        super.onResume();

    }

    public void carregarPalavras(){

        this.dicionarios = dao.listar();
        dao.close();
        adapter = new DicionarioAdapter(this, dicionarios);
        listView.setAdapter(adapter);

    }





}
