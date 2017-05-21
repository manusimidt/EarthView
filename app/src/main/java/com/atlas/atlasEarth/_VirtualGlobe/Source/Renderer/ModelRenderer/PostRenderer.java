package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Light;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.PostShaderProgram;

import java.util.List;


public class PostRenderer {

    private PostShaderProgram shaderProgram;

    public PostRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new PostShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
    }

    public void render(List<Renderable> renderables, Camera camera, Light light) {
        shaderProgram.loadViewMatrix(camera);
        shaderProgram.loadLight(light);
        for (Renderable renderable : renderables) {
            renderable.render(shaderProgram);
        }
    }

    public PostShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}
