package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TypeconverterGL3x;

/**
 * Created by Jonas on 2/11/2017.
 */

public class DepthTest {

    private boolean enabled;
    private byte depthTestFunction;


    public DepthTest() {
        enabled = false;
        depthTestFunction = ByteFlags.LESS;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public byte getDepthTestFunction() {
        return depthTestFunction;
    }

    public void setDepthTestFunction(byte depthTestFunction) {
        TypeconverterGL3x.testForValidity(depthTestFunction, ByteFlags.NEVER, ByteFlags.ALWAYS);
        this.depthTestFunction = depthTestFunction;
    }
}
