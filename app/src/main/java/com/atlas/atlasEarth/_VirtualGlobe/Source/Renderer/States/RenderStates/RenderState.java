package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates;


public class RenderState {

    private boolean dirty;
    private boolean enabled;

    public RenderState() {
        this.dirty = true;
        this.enabled = false;
    }

    public void enable() {
        enabled = true;
        dirty = true;
    }

    public void disable() {
        enabled = false;
        dirty = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void isSynced() {
        dirty = false;
    }
}
