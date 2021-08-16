package com.example.quickrepair.view.Technician.RepairRequests;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quickrepair.R;
import com.example.quickrepair.domain.RepairRequest;


import java.util.ArrayList;
import java.util.List;

public class RepairRequestAdapter extends RecyclerView.Adapter<RepairRequestAdapter.ViewHolder>
{

    private List<RepairRequest> itemList;

    private ItemSelectionListener<RepairRequest> repairRequestItemSelectionListener;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        // each data item is just a string in this case,
        public ViewGroup listItem;
        public TextView txtRepairRequest;
        public ImageButton btnSelectRepairRequest;

        public ViewHolder(ViewGroup v)
        {
            super(v);
            listItem = v;
            txtRepairRequest = listItem.findViewById(R.id.txt_repair_request);
            btnSelectRepairRequest = listItem.findViewById(R.id.btn_select_repair_request);
        }
    }


    public RepairRequestAdapter(ArrayList<RepairRequest> repairRequests)
    {
        itemList = repairRequests;
    }

    public void setRepairRequestSelectionListener(ItemSelectionListener<RepairRequest> repairRequests)
    {
        this.repairRequestItemSelectionListener = repairRequests;
    }

    @Override
    public RepairRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create a new view for the list
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repair_request, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        //get Repair Request
        final RepairRequest repairRequestAtPosition = itemList.get(position);

        holder.txtRepairRequest.setText(repairRequestAtPosition.getJob().getJobType().getName() + "\nFrom: " + repairRequestAtPosition.getCustomer().getUsername());
        holder.btnSelectRepairRequest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (repairRequestItemSelectionListener != null)
                {
                    repairRequestItemSelectionListener.onItemSelected(repairRequestAtPosition);
                }
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount()
    {
        return itemList == null ? 0 : itemList.size();
    }


}
