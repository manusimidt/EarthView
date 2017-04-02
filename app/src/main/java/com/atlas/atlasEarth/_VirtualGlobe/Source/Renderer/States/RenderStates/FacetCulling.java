package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TypeconverterGL3x;

public class FacetCulling {

    private boolean enabled;
    private byte cullFace;
    private byte windingOrder;


    public FacetCulling() {
        enabled = false;
        cullFace = ByteFlags.BACK;
        windingOrder = ByteFlags.COUNTERCLOCKWISE;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public byte getCullFace() {
        return cullFace;
    }

    public void setCullFace(byte cullFace) {
        TypeconverterGL3x.testForValidity(cullFace, ByteFlags.FRONT, ByteFlags.FRONTANDBACK);
        this.cullFace = cullFace;
    }

    public byte getWindingOrder() {
        return windingOrder;
    }

    public void setWindingOrder(byte windingOrder) {
       TypeconverterGL3x.testForValidity(windingOrder,ByteFlags.CLOCKWISE, ByteFlags.COUNTERCLOCKWISE);
        this.windingOrder = windingOrder;
    }
}
