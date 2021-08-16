package com.example.quickrepair.view.Technician.AddEditArea;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickrepair.R;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder>
{
    private List<String> areas;

    AreaAdapter(List<String> areas)
    {
        this.areas = areas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final String area = areas.get(position);

        holder.area.setText(area);
    }

    @Override
    public int getItemCount()
    {
        return areas == null ? 0 : areas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ViewGroup listItem;
        public TextView area;

        ViewHolder(ViewGroup viewGroup)
        {
            super(viewGroup);

            listItem = viewGroup;
            area = listItem.findViewById(R.id.AreaTextView);
        }
    }
}
