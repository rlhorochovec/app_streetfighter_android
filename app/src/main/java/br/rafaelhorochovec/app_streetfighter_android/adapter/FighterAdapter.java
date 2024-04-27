package br.rafaelhorochovec.app_streetfighter_android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import br.rafaelhorochovec.app_streetfighter_android.R;
import br.rafaelhorochovec.app_streetfighter_android.pojo.Fighter;

public class FighterAdapter extends ArrayAdapter<Fighter> {
    private final Context context;
    private final List<Fighter> fighters;

    public FighterAdapter(Context context, List<Fighter> fighters) {
        super(context, R.layout.item_view, fighters);
        this.context = context;
        this.fighters = fighters;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_view, parent, false);

        ImageView thumbnailUrl = rowView.findViewById(R.id.imgThumbnailUrl);
        TextView name = rowView.findViewById(R.id.txtName);
        TextView country = rowView.findViewById(R.id.txtCountry);

        name.setText(fighters.get(position).getName());
        country.setText(fighters.get(position).getCountry());
        Glide.with(context).load(fighters.get(position).getThumbnailUrl()).apply(RequestOptions.centerCropTransform()).into(thumbnailUrl);

        return rowView;
    }
}
