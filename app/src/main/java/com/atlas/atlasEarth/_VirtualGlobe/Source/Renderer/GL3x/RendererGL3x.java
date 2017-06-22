package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;

import android.content.Context;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.LinkedList;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.EarthModel.EarthModel;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.EarthModel.RayCastedGlobe;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.SkyBox;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.EarthModelRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.PostRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.RayCastedGlobeRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer.SkyBoxRenderer;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Scene.SceneState;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStatesHolder;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Sun;

import java.util.List;


/**
 * Class for Rendering {@link com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable}
 * It is necessary to create a child class of {@link com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.RendererGL3x} and declare it in the scope
 */

public class RendererGL3x {

    private Matrix4f projectionMatrix;
    private EarthModelRenderer earthRenderer;
    private RayCastedGlobeRenderer rayCastedGlobeRenderer;
    private SkyBoxRenderer skyBoxRenderer;
    private PostRenderer postRenderer;
    private List<Renderable> renderables;
    private LinkedList<Renderable> posts;
    private RenderStateAdapterGL3x renderStateAdapter;


    public RendererGL3x(Context context, RenderStatesHolder renderStates, SceneState sceneState) {
        projectionMatrix = sceneState.getProjectionMatrix();
        earthRenderer = new EarthModelRenderer(context, projectionMatrix);
        rayCastedGlobeRenderer = new RayCastedGlobeRenderer(context, projectionMatrix);
        skyBoxRenderer = new SkyBoxRenderer(context, projectionMatrix);
        postRenderer = new PostRenderer(context, projectionMatrix);
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


        clearBuffers();


        for (Renderable renderable : renderables) {

            /*
             * Set the Front Face
             */
            renderStateAdapter.fetchFrontFace(renderable.getWindingOrder());
            /*
            * Handle RenderStates for each Renderable, if the Renderable has no specific
            * RenderState sync the default
            */
            if (renderable.hasRenderState()) {
                renderStateAdapter.syncRenderStates(renderable.getRenderStateHolder());
            } else {
                renderStateAdapter.syncDefaults();
            }


            if (renderable instanceof SkyBox) {
                skyBoxRenderer.getShaderProgram().start();
                skyBoxRenderer.render(renderable, sceneState.getCamera());
                skyBoxRenderer.getShaderProgram().stop();

            } else if (renderable instanceof EarthModel) {
                earthRenderer.getShaderProgram().start();
                earthRenderer.render(renderable, sceneState, sun);
                earthRenderer.getShaderProgram().stop();

            } else if (renderable instanceof RayCastedGlobe) {
                rayCastedGlobeRenderer.getShaderProgram().start();
                rayCastedGlobeRenderer.render(renderable, sceneState, sun);
                rayCastedGlobeRenderer.getShaderProgram().stop();

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
    public void dispose() {
        earthRenderer.getShaderProgram().dispose();
        rayCastedGlobeRenderer.getShaderProgram().dispose();
        skyBoxRenderer.getShaderProgram().dispose();
        postRenderer.getShaderProgram().dispose();
        for (Renderable renderable : renderables){
            renderable.dispose();
        }
    }

    private void clearBuffers() {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT | GLES31.GL_DEPTH_BUFFER_BIT);
        GLES31.glClearColor(0.05f, 0.05f, 0.1f, 1);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }


}
