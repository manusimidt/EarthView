package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;


public class DepthTest extends RenderState {

    private byte depthTestFunction;
    private static boolean dirty = true;


    public DepthTest() {
        super();
        depthTestFunction = ByteFlags.GL_LESS;
    }


    public static void setDirty() {
        DepthTest.dirty = true;
    }

    @Override
    public boolean isDirty() {
        return DepthTest.dirty;
    }

    @Override
    public void isSynced() {
        DepthTest.dirty = false;
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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof DepthTest)){
            return false;
        }
        return (this.isEnabled()==((DepthTest) obj).isEnabled()) &&
                this.getDepthTestFunction() == ((DepthTest) obj).getDepthTestFunction();
    }
}
