package com.atlas.atlasEarth._VirtualGlobe.Source.Network.GeoData;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags.APPROXIMATE;
import static com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags.GEOMETRIC_CENTER;
import static com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags.RANGE_INTERPOLATED;
import static com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags.ROOFTOP;


public class Address {

    private byte locationType;
    private String formattedAddress;

    //location[0) = lat, location[1] = long
    private double[] location;
    // Northeast lat/long, Southeast lat/long
    private double[] viewport;
    //Only optional
    private double[] bounds;


    public Address(JSONObject address) throws JSONException {
        location = new double[2];
        viewport = new double[4];
        extractData(address);
    }

    private void extractData(JSONObject dataSet) throws JSONException {

        JSONArray addressComponents = dataSet.getJSONArray("address_components");
        extractAddressComponents(addressComponents);

        this.formattedAddress = dataSet.getString("formatted_address");
        Log.i("Geocoding", "Address: " + this.formattedAddress);


        JSONObject geometry = dataSet.getJSONObject("geometry");
        extractGeometry(geometry);


        //JSONObject googlePlaceID = dataSet.getJSONObject("place_id");
        //JSONArray types = dataSet.getJSONArray("types");
    }

    private void extractAddressComponents(JSONArray components) throws JSONException {
        for (int i = 0; i < components.length(); i++) {
            JSONObject currentComponent = components.getJSONObject(i);

            switch (currentComponent.getJSONArray("types").get(0).toString()) {
                case "country":

                    break;
                case "administrative_area_level_1":

                    break;
                case "administrative_area_level_2":

                    break;
                case "administrative_area_level_3":

                    break;
                case "administrative_area_level_4":

                    break;
                case "administrative_area_level_5":

                    break;
                case "locality ":

                    break;
                case "sublocality_level_1":

                    break;
                case "sublocality_level_2":

                    break;
                case "sublocality_level_3":

                    break;
                case "sublocality_level_4":

                    break;
                case "sublocality_level_5":

                    break;
                case "postal_code":

                    break;
            }
        }
    }

    private void extractGeometry(JSONObject geometry) throws JSONException {

        switch (geometry.getString("location_type")) {
            case "ROOFTOP":
                this.locationType = ROOFTOP;
                break;
            case "RANGE_INTERPOLATED":
                this.locationType = RANGE_INTERPOLATED;
                break;
            case "GEOMETRIC_CENTER":
                this.locationType = GEOMETRIC_CENTER;
                break;
            case "APPROXIMATE":
                this.locationType = APPROXIMATE;
                break;
        }

        JSONObject location = geometry.getJSONObject("location");
        this.location[0] = location.getDouble("lat");
        this.location[1] = location.getDouble("lng");

        JSONObject viewPort = geometry.getJSONObject("viewport");
        //Northeast
        this.viewport[0] = viewPort.getJSONObject("northeast").getDouble("lat");
        this.viewport[1] = viewPort.getJSONObject("northeast").getDouble("lng");
        //Southwest
        this.viewport[2] = viewPort.getJSONObject("southwest").getDouble("lat");
        this.viewport[3] = viewPort.getJSONObject("southwest").getDouble("lng");

        try {
            JSONObject bounds = geometry.getJSONObject("bounds");
            this.bounds = new double[4];
            //Northeast
            this.bounds[0] = bounds.getJSONObject("northeast").getDouble("lat");
            this.bounds[1] = bounds.getJSONObject("northeast").getDouble("lng");
            //Southwest
            this.bounds[2] = bounds.getJSONObject("southwest").getDouble("lat");
            this.bounds[3] = bounds.getJSONObject("southwest").getDouble("lng");
            Log.i("Geocoding", "Address has Bounds");
        } catch (JSONException e) {
        }


    }

    public byte getLocationType() {
        return locationType;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public double[] getLocation() {
        return location;
    }

    public double[] getViewport() {
        return viewport;
    }

    public double[] getBounds() {
        return bounds;
    }
}
