package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;

import android.content.Context;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapefiles.Polygon;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.BackgroundRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.EarthModelRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.PolygonRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.ShapefileRendererTest;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderState;

import java.util.List;


public class RendererGL3x {

    private Matrix4f projectionMatrix;
    private EarthModelRenderer earthRenderer;
    private BackgroundRenderer backgroundRenderer;
    private ShapefileRendererTest shapefileRenderer;
    private PolygonRenderer polygonRenderer;
    private Renderable earthRenderable;
    private Renderable background;
    private Renderable shapefile;
    private List<Renderable> polygons;
private RenderState renderState;
    public RendererGL3x(Context context, RenderState renderState) {
        projectionMatrix = MatricesUtility.createProjectionMatrix(context);
        earthRenderer = new EarthModelRenderer(context, projectionMatrix);
        backgroundRenderer = new BackgroundRenderer(context, projectionMatrix);
        shapefileRenderer = new ShapefileRendererTest(context, projectionMatrix);
        polygonRenderer = new PolygonRenderer(context, projectionMatrix);
        forceRenderStates(renderState);
this.renderState = renderState;
    }
    /**
     * Loader methods
     */





    public void progressEarthModel(Renderable renderable) {
        earthRenderable = renderable;
    }

    public void progressBackground(Renderable background) {
        this.background = background;
    }

    public void progressShapeFile(Renderable shapefile) {
        this.shapefile = shapefile;
    }
    @Deprecated
    public void progressPolygons(List<Renderable> polygons){
        this.polygons = polygons;
    }


    public void render(Light light, Camera camera) {
        camera.calculateCameraPosition();
        clearBuffers();

        backgroundRenderer.getShaderProgram().start();
        backgroundRenderer.render(background);
        backgroundRenderer.getShaderProgram().stop();


        earthRenderer.getShaderProgram().start();
        earthRenderer.render(earthRenderable, camera, light);
        earthRenderer.getShaderProgram().stop();


       shapefileRenderer.getShaderProgram().start();
       shapefileRenderer.render(shapefile, camera);
       shapefileRenderer.getShaderProgram().stop();


        polygonRenderer.getShaderProgram().start();
        polygonRenderer.render(polygons, camera);
        polygonRenderer.getShaderProgram().stop();


    }

    /**
     * Worker Methods
     */
    private void forceRenderStates(RenderState renderState) {
        if (renderState.getDepthTest().isEnabled()) {
            GLES31.glEnable(GLES31.GL_DEPTH_TEST);
            GLES31.glDepthFunc(TypeconverterGL3x.convert(renderState.getDepthTest().getDepthTestFunction()));
        }
        if (renderState.getFacetCulling().isEnabled()) {
            GLES31.glEnable(GLES31.GL_CULL_FACE);
            GLES31.glCullFace(TypeconverterGL3x.convert(renderState.getFacetCulling().getCullFace()));
            GLES31.glFrontFace(TypeconverterGL3x.convert(renderState.getFacetCulling().getWindingOrder()));
        }
    }


    private void clearBuffers() {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT | GLES31.GL_DEPTH_BUFFER_BIT);
        GLES31.glClearColor(0.005f, 0.005f, 0.02f, 1);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }


}
