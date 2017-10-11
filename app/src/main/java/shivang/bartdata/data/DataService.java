package shivang.bartdata.data;

import android.util.Log;

import java.net.URL;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import shivang.bartdata.services.NetworkService;
import shivang.bartdata.ui.MainPresenter;

/**
 * Created by SHIVVVV on 10/9/2017.
 */

public final class DataService {

    public void getStations(){

        io.reactivex.Observable.just("").
                subscribeOn(Schedulers.io()).
                map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        URL url = new URL("https://api.bart.gov/api/stn.aspx?cmd=stns&key=MW9S-E7SL-26DU-VV8V&json=y");
                        OkHttpClient client = NetworkService.getClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        Response response = client.newCall(request).execute();
                        String resp = new String(response.body().bytes());
                        Log.v("Response: ",resp);
                        return resp;
                    }
                }).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(MainPresenter.getLocationObserver());

    }

    public void getFare(String[] places){

        io.reactivex.Observable.just(places).
                subscribeOn(Schedulers.io()).
                map(new Function<String[], String>() {
                    @Override
                    public String apply(@NonNull String[] places) throws Exception {
                        URL url = new URL("https://api.bart.gov/api/sched.aspx?cmd=fare&orig="+places[0]+"&dest="+places[1]+"&key=MW9S-E7SL-26DU-VV8V&json=y");
                        OkHttpClient client = NetworkService.getClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        Response response = client.newCall(request).execute();
                        String resp = new String(response.body().bytes());
                        Log.v("Fare: ", resp);
                        return resp;
                    }
                }).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(MainPresenter.getFareObserver());

    }

}
