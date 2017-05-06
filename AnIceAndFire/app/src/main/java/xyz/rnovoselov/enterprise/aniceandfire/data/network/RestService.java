package xyz.rnovoselov.enterprise.aniceandfire.data.network;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.responces.HouseResponce;
import xyz.rnovoselov.enterprise.aniceandfire.utils.Constants;

/**
 * Created by roman on 01.05.17.
 */

public interface RestService {

    @GET("houses")
    Observable<Response<List<HouseResponce>>> getHouses(@Header(Constants.IF_MODIFIED_SINCE) String lastModifiedDate);
}
