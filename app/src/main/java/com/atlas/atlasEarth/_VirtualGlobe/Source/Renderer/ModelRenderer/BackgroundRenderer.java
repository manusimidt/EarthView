package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer;


import android.content.Context;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.BackgroundShaderProgram;


public class BackgroundRenderer {

    private BackgroundShaderProgram shaderProgram;
    public BackgroundRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new BackgroundShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(Renderable renderable){
      renderable.render(shaderProgram);
    }



    public BackgroundShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}
