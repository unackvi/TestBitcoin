package com.testbitcoin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    static final String APP_ID = "OGMwMjJmNWU4ODYyNDZjM2FmYWI5NjRhODY3MjZiMWI";
    static String APP_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    // GET https://apiv2.bitcoinaverage.com/constants/exchangerates/{market}?? is this the URL to use??
    // https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCUSD

    // shows the current value
    TextView mshowCurrValue;
    // user selection of which Base Currency
    Spinner mSpinner;

    SpinnerAdapter currSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mshowCurrValue = (TextView) findViewById(R.id.showCurrValue);
        mSpinner = (Spinner) findViewById(R.id.currSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currCodes, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String extractedCode = parent.getItemAtPosition(position).toString();
                extractedCode = extractedCode.substring(0, extractedCode.indexOf('('));
                //APP_URL = APP_URL.concat(extractedCode);
                extractedCode = APP_URL+ extractedCode;
                Log.d("TestBC", APP_URL);
                letsDoSomeNetworking(extractedCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void letsDoSomeNetworking(final String url){
        // connect to HTTP Client
        AsyncHttpClient myClientToGetAPIData = new AsyncHttpClient();

        myClientToGetAPIData.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onStart(){
                String c;
                Log.d("TestBC", "inside get ASyncHttpClient get"+url);
                Toast.makeText(MainActivity.this, "inside get ASyncHttpClient get"+url, Toast.LENGTH_LONG).show();

            }
            @Override
            public void onSuccess(int statusCode,Header[] headers, JSONObject responseBody) {
                // so un-predictable. Sometimes it works the very first time app is initialized but in most cases, it is not called subsequent times WHY????
                String test;
                Log.d("TestBC", "inside onSuccess ASyncHttpClient url"+url);
                test = responseBody.toString();
                Toast.makeText(MainActivity.this, test, Toast.LENGTH_SHORT).show();
                Log.d("TestBC", test);
                try {
                    UpdateURI(responseBody);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject responseBody ){
                Log.d("TestBC", "onFailure Error: "+error.toString());
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace(System.out);
            }

        });

    }
    public void UpdateURI(JSONObject responseToBeParsed) throws JSONException {
        try {
            BitCoinDataModel dataToBeDisplayedInUI = BitCoinDataModel.fromJSON(responseToBeParsed);
            String test = Double.toString(dataToBeDisplayedInUI.getAverageValue());
            mshowCurrValue.setText(test);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }
}