package com.example.yourlocker.Adapter;

import static com.example.yourlocker.Utils.Utils.BED_ROOM;
import static com.example.yourlocker.Utils.Utils.GARAGE;
import static com.example.yourlocker.Utils.Utils.HOME_OUTSIDE;
import static com.example.yourlocker.Utils.Utils.KITCHEN;
import static com.example.yourlocker.Utils.Utils.LIVING_ROOM;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourlocker.Model.Room;
import com.example.yourlocker.Model.UserDto;
import com.example.yourlocker.R;
import com.google.android.material.drawable.DrawableUtils;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private List<Room> place;

    private ItemClickListener itemClickListener;

    public RoomAdapter(List<Room> list, ItemClickListener itemClickListener)
    {
        this.place = list;
        this.itemClickListener = itemClickListener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_home;
        TextView tv_place, tv_explain;
        ImageView room_background;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_place = itemView.findViewById(R.id.tv_place);
            tv_explain = itemView.findViewById(R.id.tv_explain);
            room_background = itemView.findViewById(R.id.room_background);




        }
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_place, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

//
        Room Room = place.get(position);
        holder.tv_place.setText(Room.getRoom());

        switch (Room.getType()){
            case BED_ROOM:  {
                Drawable drawable = holder.itemView.getResources().getDrawable(R.drawable.room_pic);

                holder.room_background.setImageDrawable(drawable);

                break;
            }
            case LIVING_ROOM:  {
                holder.room_background.setBackgroundResource(R.drawable.living_pic);
                break;
            }
            case KITCHEN:  {
                holder.room_background.setBackgroundResource(R.drawable.kitchen_pic);
                break;
            }
//            case GARAGE:  {
//                holder.room_background.setBackgroundResource(R.drawable.);
//                break;
//            }
//            case HOME_OUTSIDE:  {
//                holder.room_background.setBackgroundResource(R.drawable.);
//                break;
//            }
            default: {
//                holder.room_background.setBackgroundColor();
                Drawable drawable = holder.itemView.getResources().getDrawable(R.drawable.living_pic);

                holder.room_background.setImageDrawable(drawable);
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(place.get(position));


                }
            });


    }

    @Override
    public int getItemCount() {
        return place.size();
    }

    public interface ItemClickListener {
        void onItemClick(Room room);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


}
