package com.atlas.atlasEarth._VirtualGlobe.Source.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Network.GeoData.Address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags.INVALID_REQUEST;
import static com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags.OVER_QUERY_LIMIT;
import static com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags.REQUEST_DENIED;
import static com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags.UNKNOWN_ERROR;
import static com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags.ZERO_RESULTS;


public class AddressToGeographicConverter {


    private AddressToGeographicConverterCallback callback;

    public AddressToGeographicConverter(AddressToGeographicConverterCallback callback) {
        this.callback = callback;
    }

    public void convert(String address) {
        new GeocodingApiRequest().execute(address.replace(" ", "+"));
    }

    public interface AddressToGeographicConverterCallback {

        void onSuccess(Address[] addresses);
        void onFailure(byte errorCode);
    }


    private class GeocodingApiRequest extends AsyncTask<String, Void, String> {


        public GeocodingApiRequest() {
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected void onPostExecute(String requestResult) {
            if (requestResult.equals("")) {
                callback.onFailure(ByteFlags.EMPTY_RESULT);
            } else {
                try {
                    JSONObject requestResultJSON = new JSONObject(requestResult);

                    //Test if the Geocoding Request was successful
                    boolean statusIsOk = checkStatusCode(requestResultJSON.getString("status"));

                    if (statusIsOk) {
                        //if so read the results
                        JSONArray results = requestResultJSON.getJSONArray("results");
                        Address[] addresses = new Address[results.length()];
                        for (int i = 0; i < results.length(); i++) {
                            addresses[i] = new Address(results.getJSONObject(i));
                        }

                        callback.onSuccess(addresses);
                    }

                } catch (JSONException e) {
                    callback.onFailure(UNKNOWN_ERROR);
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String response;
            String address = params[0];
            HTTPDataAdapter httpDataAdapter = new HTTPDataAdapter();
            String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + address;
            response = httpDataAdapter.getHTTPData(url);
            return response;
        }

        private boolean checkStatusCode(String status) {
            switch (status.toString()) {
                case "OK":
                    Log.i("Geocoding", "Request successful, reading Addresses");
                    return true;
                case "ZERO_RESULTS":
                    Log.w("Geocoding", "No results were found!");
                    callback.onFailure(ZERO_RESULTS);
                    break;
                case "OVER_QUERY_LIMIT":
                    Log.e("Geocoding", "Unknown error occurred while reading Google Geocoding Request!");
                    callback.onFailure(OVER_QUERY_LIMIT);
                    break;
                case "REQUEST_DENIED":
                    callback.onFailure(REQUEST_DENIED);
                    break;
                case "INVALID_REQUEST":
                    callback.onFailure(INVALID_REQUEST);
                    break;
                case "UNKNOWN_ERROR":
                    Log.e("Geocoding", "Unknown error occurred while reading Google Geocoding Request!");
                    callback.onFailure(UNKNOWN_ERROR);
                    break;
                default:
                    Log.e("Geocoding", "Can not resolve Status from Google Geocoding API! STATUS : " + status.toString());
                    callback.onFailure(UNKNOWN_ERROR);
            }
            return false;
        }
    }
}
