package xyz.rnovoselov.enterprise.aniceandfire.data.network;

import retrofit2.Response;
import rx.Observable;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.error.ApiError;

/**
 * Created by roman on 05.05.17.
 */

public class RestCallTransformer<R> implements Observable.Transformer<Response<R>, R> {

    @Override
    public Observable<R> call(Observable<Response<R>> responseObservable) {
        return responseObservable
                .flatMap(rResponse -> {
                    switch (rResponse.code()) {
                        case 200:
                            return Observable.just(rResponse.body());
                        case 304:
                            return Observable.empty();
                        default:
                            return Observable.error(new ApiError(rResponse.code()));
                    }
                });
    }
}
