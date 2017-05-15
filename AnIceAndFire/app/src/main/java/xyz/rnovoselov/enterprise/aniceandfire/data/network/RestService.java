package xyz.rnovoselov.enterprise.aniceandfire.data.network;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponce;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 01.05.17.
 */

public interface RestService {

    @GET("houses")
    Observable<Response<List<HouseResponce>>> getHouses(@Header(Constants.HEADER_IF_MODIFIED_SINCE) String lastModifiedDate, @Query("page") int pageNumber, @Query("pageSize") int housesCount);
}
