package xyz.rnovoselov.enterprise.aniceandfire.data.network;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.CharacterResponse;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponse;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 01.05.17.
 */

public interface RestService {

    @GET("houses")
    Observable<Response<List<HouseResponse>>> getHouses(@Header(Constants.HEADER_IF_MODIFIED_SINCE) String lastModifiedDate, @Query("page") int pageNumber, @Query("pageSize") int housesCount);

    @GET("houses/{id}")
    Observable<Response<HouseResponse>> getHouse(@Header(Constants.HEADER_IF_MODIFIED_SINCE) String lastModifiedDate, @Path("id") String houseId);

    @GET("characters/{id}")
    Observable<Response<CharacterResponse>> getCharacterById(@Path("id") String characterId);
}
