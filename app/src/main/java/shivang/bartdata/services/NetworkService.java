package shivang.bartdata.services;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by SHIVVVV on 10/9/2017.
 */

public final class NetworkService {

    private static OkHttpClient client = null;

    public static OkHttpClient getClient(){

        if(client == null){
            client = new OkHttpClient();
        }

        return client;
    }

}
