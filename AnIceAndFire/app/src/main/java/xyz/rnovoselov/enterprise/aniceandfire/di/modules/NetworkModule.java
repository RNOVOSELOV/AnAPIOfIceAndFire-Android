package xyz.rnovoselov.enterprise.aniceandfire.di.modules;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import xyz.rnovoselov.enterprise.aniceandfire.data.network.RestService;
import xyz.rnovoselov.enterprise.aniceandfire.utils.AppConfig;

/**
 * Created by roman on 01.05.17.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return createClient();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return createRetrofit(okHttpClient);
    }

    @Provides
    @Singleton
    RestService provideRestService(Retrofit retrofit) {
        return retrofit.create(RestService.class);
    }

    private Retrofit createRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(createConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private Converter.Factory createConverterFactory() {
        return MoshiConverterFactory.create(new Moshi.Builder()
                .build());
    }

    private OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(AppConfig.MAX_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(AppConfig.MAX_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(AppConfig.MAX_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }
}
