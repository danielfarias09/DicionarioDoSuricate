package br.com.coalman.dicionariodosuricate;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import br.com.coalman.dicionariodosuricate.dao.DicionarioDAO;

public class Significado extends AppCompatActivity {

    private DicionarioDAO dao;
    private AlertDialog alerta;
    private String palavra;
    private String significado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_significado);

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


        //Usando a fonte de giz que está na pasta assets
        Typeface font = Typeface.createFromAsset(getAssets(), "Gapstown.ttf");
        TextView textview1 = (TextView) findViewById(R.id.palavra);
        TextView textview2 = (TextView) findViewById(R.id.significado);
        textview1.setTypeface(font);
        textview2.setTypeface(font);

        //Recebe a mensagem da intent
        Intent intent = getIntent();
        palavra = intent.getStringExtra("EXTRA_PALAVRA");
        palavra = palavra.trim();
        significado = dao.buscaPalavra(palavra);
        //Remove espaços do final e do início da palavra

        if (significado == null) {
            mostrarAlerta();
        } else {
            textview1.setText("Significado de " + palavra + " :");
            textview2.setText(significado);

            dao.close();
        }
    }

        private void mostrarAlerta(){
            //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define o titulo
            builder.setTitle("Palavra Não Encontrada");
            //define a mensagem
            builder.setMessage("Desculpe, mas essa palavra não existe no nosso banco da dados");
            builder.setNeutralButton("Voltar", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();






        }

    }


