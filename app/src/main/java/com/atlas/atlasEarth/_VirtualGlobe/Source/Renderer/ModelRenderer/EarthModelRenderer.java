package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer;


import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Scene.SceneState;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.EarthShaderProgram;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Sun;


public class EarthModelRenderer {

    private EarthShaderProgram shaderProgram;


    public EarthModelRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new EarthShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(Renderable renderable, SceneState sceneState, Sun sun) {
        shaderProgram.loadSun(sun);
        shaderProgram.loadViewMatrix(sceneState.getCamera());


        shaderProgram.loadShineVariables(
                sceneState.getDiffuseIntensity(),
                sceneState.getSpecularIntensity(),
                sceneState.getAmbientIntensity(),
                sceneState.getShininess());


        renderable.render(shaderProgram);
    }


    public EarthShaderProgram getShaderProgram() {
        return shaderProgram;
    }


}
