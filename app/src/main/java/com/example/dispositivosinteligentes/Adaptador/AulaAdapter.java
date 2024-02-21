package com.example.dispositivosinteligentes.Adaptador;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dispositivosinteligentes.Modelo.Aula;
import com.example.dispositivosinteligentes.R;

import java.util.List;

public class AulaAdapter extends RecyclerView.Adapter<AulaAdapter.DispositivosViewHolder> {

    private List<Aula> aula;
    private OnItemClickListener onItemClickListener;

    public AulaAdapter(List<Aula> aula, OnItemClickListener onItemClickListener) {
        this.aula = aula;
        this.onItemClickListener = onItemClickListener;
    }

    public static class DispositivosViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public ImageView foto;

        public DispositivosViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtAula);
            foto = itemView.findViewById(R.id.imgAula);
        }
    }

    @Override
    public DispositivosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aulas, parent, false);
        return new DispositivosViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(DispositivosViewHolder holder, int position) {
        Aula aulas = aula.get(position);
        holder.nombre.setText(aulas.getNombre());

        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(aulas.getId_aula(), false, v);
            });

        holder.itemView.setOnLongClickListener(v -> {
            onItemClickListener.onItemClick(aulas.getId_aula(), true, v);
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return aula.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int id, boolean isLongClick, View view);
    }
}
