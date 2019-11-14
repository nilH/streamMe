package com.example.fodmap.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fodmap.R;
import com.example.fodmap.data.Ingredient;
import com.example.fodmap.ui.IngredientsActivity;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView safetyimg;
        TextView nametext;
        Button editbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            safetyimg=itemView.findViewById(R.id.item_safetyImg);
            nametext=itemView.findViewById(R.id.item_IngredientText);
            editbtn=itemView.findViewById(R.id.item_editBtn);
            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startEditaction(getAdapterPosition());
                }
            });
        }
    }
    List<Ingredient> datalist;
    IngredientsActivity context;
    public IngredientListAdapter(List<Ingredient> list){
        datalist=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=(IngredientsActivity) parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View ingredientView=layoutInflater.inflate(R.layout.item_ingredient,parent,false);
        return new ViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient item=datalist.get(position);
        holder.nametext.setText(item.getName());
        if(item.getSafety()== Ingredient.Safety.good){
            holder.safetyimg.setImageResource(R.color.safety_green);
        }else if(item.getSafety()==Ingredient.Safety.ok){
            holder.safetyimg.setImageResource(R.color.safety_yellow);
        }else {
            holder.safetyimg.setImageResource(R.color.safety_red);
        }
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public void update(List<Ingredient> list){
        datalist=list;
        notifyDataSetChanged();
    }

    public List<Ingredient> getDatalist(){
        return datalist;
    }
}
