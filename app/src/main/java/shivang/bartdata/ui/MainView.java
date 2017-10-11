package shivang.bartdata.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.TextView;

import shivang.bartdata.R;

public class MainView extends AppCompatActivity {

    public static Spinner spinnerFrom;
    public static Spinner spinnerTo;
    public static Button buttonCalculate;
    public static TextView textOneWay;
    public static TextView textTwoWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFrom = (Spinner)findViewById(R.id.spinnerFrom);
        spinnerTo = (Spinner)findViewById(R.id.spinnerTo);
        buttonCalculate = (Button)findViewById(R.id.button);
        textOneWay = (TextView)findViewById(R.id.textOneWay);
        textTwoWay = (TextView)findViewById(R.id.textTwoWay);

        final MainPresenter mainPresenter = new MainPresenter(getApplicationContext());

        mainPresenter.getStations();

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] places = new String[2];
                places[0] = spinnerFrom.getSelectedItem().toString();
                places[1] = spinnerTo.getSelectedItem().toString();
                mainPresenter.getFare(places);
            }
        });

    }
}
