package com.assessment.tc2r.grossfilms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.assessment.tc2r.grossfilms.adapter.FilmAdapter;
import com.assessment.tc2r.grossfilms.models.Film;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
	private static final String TAG = "DisplayActivity";

	ArrayList<Film> filmList;
	RecyclerView recyclerView;
	FilmAdapter adapter;
	LinearLayoutManager layoutManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		filmList = getIntent().getParcelableArrayListExtra(getString(R.string.films_label));

		Log.wtf(TAG, String.valueOf(filmList.size()));

		if (filmList == null) {
			return;
		}
		recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
		adapter = new FilmAdapter(filmList);
		layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);
	}
}
