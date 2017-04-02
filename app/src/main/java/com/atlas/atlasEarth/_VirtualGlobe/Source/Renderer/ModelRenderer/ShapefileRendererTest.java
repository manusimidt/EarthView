package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.ShapefileShaderProgram;


public class ShapefileRendererTest {
    private ShapefileShaderProgram shaderProgram;


    public ShapefileRendererTest(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new ShapefileShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(Renderable renderable, Camera camera) {
        shaderProgram.loadViewMatrix(camera);
        renderable.render(shaderProgram);
    }


    public ShapefileShaderProgram getShaderProgram() {
        return shaderProgram;
    }


}
