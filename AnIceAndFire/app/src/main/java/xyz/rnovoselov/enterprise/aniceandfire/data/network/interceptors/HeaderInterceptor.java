package xyz.rnovoselov.enterprise.aniceandfire.data.network.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import xyz.rnovoselov.enterprise.aniceandfire.utils.AppConfig;

/**
 * Created by roman on 14.05.17.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .addHeader(AppConfig.API_VERSION_HEADER_KEY, AppConfig.API_VERSION_HEADER_VALUE);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
