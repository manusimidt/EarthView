package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.ModelRenderer;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Shapefile.Shapefiles.Polygon;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.PolygonShaderProgram;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.ShapefileShaderProgram;

import java.util.List;


public class PolygonRenderer {
    private PolygonShaderProgram shaderProgram;


    public PolygonRenderer(Context context, Matrix4f projectionMatrix) {
        shaderProgram = new PolygonShaderProgram(context);
        shaderProgram.start();
        shaderProgram.loadProjectionMatrix(projectionMatrix);
        shaderProgram.stop();
    }

    public void render(List<Renderable> polygons, Camera camera) {
        shaderProgram.loadViewMatrix(camera);
        for(Renderable polygon : polygons){
            polygon.render(shaderProgram);
        }

    }


    public PolygonShaderProgram getShaderProgram() {
        return shaderProgram;
    }


}
