package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;

public class FaceCulling extends RenderState {


    private byte cullFace;
    private byte windingOrder;


    public FaceCulling() {
        cullFace = ByteFlags.GL_BACK;
        windingOrder = ByteFlags.COUNTERCLOCKWISE;
    }


    @Override
    public void enable() {
        super.enable();
    }

    @Override
    public void disable() {
        super.disable();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    public void setCullFace(byte cullFace) {
        this.cullFace = cullFace;
    }

    public byte getCullFace() {
        return cullFace;
    }

    public void setWindingOrder(byte windingOrder) {
        this.windingOrder = windingOrder;
    }

    public byte getWindingOrder() {
        return windingOrder;
    }


}
