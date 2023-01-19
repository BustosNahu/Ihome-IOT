package com.example.yourlocker.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourlocker.Espacio;
import com.example.yourlocker.R;

import java.util.ArrayList;

public class EspacioAdapter extends RecyclerView.Adapter<EspacioAdapter.ViewHolder> {

    private ArrayList<Espacio> place;
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClick(int index);
    }

    public EspacioAdapter(Context context, ArrayList<Espacio> list)
    {
        place = list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_home;
        TextView tv_place, tv_explain;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_place = itemView.findViewById(R.id.tv_place);
            tv_explain = itemView.findViewById(R.id.tv_explain);
            iv_home = itemView.findViewById(R.id.iv_home);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClick(place.indexOf((Espacio) v.getTag()));
                }
            });

        }
    }

    @NonNull
    @Override
    public EspacioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_place, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EspacioAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(place.get(position));

        holder.tv_place.setText(place.get(position).getLugar());

    }

    @Override
    public int getItemCount() {
        return place.size();
    }
}
