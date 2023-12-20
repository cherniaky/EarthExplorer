package sk.tuke.earthexplorer;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IApiDefinition {
    @GET("quotes")
    Call<JsonArray> getQuotes();
}
