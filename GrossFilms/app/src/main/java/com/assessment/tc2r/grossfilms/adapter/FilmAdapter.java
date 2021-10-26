package com.assessment.tc2r.grossfilms.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assessment.tc2r.grossfilms.R;
import com.assessment.tc2r.grossfilms.models.Film;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Tc2r on 6/11/2017.
 * <p>
 * Description:
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

	private ArrayList<Film> filmsList;
	private static int viewHolderCount;

	public FilmAdapter(ArrayList<Film> filmsList) {
		this.filmsList = filmsList;
		viewHolderCount = 0;
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView tvTitle, tvRank, tvDist, tvGross;

		public ViewHolder(View iV) {
			super(iV);

			tvRank = (TextView) iV.findViewById(R.id.tv_rank);
			tvTitle = (TextView) iV.findViewById(R.id.tv_title);
			tvDist = (TextView) iV.findViewById(R.id.tv_distributor);
			tvGross = (TextView) iV.findViewById(R.id.tv_gross);
		}
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_layout, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);


		// Quick Alternating Row Colors.
		int bgColorForViewHolder;
		if(viewHolderCount % 2 == 0) {
			 bgColorForViewHolder = ContextCompat.getColor(parent.getContext(), R.color.colorRV1);
		}else{
			bgColorForViewHolder = ContextCompat.getColor(parent.getContext(), R.color.colorRV2);
		}

		viewHolder.itemView.setBackgroundColor(bgColorForViewHolder);
		viewHolderCount++;
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.tvRank.setText(String.valueOf(filmsList.get(position).getRank()));
		holder.tvTitle.setText(filmsList.get(position).getTitle());
		holder.tvDist.setText(filmsList.get(position).getDistributor());

		Locale locale = Locale.US;
		NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
		String currencyText = fmt.format(filmsList.get(position).getGross());
		holder.tvGross.setText(currencyText);

	}


	@Override
	public int getItemCount() {
		return filmsList.size();
	}
}
