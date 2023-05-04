package com.example.yourlocker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourlocker.Model.Room;
import com.example.yourlocker.Model.UserDto;
import com.example.yourlocker.R;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private List<Room> place;
    Context context;
//    ItemClicked activity;

    public interface ItemClicked{
        void onItemClick(int index);
    }

    public RoomAdapter(Context context, List<Room> list)
    {
        place = list;
//        activity = (ItemClicked) context;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_home;
        TextView tv_place, tv_explain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_place = itemView.findViewById(R.id.tv_place);
            tv_explain = itemView.findViewById(R.id.tv_explain);
            iv_home = itemView.findViewById(R.id.iv_home);



//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    activity.onItemClick(place.indexOf((Room) v.getTag()));
//                }
//            });

        }
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_items_place, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder holder, int position) {

//
        Room Room = place.get(position);
        holder.tv_place.setText(Room.getLugar());


    }

    @Override
    public int getItemCount() {
        return place.size();
    }
}
