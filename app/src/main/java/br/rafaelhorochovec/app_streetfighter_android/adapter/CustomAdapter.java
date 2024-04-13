package br.rafaelhorochovec.app_streetfighter_android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import br.rafaelhorochovec.app_streetfighter_android.R;
import br.rafaelhorochovec.app_streetfighter_android.pojo.Fighter;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Fighter> fighters;
    private Context context;

    public CustomAdapter(Context context,List<Fighter> fighters){
        this.context = context;
        this.fighters = fighters;
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtName;
        TextView txtCountry;
        ImageView imgAvatar;

        @SuppressLint("WrongViewCast")
        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtName = mView.findViewById(R.id.name);
            txtCountry = mView.findViewById(R.id.country);
            imgAvatar = mView.findViewById(R.id.thumbnailUrl);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtName.setText(fighters.get(position).getName());
        holder.txtCountry.setText(fighters.get(position).getCountry());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(fighters.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return fighters.size();
    }
}
