package br.com.coalman.dicionariodosuricate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


import java.util.List;

import br.com.coalman.dicionariodosuricate.dao.DatabaseHelper;
import br.com.coalman.dicionariodosuricate.dao.DicionarioDAO;
import br.com.coalman.dicionariodosuricate.model.Dicionario;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper helper;
    //private EditText editText;
    public final static String EXTRA_PALAVRA="br.com.coalman.dicionarioceares.PALAVRA";
    private String palavra;
    private View view;
    CustomAutoCompleteView myAutoComplete;
    DicionarioDAO dao;
    ArrayAdapter adapter;
    //Valor inicial
    String[] item = new String[] {"Pesquise uma Palavra..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        inicializar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.meuToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Todas as Palavras");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withName("Sair");

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position){
                            case 0:
                                listarPalavras(view);
                                break;
                            case 1:
                                finish();
                                break;
                        }

                        return false;

                    }
                })
                .build();


        /*editText = (EditText) findViewById(R.id.editText1);

        editText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return false;
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    palavra = editText.getText().toString();
                    enviarPalavra(view);
                    return true;
                }
                return false;
            }
        });*/
    }

    public void inicializar(){

        DicionarioDAO.copiaBanco(getBaseContext(), "database.db");

        dao = new DicionarioDAO(this);
        dao.open();

        try{

            // instantiate database handler
            helper = new DatabaseHelper(this);

            // autocompletetextview is in activity_main.xml
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);

            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

            // set our adapter
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(adapter);


        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this function is used in CustomAutoCompleteTextChangedListener.java
    public String[] getItemsFromDb(String palavraBuscada){

        // add items on the array dynamically
        List<Dicionario> dicionarios = dao.listarRelacionados(palavraBuscada);

        int rowCount = dicionarios.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (Dicionario dicionario : dicionarios) {

            item[x] = dicionario.getPalavra();
            x++;
        }

        return item;
    }

    public void enviarPalavra(View view){

        //palavra = editText.getText().toString();
        palavra = myAutoComplete.getText().toString();
        Intent intent = new Intent(this,Significado.class);
        intent.putExtra("EXTRA_PALAVRA", palavra);
        startActivity(intent);

    }

    public void listarPalavras(View view){
        //Chama a activity ListarPalavrasActivity
        Intent intent = new Intent(this,ListarPalavras.class);
        startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
