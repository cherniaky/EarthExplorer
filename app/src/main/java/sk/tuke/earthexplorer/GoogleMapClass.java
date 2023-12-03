package sk.tuke.earthexplorer;

import android.content.Context;
import android.location.Location;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Random;

public class GoogleMapClass {
    private GoogleMap mGoogleMap;
    private Context context;
    private LatLng correctPlace = new LatLng(0.0, 0.0);
    private LatLng selectedPlace = null;

    public GoogleMapClass(GoogleMap mGoogleMap, Context context) {
        this.mGoogleMap = mGoogleMap;
        this.context = context;
    }

    public void setSelectedPlace(LatLng place) {
        this.selectedPlace = place;
    }

    public void setCorrectPlace(LatLng place) {
        this.correctPlace = place;
    }


    public int getDistance() {
        float[] result = new float[1];
        Location.distanceBetween(
                selectedPlace.latitude,
                selectedPlace.longitude,
                correctPlace.latitude,
                correctPlace.longitude,
                result
        );
        return Math.round((result[0] / 1000));
    }

    private Marker placeMarker = null;

    public void addSelectedPlaceMarker(LatLng position) {
        if (placeMarker != null) {
            placeMarker.remove();
        }
        placeMarker = mGoogleMap.addMarker(new MarkerOptions()
                .position(position).title("Guess")
        );
    }

    public void addCorrectMarker(LatLng position) {
        mGoogleMap.addMarker(new MarkerOptions()
                .position(position)
                .title("Correct")
        );
    }

    public void addGuessMarker(LatLng position) {
        mGoogleMap.addMarker(new MarkerOptions()
                .position(position)
                .title("Guess")
        );
    }

    public void addCircle() {
        double mLat = correctPlace.latitude;
        double mLong = correctPlace.longitude;
        Random random = new Random();
        LatLng position = new LatLng(mLat + random.nextDouble() * 0.1 + 0.01,
                mLong + random.nextDouble() * 0.1 + 0.01
        );

        mGoogleMap.addCircle(new CircleOptions()
                .center(position)
                .radius(15000.0)
                .fillColor(ContextCompat.getColor(context, R.color.trans_yellow))
                .strokeColor(ContextCompat.getColor(context, R.color.yellow))
        );

        zoomOnMap(position, 10f);
    }

    public void zoomOnMap() {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        builder.include(correctPlace);
        builder.include(selectedPlace);
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        mGoogleMap.animateCamera(cameraUpdate);
    }

    public void zoomOnMap(LatLng position, Float zoomLevel) {
        CameraUpdate newLatLngZoom = CameraUpdateFactory.newLatLngZoom(position, zoomLevel);
        mGoogleMap.animateCamera(newLatLngZoom);
    }

    public void addPolyline(LatLng correctPlace, LatLng selectedPlace) {

        mGoogleMap.addPolyline(new PolylineOptions()
                .add(correctPlace, selectedPlace)
                .color(ContextCompat.getColor(context, R.color.black))
        );
    }
}
