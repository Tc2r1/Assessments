package com.assessment.tc2r.grossfilms;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.assessment.tc2r.grossfilms.adapter.FilmAdapter;
import com.assessment.tc2r.grossfilms.models.Film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* Instructor: Assessment
* With the following list of movies (attached image),
* create an Activity that includes a RecyclerView that
* shows on each item the name of the movie,
* the Distributor and the Gross money.
* Remember to use a ViewHolder and an Adapter.
*
*
* Notes on Project:
* Lets have some fun. So the quick way to do this would be just take data
* from a string array and display it with a recyclerview, but thats boring.
*
* Instead I'll populate a local database and a remote database with the movie data.
* then create a model object for movies, create an array of the models and fill it
* using the databases. And use this array to populate the recyclerView by passing the data
* through a custom adapter.
*
*
*/
public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";
	private static final int FILMS_LOADER = 20;

	private Button btnFromLocal, btnFromRemote, btnViewAll;
	private DatabaseHelper myDB;
	private ArrayList<Film> topFilms;
	private Film tempFilm;

	private RecyclerView recyclerView;
	private FilmAdapter adapter;

	private ProgressBar progressBar;
	private TextView tvError;


	private String[] mTitleArray, mDistArray, mGrossArray;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize Database
		myDB = new DatabaseHelper(this);

		// Initialize xml Objects
		btnFromLocal = (Button) findViewById(R.id.btn_fromlocal);
		btnFromRemote = (Button) findViewById(R.id.btn_fromremote);
		btnViewAll = (Button) findViewById(R.id.btn_viewAll);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		tvError = (TextView) findViewById(R.id.tv_error);


		// Initialize String Arrays to their xml counterparts.
		mTitleArray = getResources().getStringArray(R.array.movieTitle);
		mDistArray = getResources().getStringArray(R.array.movieDist);
		mGrossArray = getResources().getStringArray(R.array.movieGross);

		topFilms = new ArrayList<>();

		addDataToLocalTable();
		addDataFromRemoteTable();
		getAll();


	}

	private LoaderManager.LoaderCallbacks<JSONArray> grossFilmsLoaderCallbacks
					= new LoaderManager.LoaderCallbacks<JSONArray>() {
		@Override
		public Loader<JSONArray> onCreateLoader(int id, Bundle args) {
			return new AsyncTaskLoader<JSONArray>(getApplicationContext()) {

				@Override
				protected void onStartLoading() {
					progressBar.setVisibility(View.VISIBLE);
					forceLoad();
				}

				@Override
				public JSONArray loadInBackground() {
					Request request = new Request.Builder()
									.url(getString(R.string.get_url))
									.build();

					OkHttpClient client = new OkHttpClient();
					try {
						Response response = client.newCall(request).execute();
						JSONArray array = new JSONArray(response.body().string());
						return array;
					} catch (JSONException | IOException e) {
						Log.wtf(getString(R.string.http_response), e.toString());
						e.printStackTrace();
						return null;
					}
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {
			progressBar.setVisibility(View.INVISIBLE);

			if (data != null) {
				Log.v("DATA ", data.toString());

				try {
					for (int i = 0; i < data.length(); i++) {
						JSONObject object = data.getJSONObject(i);

						int rank = object.getInt("Rank");
						String title = object.getString("Title");
						String distributor = object.getString("Distributor");
						int gross = object.getInt("Gross");
						Film temp = new Film(rank, title, distributor, gross);
						topFilms.add(temp);
					}

					goToNextActivity();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(MainActivity.this, R.string.data_call_error, Toast.LENGTH_LONG).show();
				tvError.setVisibility(View.VISIBLE);
				tvError.setText("R.string.data_call_error");
			}
		}


		@Override
		public void onLoaderReset(Loader<JSONArray> loader) {

		}
	};


	private void addDataFromRemoteTable() {

		btnFromRemote.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				tvError.setVisibility(View.INVISIBLE);
				LoaderManager loaderManager = getSupportLoaderManager();

				Loader<String> grossMoviesLoader = loaderManager.getLoader(FILMS_LOADER);
				// If the Loader was null, initialize it. Else, restart it.
				if (grossMoviesLoader == null) {
					loaderManager.initLoader(FILMS_LOADER, null, grossFilmsLoaderCallbacks);

				} else {
					loaderManager.restartLoader(FILMS_LOADER, null, grossFilmsLoaderCallbacks);
				}

			}
		});
	}


	private void addDataToLocalTable() {
		btnFromLocal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				tvError.setVisibility(View.INVISIBLE);
				// Clear table before populating.
				int rowAmount = (int) myDB.getCount();
				if (rowAmount > 0) {
					myDB.dropTable();
				}
				// DatabaseHelper.insertData returns a bool
				// based on completion of inserting to table.

				for (int i = 0; i < mTitleArray.length; i++) {

					boolean isInserted =
									myDB.insertData(
													mTitleArray[i],
													mDistArray[i],
													mGrossArray[i]);
					if (isInserted) {
						Log.i(TAG, mTitleArray[i] + getString(R.string.table_insert_success));
					} else {
						Log.i(TAG, mTitleArray[i] + getString(R.string.table_insert_failure));
						break;
					}
				}

				// Pull Data To Create Film Objects.
				convertLocaltoModel();
			}
		});
	}

	private void convertLocaltoModel() {
		Cursor results = myDB.getAllData();
		if (results.getCount() == 0) {
			// Show Message No Data
			showMessage(getString(R.string.error), getString(R.string.error_no_data));
			return;
		}


		int rank = 1;
		while (results.moveToNext()) {
			String title = results.getString(1);
			String distributor = results.getString(2);
			int gross = Integer.parseInt((results.getString(3)));
			tempFilm = new Film(rank, title, distributor, gross);
			rank++;
			topFilms.add(tempFilm);
		}

		goToNextActivity();

	}

	private void goToNextActivity() {
		Intent intent = new Intent(this, DisplayActivity.class);
		intent.putParcelableArrayListExtra(getString(R.string.films_label), topFilms);
		startActivity(intent);
	}

	public void getAll() {
		btnViewAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Cursor results = myDB.getAllData();
				if (results.getCount() == 0) {
					// Show Message No Data
					showMessage(getString(R.string.error), getString(R.string.error_no_data));
					return;
				}
				StringBuffer buffer = new StringBuffer();

				while (results.moveToNext()) {
					buffer.append("Rank: " + results.getString(0) + "\n");
					buffer.append("Title: " + results.getString(1) + "\n");
					buffer.append("Distributor: " + results.getString(2) + "\n");
					buffer.append("WorldWide Gross: " + results.getString(3) + "\n\n");
				}

				// Show all Data
				showMessage("Data", buffer.toString());

			}
		});
	}

	private void showMessage(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
	}
}
