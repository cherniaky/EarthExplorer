package sk.tuke.earthexplorer;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

class FamousPlacesData {
    private static List<LatLng> data = Arrays.asList(
            new LatLng(40.690667, -74.046202),
            new LatLng(48.858994, 2.293695),
            new LatLng(51.500965, -0.124230),
            new LatLng(43.722874, 10.395610),
            new LatLng(41.890210, 12.490246),
            new LatLng(34.134187, -118.321615),
            new LatLng(51.502912, -0.117386),
            new LatLng(41.402655, 2.174312),
            new LatLng(-33.856144, 151.215155),
            new LatLng(55.752563, 37.623820),
            new LatLng(48.874060, 2.294341),
            new LatLng(51.178936, -1.826430),
            new LatLng(27.174229, 78.042182),
            new LatLng(29.975264, 31.138207),
            new LatLng(48.860413, 2.337558),
            new LatLng(51.501284, -0.141536),
            new LatLng(-22.951985, -43.209790),
            new LatLng(25.199022, 55.273389),
            new LatLng(3.156877, 101.712642),
            new LatLng(47.620947, -122.349362),
            new LatLng(38.625419, -90.190066)
    );

    static List<LatLng> getFamousPlacesList() {
        Set<LatLng> list = new HashSet<>();
        Random rand = new Random();
        while (list.size() < 5) {
            int n = rand.nextInt(data.size());
            list.add(data.get(n));
        }
        return new ArrayList<>(list);
    }
}