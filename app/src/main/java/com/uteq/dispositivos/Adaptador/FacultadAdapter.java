package com.uteq.dispositivos.Adaptador;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.uteq.dispositivos.Modelo.Facultad;
import com.uteq.dispositivos.R;

import java.util.List;

public class FacultadAdapter extends RecyclerView.Adapter<FacultadAdapter.DispositivosViewHolder> {

    private List<Facultad> facultades;
    private OnItemClickListener onItemClickListener;

    public FacultadAdapter(List<Facultad> facultades, OnItemClickListener onItemClickListener) {
        this.facultades = facultades;
        this.onItemClickListener = onItemClickListener;
    }

    public static class DispositivosViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public ImageView foto;

        public DispositivosViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtFacultad);
            foto = itemView.findViewById(R.id.imgFacultad);
        }
    }

    @Override
    public DispositivosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facultades, parent, false);
        return new DispositivosViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(DispositivosViewHolder holder, int position) {
        Facultad facultad = facultades.get(position);
        holder.nombre.setText(facultad.getNombre());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(facultad.getId_facultad(), false, v));

        holder.itemView.setOnLongClickListener(v -> {
            onItemClickListener.onItemClick(facultad.getId_facultad(), true, v);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return facultades.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int id, boolean isLongClick, View view);
    }
}
