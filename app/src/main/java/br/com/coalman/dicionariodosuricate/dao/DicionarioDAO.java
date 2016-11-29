package br.com.coalman.dicionariodosuricate.dao;

/**
 * Created by Daniel on 15/11/2015.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.coalman.dicionariodosuricate.model.Dicionario;


public class DicionarioDAO {

    public final static String NOME_TABELA="significados";
    public final static String COLUNA_PALAVRA="palavra";
    public final static String COLUNA_SIGNIFICADO="significado";
    private  SQLiteDatabase database;
    private DatabaseHelper helper;



    public DicionarioDAO(Context contexto){

        helper = new DatabaseHelper(contexto);


    }



    public void open() throws SQLException{
        database = helper.getWritableDatabase();


    }


    public void close(){
        helper.close();

    }


    public List<Dicionario> listar(){

        List<Dicionario> dicionarios = new ArrayList<Dicionario>();

        String sql="select * from significados order by palavra";
        Cursor cursor = database.rawQuery(sql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Dicionario dicionario = new Dicionario();
            dicionario.setId(cursor.getLong(0));
            dicionario.setPalavra(cursor.getString(1));
            dicionario.setSignificado(cursor.getString(2));
            dicionarios.add(dicionario);
            cursor.moveToNext();
        }
        cursor.close();
        return dicionarios;
    }

    // Lista de acordo com o que o usuário digitar no campo de pesquisa
    public List<Dicionario> listarRelacionados(String termoBuscado) {

        List<Dicionario> lista = new ArrayList<Dicionario>();

        String sql = "";
        sql += "SELECT * FROM " + DatabaseHelper.TABELA_DICIONARIO;
        sql += " WHERE " + DatabaseHelper.COLUNA_PALAVRA + " LIKE '" + termoBuscado + "%'";
        sql += " ORDER BY " + DatabaseHelper.COLUNA_PALAVRA + " DESC";
        sql += " LIMIT 0,5";


        // execute the query
        Cursor cursor = database.rawQuery(sql,null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Dicionario dicionario = new Dicionario();
                dicionario.setId(cursor.getLong(0));
                dicionario.setPalavra(cursor.getString(1));
                dicionario.setSignificado(cursor.getString(2));
                lista.add(dicionario);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return lista;
    }





    public String buscaPalavra(String palavra) {


        database = helper.getReadableDatabase();
        String significado = null;


        // cursor = database.query(NOME_TABELA, colunas, where, argumentos, null, null, null);
        Cursor cursor = database.query("significados",new String[]{"significado"},"palavra = " + "'" + palavra + "'", null, null, null, null);
        //startManagingCursor(cursor);
        cursor.moveToFirst();
        //Se eu conseguir ir para o próximo registro
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            significado = cursor.getString(cursor.getColumnIndex("significado"));
            cursor.close();


        }
        //System.out.println(significado);

        return significado;

    }

    public static void copiaBanco(Context context, String nomeDB){

        // Cria o banco vazio
        SQLiteDatabase db = context.openOrCreateDatabase(
                nomeDB, Context.MODE_WORLD_WRITEABLE, null);
        db.close();

        try {
            // Abre o arquivo que deve estar na pasta assets
            InputStream is = context.getAssets().open(nomeDB);
            // Abre o arquivo do banco vazio ele fica em:
            // /data/data/nome.do.pacote.da.app/databases
            FileOutputStream fos = new FileOutputStream(context.getDatabasePath(nomeDB));
            // Copia byte a byte o arquivo do assets para
            // o aparelho/emulador
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) > 0){
                fos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

