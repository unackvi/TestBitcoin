package com.testbitcoin;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class BitCoinDataModel {
    // instance vars
    private Double averageValue;
    // setter  & getter methods
    public Double getAverageValue() {
        return averageValue;
    }

    // constructor
    public static BitCoinDataModel fromJSON(JSONObject myResponseObject) throws JSONException {
        try {
            BitCoinDataModel averageBCData = new BitCoinDataModel();
            averageBCData.averageValue = myResponseObject.getJSONObject("averages").getDouble("day");
            return averageBCData;
        } catch (JSONException e) {
            Log.d("TestBC", "fromJSON: could not parse the response packet from BitCoin Website ");
            e.printStackTrace();
            return null;
        }
    }


}
