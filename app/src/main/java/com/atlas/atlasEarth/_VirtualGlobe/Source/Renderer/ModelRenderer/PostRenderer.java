package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.PostShaderProgram;


public class PostRenderer {

    private PostShaderProgram shaderProgram;

    public PostRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new PostShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
    }

    public void render(Renderable renderable, Camera camera, Light light) {
        shaderProgram.loadViewMatrix(camera);
        shaderProgram.loadLight(light);
        renderable.render(shaderProgram);
    }

    public PostShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}
