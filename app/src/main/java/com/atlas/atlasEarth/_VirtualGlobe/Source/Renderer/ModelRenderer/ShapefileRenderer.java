package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.ShapefileShaderProgram;

import java.util.List;


public class ShapefileRenderer {
    private ShapefileShaderProgram shaderProgram;


    public ShapefileRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new ShapefileShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(List<Renderable> polygons, Camera camera) {

        shaderProgram.loadViewMatrix(camera);
        for (Renderable polygon : polygons) {
            polygon.render(shaderProgram);
        }

    }


    public ShapefileShaderProgram getShaderProgram() {
        return shaderProgram;
    }


}
