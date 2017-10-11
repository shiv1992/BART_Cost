package shivang.bartdata.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import shivang.bartdata.R;
import shivang.bartdata.data.DataService;

/**
 * Created by SHIVVVV on 10/9/2017.
 */

public final class MainPresenter{

    Spinner spinnerFrom;
    Spinner spinnerTo;
    Context context;
    Button buttonCalculate;
    TextView textOneWay;
    TextView textTwoWay;

    private static Observer locationObserver;
    private static Observer fareObserver;

    private DataService dataService;

    public MainPresenter(Context context){

        spinnerFrom = MainView.spinnerFrom;
        spinnerTo = MainView.spinnerTo;
        buttonCalculate = MainView.buttonCalculate;
        textOneWay = MainView.textOneWay;
        textTwoWay = MainView.textTwoWay;

        this.context = context;
        dataService = new DataService();
        initializeObservers();
    }

    public void initializeObservers(){

        locationObserver = new Observer() {

            public void fun(String input) throws JSONException {

                JSONObject jsonObject = new JSONObject(input);
                setStations(jsonObject);
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull Object o) {
                try {
                    fun((String) o);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };

        fareObserver = new Observer() {

            public void fun(String input) throws JSONException {

                JSONObject jsonObject = new JSONObject(input);
                setFare(jsonObject);
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull Object o) {
                try {
                    fun((String) o);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }

    public static Observer getLocationObserver(){

        return locationObserver;
    }

    public static Observer getFareObserver(){

        return fareObserver;
    }

    public void getStations(){
        dataService.getStations();
    }

    public void getFare(String[] places){
        dataService.getFare(places);
    }

    public void setStations(JSONObject jsonObject) throws JSONException {

        Log.v("Response Json: ",jsonObject.toString());

        JSONObject obj1= (JSONObject) jsonObject.get("root");
        Log.v("Response obj1: ",obj1.toString());

        JSONObject obj2= (JSONObject) obj1.get("stations");
        Log.v("Response obj3: ",obj2.toString());

        JSONArray jsonArray = obj2.getJSONArray("station");
        Log.v("Response Array: ",jsonArray.toString());

        List<String> listStation = new ArrayList<String>();
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject tmp = (JSONObject) jsonArray.get(i);
            listStation.add(String.valueOf(tmp.get("abbr")));
            Log.v("Station "+(i+1)+" : ", listStation.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_list, listStation);

        adapter.setDropDownViewResource(R.layout.spinner_list);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

    }

    public void setFare(JSONObject jsonObject) throws JSONException{

        Log.v("Response Json: ",jsonObject.toString());

        JSONObject obj1= (JSONObject) jsonObject.get("root");
        Log.v("Response obj1: ",obj1.toString());

        JSONObject obj2= (JSONObject) obj1.get("trip");
        Log.v("Response obj3: ",obj2.toString());

        String fare = (String)obj2.get("fare");
        Log.v("Fare Array: ","$ "+fare);
        textOneWay.setText("$ "+fare);
        Float fare1 = Float.valueOf(fare);
        textTwoWay.setText("$ "+String.valueOf(fare1 * 2));
    }
}
