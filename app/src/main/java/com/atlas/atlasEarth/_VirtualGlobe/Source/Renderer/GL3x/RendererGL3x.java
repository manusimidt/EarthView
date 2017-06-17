package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;

import android.content.Context;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.LinkedList;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.EarthModel.EarthModel;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.EarthModel.RayCastedGlobe;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Shapefiles.ShapefileRenderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.SpaceBackground;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Testing.TestTriangle;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Testing.TestTriangleRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.BackgroundRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.EarthModelRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.PostRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.RayCastedGlobeRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.ShapefileRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Scene.SceneState;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStatesHolder;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Sun;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for Rendering {@link com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable}
 * It is necessary to create a child class of {@link com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.RendererGL3x} and declare it in the scope
 */

public class RendererGL3x {

    private Matrix4f projectionMatrix;
    private EarthModelRenderer earthRenderer;
    private RayCastedGlobeRenderer rayCastedGlobeRenderer;
    private BackgroundRenderer backgroundRenderer;
    private ShapefileRenderer shapefileRenderer;
    private PostRenderer postRenderer;
    private TestTriangleRenderer testTriangleRenderer;
    private List<Renderable> renderables;
    private LinkedList<Renderable> posts;
    private RenderStateAdapterGL3x renderStateAdapter;


    public RendererGL3x(Context context, RenderStatesHolder renderStates, SceneState sceneState) {
        projectionMatrix = sceneState.getProjectionMatrix();
        earthRenderer = new EarthModelRenderer(context, projectionMatrix);
        rayCastedGlobeRenderer = new RayCastedGlobeRenderer(context, projectionMatrix);
        backgroundRenderer = new BackgroundRenderer(context, projectionMatrix);
        shapefileRenderer = new ShapefileRenderer(context, projectionMatrix);
        postRenderer = new PostRenderer(context, projectionMatrix);
        testTriangleRenderer = new TestTriangleRenderer(context, projectionMatrix);
        renderStateAdapter = new RenderStateAdapterGL3x(renderStates);

    }


    public void postRenderables(List<Renderable> renderables) {
        this.renderables = renderables;
    }

    public void postPosts(LinkedList<Renderable> posts) {
        this.posts = posts;
    }


    public void render(Sun sun, SceneState sceneState) {
        sceneState.getCamera().calculateCameraPosition();
        sun.calculateAngle();

        int renderableCounter = renderables.size();
        boolean lastRenderable = false;
        List<Renderable> shapefiles = new ArrayList<>();
        clearBuffers();


        for (Renderable renderable : renderables) {
            renderableCounter--;
            if (renderableCounter == 0) {
                lastRenderable = true;
            }

            /*
             * Set the Front Face
             */
            renderStateAdapter.fetchFrontFace(renderable.getWindingOrder());
            /*
            * Handle RenderStates for each Renderable, if the Renderable has no specific
            * RenderState sync the default
            */
            if (renderable.hasRenderstate()) {
                renderStateAdapter.syncRenderStates(renderable.getRenderStateHolder());
            } else {
                renderStateAdapter.syncDefaults();
            }


            if (renderable instanceof SpaceBackground) {
                backgroundRenderer.getShaderProgram().start();
                backgroundRenderer.render(renderable, sceneState.getCamera());
                backgroundRenderer.getShaderProgram().stop();

            } else if (renderable instanceof EarthModel) {
                earthRenderer.getShaderProgram().start();
                earthRenderer.render(renderable, sceneState, sun);
                earthRenderer.getShaderProgram().stop();

            } else if (renderable instanceof RayCastedGlobe) {
                rayCastedGlobeRenderer.getShaderProgram().start();
                rayCastedGlobeRenderer.render(renderable, sceneState, sun);
                rayCastedGlobeRenderer.getShaderProgram().stop();

            } else if (renderable instanceof ShapefileRenderable) {
                /*
                * Sort all Shapefiles out of the posted Renderables
                * if the last Renderable in the Array is rendered, render all Shapefiles
                */
                shapefiles.add(renderable);
                if (lastRenderable) {
                    shapefileRenderer.getShaderProgram().start();
                    shapefileRenderer.render(shapefiles, sceneState.getCamera());
                    shapefileRenderer.getShaderProgram().stop();
                }

            } else if (renderable instanceof TestTriangle) {
                testTriangleRenderer.getShaderProgram().start();
                testTriangleRenderer.render(renderable, sceneState.getCamera());
                testTriangleRenderer.getShaderProgram().stop();
            }
            if (posts.size() > 0) {
                postRenderer.getShaderProgram().start();
                postRenderer.render(posts.asArrayList(), sceneState.getCamera(), sun);
                postRenderer.getShaderProgram().stop();
            }
        }
    }

    /**
     * Worker Methods
     */


    private void clearBuffers() {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT | GLES31.GL_DEPTH_BUFFER_BIT);
        GLES31.glClearColor(0.05f, 0.05f, 0.1f, 1);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }


}
