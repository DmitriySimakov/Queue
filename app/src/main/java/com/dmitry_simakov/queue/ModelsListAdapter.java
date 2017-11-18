package com.dmitry_simakov.queue;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmitry_simakov.queue.models.ModelAB;

import static com.dmitry_simakov.queue.fragments.MainActivityFragment.MODELS;

public class ModelsListAdapter extends RecyclerView.Adapter<ModelsListAdapter.ViewHolder> {
    
    private Context context;
    
    public ModelsListAdapter(Context context) {
        this.context = context;
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        TextView modelName;
        ImageView image;
        
        private View.OnClickListener clickListener;
        
        public ViewHolder(View itemView) {
            super(itemView);
            
            modelName = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.itemImage);
            
            itemView.setOnClickListener(this);
        }
    
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ModelActivity.class);
            intent.putExtra(ModelActivity.MODEL_ID, getAdapterPosition());
            context.startActivity(intent);
        }
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_models_list, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelAB model = MODELS[position];
        holder.modelName.setText(model.getName());
        holder.image.setImageResource(model.getModelImage());
    }
    
    @Override
    public int getItemCount() {
        return MODELS.length;
    }
}
