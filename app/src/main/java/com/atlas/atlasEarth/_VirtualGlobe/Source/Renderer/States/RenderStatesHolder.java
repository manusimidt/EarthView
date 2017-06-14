package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States;


import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.ColorMask;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.DepthTest;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.FaceCulling;


/**
 * Container class for OpenGL RenderState
 * (Classes which extend {@link com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.RenderState})
 */
public class RenderStatesHolder {

    private DepthTest depthTest;
    private FaceCulling faceCulling;
    private ColorMask colorMask;

    public RenderStatesHolder() {
        depthTest = new DepthTest();
        faceCulling = new FaceCulling();
        colorMask = new ColorMask(true, true, true, true);
    }

    public void loadGlobalDefaults() {
        faceCulling.enable();
        faceCulling.setCullFace(ByteFlags.GL_BACK);
        faceCulling.setWindingOrder(ByteFlags.COUNTERCLOCKWISE);
        depthTest.setDepthTestFunction(ByteFlags.GL_LESS);
        depthTest.enable();
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

    public FaceCulling getFaceCulling() {
        return faceCulling;
    }

    public void setFaceCulling(FaceCulling faceCulling) {
        this.faceCulling = faceCulling;
    }



    public void markChangedRenderStates(RenderStatesHolder renderStatesHolder2) {
        if (!(this.getDepthTest().equals(renderStatesHolder2.getDepthTest()))) {
            DepthTest.setDirty();
        }
        if (this.getFaceCulling().equals(renderStatesHolder2.getFaceCulling())){
            FaceCulling.setDirty();
        }
    }

}
