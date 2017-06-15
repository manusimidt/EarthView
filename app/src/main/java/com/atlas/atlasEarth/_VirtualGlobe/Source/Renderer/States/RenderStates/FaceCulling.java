package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;


public class FaceCulling extends RenderState {


    private byte cullFace;
    private static boolean dirty = true;


    public FaceCulling() {
        cullFace = ByteFlags.GL_BACK;
    }


    public static void setDirty() {
        FaceCulling.dirty = true;
    }

    @Override
    public boolean isDirty() {
        return FaceCulling.dirty;
    }

    @Override
    public void isSynced() {
        FaceCulling.dirty = false;
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




    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FaceCulling)) {
            return false;
        }
        return (this.isEnabled() == ((FaceCulling) obj).isEnabled()) &&
                this.getCullFace() == ((FaceCulling) obj).getCullFace();
    }
}
