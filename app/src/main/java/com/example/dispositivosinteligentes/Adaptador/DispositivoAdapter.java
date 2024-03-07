package com.example.dispositivosinteligentes.Adaptador;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dispositivosinteligentes.Modelo.Dispositivo;
import com.example.dispositivosinteligentes.R;

import java.util.List;

public class DispositivoAdapter extends RecyclerView.Adapter<DispositivoAdapter.DispositivosViewHolder> {

    private List<Dispositivo> dispositivos;
    private OnItemClickListener onItemClickListener;

    public DispositivoAdapter(List<Dispositivo> dispositivos, OnItemClickListener onItemClickListener) {
        this.dispositivos = dispositivos;
        this.onItemClickListener = onItemClickListener;
    }

    public static class DispositivosViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        ImageView foto;

        public DispositivosViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreDispositivo);
            foto = itemView.findViewById(R.id.imgFotoDispositivo);
        }
    }

    @Override
    public DispositivosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int tipoDiseno = viewType == 1 ? R.layout.item_dispositivos : R.layout.item_dispositivos_2;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(tipoDiseno, parent, false);
        return new DispositivosViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(DispositivosViewHolder holder, int position) {
        Dispositivo dispositivo = dispositivos.get(position);
        holder.nombre.setText(dispositivo.getNombre());

        holder.itemView.setOnLongClickListener(v -> {
            onItemClickListener.onItemClick(dispositivo.getIdDispositivo(), position, 0, v);
            return true;
        });

        if (dispositivo.getModelo().equals("Tomacorriente")) {
            holder.foto.setImageResource(R.drawable.dispositivo_tomacorriente);
        } else if (dispositivo.getModelo().equals("ON_I_OFF_1") || dispositivo.getModelo().equals("ON_I_OFF_2")) {
            holder.foto.setImageResource(R.drawable.dispositivo_switch);
        } else if (dispositivo.getModelo().equals("IOT-BASED")) {
            holder.foto.setImageResource(R.drawable.dispositivo_breaker);
        } else if (dispositivo.getModelo().equals("Smart Touch")) {
            holder.foto.setImageResource(R.drawable.dispositivo_touch);
        }

        if (!dispositivo.getModelo().equals("Tomacorriente")) {
            ImageButton estado = holder.itemView.findViewById(R.id.btnBloquearDispositivo);
            ImageView bloqueo = holder.itemView.findViewById(R.id.imgBloqueo);
            bloqueo.setVisibility(View.GONE);
            if (dispositivo.isEstado()) {
                estado.setImageResource(R.drawable.dispositivo_bloquear);
                bloqueo.setVisibility(View.GONE);
            } else {
                estado.setImageResource(R.drawable.dispositivo_desbloquear);
                bloqueo.setVisibility(View.VISIBLE);
            }

            estado.setOnClickListener(v -> {
                onItemClickListener.onItemClick(dispositivo.getIdDispositivo(), position, 1, v);
            });
        } else {
            ImageButton estado1 = holder.itemView.findViewById(R.id.btnBloquearDispositivo_1);
            ImageButton estado2 = holder.itemView.findViewById(R.id.btnBloquearDispositivo_2);
            if (dispositivo.isEstado()) {
                estado1.setImageResource(R.drawable.dispositivo_bloquear);
            } else {
                estado1.setImageResource(R.drawable.dispositivo_desbloquear);
            }

            if (dispositivo.isEstado2()) {
                estado2.setImageResource(R.drawable.dispositivo_bloquear);
            } else {
                estado2.setImageResource(R.drawable.dispositivo_desbloquear);
            }

            estado1.setOnClickListener(v -> {
                onItemClickListener.onItemClick(dispositivo.getIdDispositivo(), position, 2, v);
            });

            estado2.setOnClickListener(v -> {
                onItemClickListener.onItemClick(dispositivo.getIdDispositivo(), position, 3, v);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        Dispositivo dispositivo = dispositivos.get(position);
        return !"Tomacorriente".equals(dispositivo.getModelo()) ? 1 : 2;
    }

    @Override
    public int getItemCount() {
        return dispositivos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int dispositivoId, int position, int type, View view);
    }
}
