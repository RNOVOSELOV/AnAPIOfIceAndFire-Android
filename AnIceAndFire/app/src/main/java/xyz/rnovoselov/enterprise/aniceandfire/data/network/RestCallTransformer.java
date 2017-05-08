package xyz.rnovoselov.enterprise.aniceandfire.data.network;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import retrofit2.Response;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.error.ApiError;

/**
 * Created by roman on 05.05.17.
 */

public class RestCallTransformer<R> implements ObservableTransformer<Response<R>, R> {

    @Override
    public ObservableSource<R> apply(@NonNull Observable<Response<R>> upstream) {
        return upstream
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
