package com.assessment.tc2r.grossfilms.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tc2r on 6/11/2017.
 * <p>
 * Description: Film Object
 * @param @title - title of movie.
 * @param @Distributor - Distributor of movie.
 * @param @Gross - Worldwide Gross of movie.
 */

public class Film implements Parcelable {
	String title, Distributor;
	int rank;
	int gross;


	public Film(int rank, String title, String distributor, int gross) {
		this.title = title;
		Distributor = distributor;
		this.rank = rank;
		this.gross = gross;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDistributor() {
		return Distributor;
	}

	public void setDistributor(String distributor) {
		Distributor = distributor;
	}

	public int getGross() {
		return gross;
	}

	public void setGross(int gross) {
		this.gross = gross;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.title);
		dest.writeString(this.Distributor);
		dest.writeInt(this.rank);
		dest.writeInt(this.gross);
	}

	protected Film(Parcel in) {
		this.title = in.readString();
		this.Distributor = in.readString();
		this.rank = in.readInt();
		this.gross = in.readInt();
	}

	public static final Creator<Film> CREATOR = new Creator<Film>() {
		@Override
		public Film createFromParcel(Parcel source) {
			return new Film(source);
		}

		@Override
		public Film[] newArray(int size) {
			return new Film[size];
		}
	};
}
