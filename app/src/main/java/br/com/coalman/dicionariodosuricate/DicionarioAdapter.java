package br.com.coalman.dicionariodosuricate;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.coalman.dicionariodosuricate.model.Dicionario;

@SuppressLint("ViewHolder") public class DicionarioAdapter extends BaseAdapter {

    private List<Dicionario> dicionarios;
    //private Context context;
    private LayoutInflater inflater;




    public DicionarioAdapter(Context context, List<Dicionario> dicionarios) {
        super();
        this.dicionarios = dicionarios;
        inflater = LayoutInflater.from(context);
        //this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dicionarios.size();
    }

    //Retorna o id do item
    @Override
    public Dicionario getItem(int position) {

        return dicionarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Dicionario dicionario = dicionarios.get(position);

        convertView = inflater.inflate(R.layout.item, null);

        TextView palavra = (TextView) convertView.findViewById(R.id.palavra);

        palavra.setText(dicionario.getPalavra());

        return convertView;
    }


}
