package sk.tuke.earthexplorer;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Array;

public class PlaceModel implements Parcelable {
    LatLng correctPlace;
    LatLng guessedPlace;
    int score;
    int distance;

    PlaceModel(LatLng correctPlace, LatLng guessedPlace, int score, int distance) {
        this.correctPlace = correctPlace;
        this.guessedPlace = guessedPlace;
        this.score = score;
        this.distance = distance;
    }

    protected PlaceModel(Parcel parcel) {
        this.correctPlace = parcel.readParcelable(LatLng.class.getClassLoader());
        this.guessedPlace = parcel.readParcelable(LatLng.class.getClassLoader());
        this.score = parcel.readInt();
        this.distance = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeParcelable(correctPlace, flags);
        parcel.writeParcelable(guessedPlace, flags);
        parcel.writeInt(score);
        parcel.writeInt(distance);
    }

    public static final Creator<PlaceModel> CREATOR = new Creator<PlaceModel>() {
        @Override
        public PlaceModel createFromParcel(Parcel source) {
            return new PlaceModel(source);
        }

        @Override
        public PlaceModel[] newArray(int size) {
            return new PlaceModel[size];
        }
    };
}
