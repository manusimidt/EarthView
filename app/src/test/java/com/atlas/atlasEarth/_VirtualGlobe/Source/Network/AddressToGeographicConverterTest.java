package com.atlas.atlasEarth._VirtualGlobe.Source.Network;

import com.atlas.atlasEarth._VirtualGlobe.Source.Network.GeoData.Address;

import org.junit.Test;


/**
 * Created by Jonas on 6/14/2017.
 */
public class AddressToGeographicConverterTest {

    @Test
    public void getGeographicCoordinates() throws Exception {
        AddressToGeographicConverter converter = new AddressToGeographicConverter(new AddressToGeographicConverter.AddressToGeographicConverterCallback() {
            @Override
            public void onSuccess(Address[] addresses) {

            }

            @Override
            public void onFailure(byte failureCode) {

            }
        });
        converter.convert("Carl Loewe Str.7");

    }

}