package br.rafaelhorochovec.app_streetfighter_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import br.rafaelhorochovec.app_streetfighter_android.R;
import br.rafaelhorochovec.app_streetfighter_android.pojo.Fighter;

public class FighterAdapter extends RecyclerView.Adapter<FighterAdapter.MyViewHolder> implements Filterable {

    private List<Fighter> fighters;
    private List<Fighter> fightersFiltered;
    private Context context;

    public void setFighterList(Context context, final List<Fighter> fighters){
        this.context = context;
        if(this.fighters == null){
            this.fighters = fighters;
            this.fightersFiltered = fighters;
            notifyItemChanged(0, fightersFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return FighterAdapter.this.fighters.size();
                }

                @Override
                public int getNewListSize() {
                    return fighters.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return FighterAdapter.this.fighters.get(oldItemPosition).getName() == fighters.get(newItemPosition).getName();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Fighter newFighter = FighterAdapter.this.fighters.get(oldItemPosition);
                    Fighter oldFighter = fighters.get(newItemPosition);
                    return newFighter.getName() == oldFighter.getName() ;
                }
            });
            this.fighters = fighters;
            this.fightersFiltered = fighters;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_custom_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FighterAdapter.MyViewHolder holder, int position) {
        holder.name.setText(fightersFiltered.get(position).getName());
        holder.country.setText(fightersFiltered.get(position).getCountry());
        Glide.with(context).load(fighters.get(position).getThumbnailUrl()).apply(RequestOptions.centerCropTransform()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if(fighters != null){
            return fightersFiltered.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    fightersFiltered = fighters;
                } else {
                    List<Fighter> filteredList = new ArrayList<>();
                    for (Fighter fighter : fighters) {
                        if (fighter.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(fighter);
                        }
                    }
                    fightersFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = fightersFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                fightersFiltered = (ArrayList<Fighter>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView country;
        ImageView image;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            country = (TextView) view.findViewById(R.id.country);
            image = (ImageView)view.findViewById(R.id.thumbnailUrl);
        }
    }
}
