package com.tc2r.worldcountries;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.tc2r.worldcountries.Model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private CountriesAdapter adapter;

	private ArrayList<Country> listOfCountries;
	private Context context;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;

		initVariables();

		parseLocalJSON();

		adapter = new CountriesAdapter(listOfCountries, this);
		recyclerView.setAdapter(adapter);
	}

	private void parseLocalJSON() {
		try {
			String jsonString = loadJSONFromAsset();
			JSONArray jsonArray = new JSONArray(jsonString);

			Country tempCountry;

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject country = jsonArray.getJSONObject(i);
				String tempNativeName = country.getString(getString(R.string.json_country_label_native));
				String tempName = country.getString(getString(R.string.json_country_label_name));
				String tempCapital = country.getString(getString(R.string.json_country_label_capital));
				int tempPopulation = country.getInt(getString(R.string.json_country_label_population));
				String tempRegion = country.getString(getString(R.string.json_country_label_region));
				String tempsubRegion = country.getString(getString(R.string.json_country_label_subregion));

				Double tempArea = 0.0;
				if (country.getString(getString(R.string.json_country_label_area)) != null &&
								!country.getString(getString(R.string.json_country_label_area)).equals(getString(R.string.json_country_area_null))) {

					tempArea = country.getDouble(getString(R.string.json_country_label_area));
				}

				JSONArray arr;
				List<String> tempNeighbors = new ArrayList<>();

				arr = country.getJSONArray(getString(R.string.json_country_label_borders));
				for (int j = 0; j < arr.length(); j++) {
					if (arr.get(j) != null) {
						tempNeighbors.add(String.valueOf(arr.get(j)));
					}
				}

				arr = country.getJSONArray(getString(R.string.json_country_label_currency));
				List<String> tempCurrencies = new ArrayList<>();
				for (int j = 0; j < arr.length(); j++) {
					if (arr.get(j) != null) {
						tempCurrencies.add(String.valueOf(arr.get(j)));
					}
				}

				arr = country.getJSONArray(getString(R.string.json_country_label_language));
				List<String> tempLanguages = new ArrayList<>();
				for (int j = 0; j < arr.length(); j++) {

					if (arr.get(j) != null) {
						tempLanguages.add(String.valueOf(arr.get(j)));
					}
				}

				tempCountry = new Country(tempName, tempCapital, tempRegion,
								tempsubRegion, tempPopulation, tempArea, tempNeighbors,
								tempNativeName, tempCurrencies, tempLanguages);

				listOfCountries.add(tempCountry);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initVariables() {
		listOfCountries = new ArrayList<>();
		recyclerView = (RecyclerView) findViewById(R.id.rv_countries);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setHasFixedSize(true);
	}

	public String loadJSONFromAsset() {
		String json = null;
		try {
			InputStream is = context.getAssets().open(getString(R.string.json_input_file_name));
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, getString(R.string.json_input_byte_type));

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}


}

