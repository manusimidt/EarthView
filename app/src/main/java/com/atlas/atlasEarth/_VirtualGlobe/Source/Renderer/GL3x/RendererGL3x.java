package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.EarthModel.EarthModel;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Post;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Shapefiles.ShapefileRenderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.SpaceBackground;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.BackgroundRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.EarthModelRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.PostRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.ShapefileRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderState;

import java.util.ArrayList;
import java.util.List;


public class RendererGL3x {

    private Matrix4f projectionMatrix;
    private EarthModelRenderer earthRenderer;
    private BackgroundRenderer backgroundRenderer;
    private ShapefileRenderer shapefileRenderer;
    private PostRenderer postRenderer;
    private List<Renderable> renderables;


    public RendererGL3x(Context context, RenderState renderState) {
        projectionMatrix = MatricesUtility.createProjectionMatrix(context);
        earthRenderer = new EarthModelRenderer(context, projectionMatrix);
        backgroundRenderer = new BackgroundRenderer(context, projectionMatrix);
        shapefileRenderer = new ShapefileRenderer(context, projectionMatrix);
        postRenderer = new PostRenderer(context, projectionMatrix);
        forceRenderStates(renderState);
    }

    /**
     * Loader methods
     */
    public void postRendables(List<Renderable> renderables) {
        this.renderables = renderables;
    }

    public void render(Light light, Camera camera) {
        camera.calculateCameraPosition();

        int renderableCounter = renderables.size();
        boolean lastRenderable = false;
        List<Renderable> shapefiles = new ArrayList<>();
        List<Renderable> posts = new ArrayList<>();
        clearBuffers();


        for (Renderable renderable : renderables) {
            renderableCounter--;
            if (renderableCounter == 0) {
                lastRenderable = true;
            }

            if (renderable instanceof SpaceBackground) {
                backgroundRenderer.getShaderProgram().start();
                backgroundRenderer.render(renderable);
                backgroundRenderer.getShaderProgram().stop();

            } else if (renderable instanceof EarthModel) {
                earthRenderer.getShaderProgram().start();
                earthRenderer.render(renderable, camera, light);
                earthRenderer.getShaderProgram().stop();

            } else if (renderable instanceof ShapefileRenderable) {
                shapefiles.add(renderable);
                if (lastRenderable){
                    shapefileRenderer.getShaderProgram().start();
                    shapefileRenderer.render(shapefiles, camera);
                    shapefileRenderer.getShaderProgram().stop();
                }

            } else if (renderable instanceof Post) {
                posts.add(renderable);
                if (lastRenderable) {
                    postRenderer.getShaderProgram().start();
                    postRenderer.render(posts, camera, light);
                    postRenderer.getShaderProgram().stop();
                }
            }
        }
    }

    /**
     * Worker Methods
     */
    private void forceRenderStates(RenderState renderState) {
        if (renderState.getDepthTest().isEnabled()) {
            GLES31.glEnable(GLES31.GL_DEPTH_TEST);
            GLES31.glDepthFunc(TypeconverterGL3x.convert(renderState.getDepthTest().getDepthTestFunction()));
            GLES31.glDepthRangef(0.1f,50f);
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
