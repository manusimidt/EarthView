package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer;


import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.EarthShaderProgram;

public class EarthModelRenderer {

    private EarthShaderProgram shaderProgram;


    public EarthModelRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new EarthShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(Renderable renderable, Camera camera, Light light) {
        shaderProgram.loadLight(light);
        shaderProgram.loadViewMatrix(camera);
        shaderProgram.loadGridResolution(0.05f);
        renderable.render(shaderProgram);
    }


    public EarthShaderProgram getShaderProgram() {
        return shaderProgram;
    }


}
