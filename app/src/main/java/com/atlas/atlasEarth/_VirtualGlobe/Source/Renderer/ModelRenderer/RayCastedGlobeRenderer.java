package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector4F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Scene.SceneState;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.RayCastedEarthShaderProgram;


/**
 * Created by Jonas on 4/19/2017.
 */

public class RayCastedGlobeRenderer {

    private RayCastedEarthShaderProgram shaderProgram;


    public RayCastedGlobeRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new RayCastedEarthShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(Renderable renderable, SceneState sceneState, Light light) {
        shaderProgram.loadLight(light);
        shaderProgram.loadViewMatrix(sceneState.getCamera());
        shaderProgram.loadModelZtoClipCoordinates(sceneState.getModelZToClipCoordinates());
        shaderProgram.loadDiffuseSpecularAmbientLighting(new Vector4F(sceneState.getDiffuseIntensity(), sceneState.getSpecularIntensity(), sceneState.getAmbientIntensity(), sceneState.getShininess()));
        renderable.render(shaderProgram);

    }


    public RayCastedEarthShaderProgram getShaderProgram() {
        return shaderProgram;
    }

}
