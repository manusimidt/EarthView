package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States;


import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.ColorMask;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.DepthTest;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.FacetCulling;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.ScissorTest;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.StencilTest;

public class RenderState {

    private DepthTest depthTest;
    private FacetCulling facetCulling;
    private StencilTest stencilTest;
    private ScissorTest scissorTest;
    private ColorMask colorMask;

    public RenderState() {
        depthTest = new DepthTest();
        facetCulling = new FacetCulling();
        stencilTest = new StencilTest();
        scissorTest = new ScissorTest();
        colorMask = new ColorMask(true, true, true, true);
    }

    public ColorMask getColorMask() {
        return colorMask;
    }

    public void setColorMask(ColorMask colorMask) {
        this.colorMask = colorMask;
    }

    public DepthTest getDepthTest() {
        return depthTest;
    }

    public void setDepthTest(DepthTest depthTest) {
        this.depthTest = depthTest;
    }

    public FacetCulling getFacetCulling() {
        return facetCulling;
    }

    public void setFacetCulling(FacetCulling facetCulling) {
        this.facetCulling = facetCulling;
    }

    public ScissorTest getScissorTest() {
        return scissorTest;
    }

    public void setScissorTest(ScissorTest scissorTest) {
        this.scissorTest = scissorTest;
    }

    public StencilTest getStencilTest() {
        return stencilTest;
    }

    public void setStencilTest(StencilTest stencilTest) {
        this.stencilTest = stencilTest;
    }
}
