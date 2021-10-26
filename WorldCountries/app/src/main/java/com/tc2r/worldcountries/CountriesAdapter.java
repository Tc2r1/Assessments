package com.tc2r.worldcountries;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tc2r.worldcountries.Model.Country;

import java.util.ArrayList;

/**
 * Created by tc2r on 6/29/17.
 */

class CountriesAdapter extends RecyclerView.Adapter <CountriesAdapter.CountryViewHolder> {


    private ArrayList<Country> countriesList;
    private Context context;

    public CountriesAdapter(ArrayList<Country> countriesList, Context context){
        this.countriesList = countriesList;
        this.context = context;
    }
    @Override


    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutId = R.layout.country_layout;
        View view = inflater.inflate(layoutId, parent, false);
        CountryViewHolder viewHolder = new CountryViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, final int position) {
        holder.tvCountryName.setText(countriesList.get(position).getName());

        holder.tvCountryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(context.getString(R.string.country_intent_label), countriesList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        TextView tvCountryName;

        public CountryViewHolder(View itemView) {
            super(itemView);
            tvCountryName = (TextView) itemView.findViewById(R.id.tv_country_name);
        }
    }

}
