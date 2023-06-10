package com.ismt.world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismt.world.Model.Placement_list_model;
import com.ismt.world.R;

import java.util.List;

public class PlacementListAdpater extends RecyclerView.Adapter<PlacementListAdpater.PlacementListViewHolder> {

    private Context context;
    private List<Placement_list_model> dataList;

    public PlacementListAdpater(Context context, List<Placement_list_model> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public PlacementListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.placement_n_sponsor_list_item, parent, false);
        return new PlacementListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacementListViewHolder holder, int position) {

        Placement_list_model placement_list_model = dataList.get(position);
        int serial_no=position+1;

        holder.serial.setText(""+serial_no);

        holder.userID.setText("User ID: \n"+placement_list_model.getUsername());
        holder.date.setText("Joining Date: \n"+placement_list_model.getJoinDate());
        holder.sponsor.setText("Sponsor ID: \n"+placement_list_model.getSponsorUserN());
        holder.designation.setText("Designation: \nMember");
//        holder.teamA.setText("Team A Carry: \n"+placement_list_model.getCarry1());
//        holder.teamB.setText("Team B Carry: \n"+placement_list_model.getCarry2());
//        holder.teamC.setText("Team C Carry: \n"+placement_list_model.getCarry3());

    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    public class PlacementListViewHolder extends RecyclerView.ViewHolder {

        Button serial;
        TextView userID, date, sponsor, designation, teamA, teamB, teamC;

        public PlacementListViewHolder(@NonNull View itemView) {
            super(itemView);

            serial = itemView.findViewById(R.id.tv_placemetn_list_SL);
            userID = itemView.findViewById(R.id.tv_placemetn_list_userID);
            date = itemView.findViewById(R.id.tv_placemetn_list_date);
            sponsor = itemView.findViewById(R.id.tv_placemetn_list_sponsor);
            designation = itemView.findViewById(R.id.tv_placemetn_list_designation);
//            teamA = itemView.findViewById(R.id.tv_placemetn_list_teamA);
//            teamB = itemView.findViewById(R.id.tv_placemetn_list_teamB);
//            teamC = itemView.findViewById(R.id.tv_placemetn_list_teamC);

        }
    }
}
