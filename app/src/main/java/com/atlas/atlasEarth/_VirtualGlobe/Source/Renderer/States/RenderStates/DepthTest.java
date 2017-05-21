package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;


public class DepthTest extends RenderState {

    private byte depthTestFunction;


    public DepthTest() {
        super();
        depthTestFunction = ByteFlags.GL_LESS;
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


    public byte getDepthTestFunction() {
        return depthTestFunction;
    }

    public void setDepthTestFunction(byte depthTestFunction) {
        this.depthTestFunction = depthTestFunction;
    }
}
