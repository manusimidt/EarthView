package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States;

import android.graphics.Color;

import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.ColorMask;


public class ClearState {


    private ColorMask colorMask;
    private boolean depthMask;
    private int frontStencilTest = ~0;
    private int backStencilTest = ~0;
    private int color;
    private float depth;
    private int stencil;


    public ClearState() {

        colorMask = new ColorMask(true, true, true, true);
        depthMask = true;
        color = Color.WHITE;
        depth = 1;
        stencil = 0;
    }

    public boolean isDepthMask() {
        return depthMask;
    }

    public void setDepthMask(boolean depthMask) {
        this.depthMask = depthMask;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBackStencilTest() {
        return backStencilTest;
    }

    public void setBackStencilTest(int backStencilTest) {
        this.backStencilTest = backStencilTest;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public int getFrontStencilTest() {
        return frontStencilTest;
    }

    public void setFrontStencilTest(int frontStencilTest) {
        this.frontStencilTest = frontStencilTest;
    }

    public int getStencil() {
        return stencil;
    }

    public void setStencil(int stencil) {
        this.stencil = stencil;
    }

    public ColorMask getColorMask() {
        return colorMask;
    }

    public void setColorMask(ColorMask colorMask) {
        this.colorMask = colorMask;
    }
}
