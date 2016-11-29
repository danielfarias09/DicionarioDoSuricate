package br.com.coalman.dicionariodosuricate.dao;

/**
 * Created by Daniel on 15/11/2015.
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String TABELA_DICIONARIO = "significados";
    public static final String COLUNA_ID = "_id ";
    public static final String COLUNA_PALAVRA = "palavra ";
    public static final String COLUNA_SIGNIFICADO = "significado ";
    //Nome do Banco
    private static final String BANCO_DADOS = "database.db";
    private static final int VERSAO = 1;


    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        //String ddl ="create table significados(_id integer primary key autoincrement ,palavra text unique , significado text);";

        //db.execSQL(ddl);


    }

    //Futuras Atualizações
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "drop table if exists significados";
        db.execSQL(sql);
        onCreate(db);
        // TODO Auto-generated method stub

    }
}