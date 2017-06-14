package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates;


public abstract class RenderState {


    private boolean enabled;


    public RenderState() {
        this.enabled = false;
    }


    public abstract boolean isDirty();
    public abstract void isSynced();

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }


}
