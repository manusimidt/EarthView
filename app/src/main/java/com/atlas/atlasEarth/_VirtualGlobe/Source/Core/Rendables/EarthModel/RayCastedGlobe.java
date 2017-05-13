package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.EarthModel;

import android.content.Context;
import android.opengl.GLES31;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.EarthViewOptions;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.cartesianCS.Tessellation.BoxTessellator;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.RayCastedEarthShaderProgram;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Texture;


public class RayCastedGlobe extends Renderable {

    private Ellipsoid ellipsoid;
    private Context context;
    private boolean useAverageDepth = false;

    public RayCastedGlobe(Context context, Ellipsoid ellipsoid) {
        super(new Vector3F(0, 0, 0), 0, 0, 0, 1);
        this.ellipsoid =ellipsoid;
        this.context = context;super.setTexture(configureDayTexture(R.drawable.texture1_5));
      //  super.setTexture(configureNightTexture(R.drawable.texture_night));
    }

    private Texture configureDayTexture(int path) {
        Texture texture = new Texture(path);
        texture.setReflectivity(0.05f);
        texture.setShineDamper(6f);
        return texture;
    }

    private Texture configureNightTexture(int path) {
        Texture texture = new Texture(path);
        texture.setReflectivity(0.05f);
        texture.setShineDamper(6f);
        return texture;
    }

    @Override
    public void onCreate() {
        super.mesh = BoxTessellator.compute(ellipsoid.getRadii().toVector3F().multiplyComponents(2));
        loadTextures(context);
    }

    public void setUseAverageDepth(boolean useAverageDepth) {
        this.useAverageDepth = useAverageDepth;
    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {
        RayCastedEarthShaderProgram earthShaderProgram = (RayCastedEarthShaderProgram) shaderProgram;


        earthShaderProgram.loadTextureIdentifier();
        earthShaderProgram.loadFullLightningOption(EarthViewOptions.isFullLightning());

        earthShaderProgram.loadGlobeOverRadiiSquaredValue(ellipsoid.getOneOverRadiiSquared().toVector3F());
        earthShaderProgram.loadUseAverageDepth(useAverageDepth);

        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture0().getTextureID());


       // GLES31.glActiveTexture(GLES20.GL_TEXTURE1);
       // GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture1().getTextureID());

        mesh.getVertexArray().bindAndEnableVAO();
        mesh.getIndicesBuffer().bind();

        GLES31.glDrawElements(GLES31.GL_TRIANGLES, mesh.getVertexCount(), mesh.getIndicesBuffer().getDataType(), 0);

        mesh.getIndicesBuffer().unbind();
        VertexArrayNameGL3x.unbindAndDisableVAO();
    }

}