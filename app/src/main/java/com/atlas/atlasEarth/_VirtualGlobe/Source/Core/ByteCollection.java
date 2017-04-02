package com.atlas.atlasEarth._VirtualGlobe.Source.Core;

public class ByteCollection {
    private byte minValue;
    private byte maxValue;

    public ByteCollection(byte minValue, byte maxValue) {

        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    protected void testForValidity(byte value) throws IllegalArgumentException {
        if (!(value >= minValue && value <= maxValue))
            throw new IllegalArgumentException();
    }
}
