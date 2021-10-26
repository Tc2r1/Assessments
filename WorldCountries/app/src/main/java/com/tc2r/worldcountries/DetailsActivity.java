package com.tc2r.worldcountries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tc2r.worldcountries.Model.Country;

public class DetailsActivity extends AppCompatActivity {

	TextView tvNativeName, tvCapitalCity, tvPopulation, tvRegion,
					tvSubRegion, tvArea, tvCurrencies, tvLanguages, tvNeighbors;

	Country country;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			country = bundle.getParcelable(getString(R.string.country_intent_label));
		}

		setTitle(country.getName());

		initVariables();

		setDisplayInfo();
	}

	private void setDisplayInfo() {
		tvNativeName.setText(country.getName());
		tvCapitalCity.setText(country.getCapital());
		tvPopulation.setText(String.valueOf(country.getPopulation()));
		tvRegion.setText(country.getRegion());
		tvSubRegion.setText(country.getSubregion());
		tvArea.setText(String.valueOf(country.getArea()) + getString(R.string.country_area_measurement));
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < country.getCurrencies().size(); i++) {
			builder.append(country.getCurrencies().get(i));

			if (country.getCurrencies().size()> 1 && i < country.getCurrencies().size() - 1) {
				builder.append(", ");
			}
		}
		tvCurrencies.setText(builder);


		builder = new StringBuilder();
		for (int i = 0; i < country.getLanguages().size(); i++) {
			builder.append(country.getLanguages().get(i));
			if (country.getLanguages().size()> 1 && i < country.getLanguages().size() - 1) {
				builder.append(", ");
			}
		}
		tvLanguages.setText(builder);


		builder = new StringBuilder();
		for (int i = 0; i < country.getBorders().size(); i++) {
			builder.append(country.getBorders().get(i));

			if (country.getBorders().size()> 1 && i < country.getBorders().size() - 1) {
				builder.append(", ");
			}
		}
		tvNeighbors.setText(builder);
	}

	private void initVariables() {
		tvNativeName = (TextView) findViewById(R.id.tv_nat_name);
		tvCapitalCity = (TextView) findViewById(R.id.tv_capital);
		tvPopulation = (TextView) findViewById(R.id.tv_population);
		tvRegion = (TextView) findViewById(R.id.tv_region);
		tvSubRegion = (TextView) findViewById(R.id.tv_sub_region);
		tvArea = (TextView) findViewById(R.id.tv_area);
		tvCurrencies = (TextView) findViewById(R.id.tv_currencies);
		tvLanguages = (TextView) findViewById(R.id.tv_languages);
		tvNeighbors = (TextView) findViewById(R.id.tv_neighbors);
	}
}
