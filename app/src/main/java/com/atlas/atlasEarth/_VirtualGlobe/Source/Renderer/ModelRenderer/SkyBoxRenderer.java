package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer;


import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.SkyBoxShaderProgram;


public class SkyBoxRenderer {

    private SkyBoxShaderProgram shaderProgram;
    public SkyBoxRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new SkyBoxShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.loadUniverseIntensity();
        shaderProgram.stop();
    }

    public void render(Renderable renderable, Camera camera){
        shaderProgram.loadViewMatrix(camera);
      renderable.render(shaderProgram);
    }



    public SkyBoxShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}
