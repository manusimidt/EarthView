package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables;


import android.opengl.GLES31;

import com.atlas.atlasEarth.R;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.ByteFlags;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.CubeMapTexture;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.Mesh;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.VertexBufferCollection;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.SkyBoxShaderProgram;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStatesHolder;


public class SkyBox extends Renderable {


    private final float SIZE = 10;
    private static final int[] TEXTURE_IDS_1 = {
            R.drawable.right,
            R.drawable.left,
            R.drawable.up,
            R.drawable.down,
            R.drawable.back,
            R.drawable.front};
    private static final int[] TEXTURE_IDS_2 = {
            R.drawable.right2,
            R.drawable.left2,
            R.drawable.top2,
            R.drawable.down2,
            R.drawable.back2,
            R.drawable.front2,
    };
    private static final int[] TEXTURE_IDS_3 = {
            R.drawable.sky_box_right,
            R.drawable.sky_box_left,
            R.drawable.sky_box_top,
            R.drawable.sky_box_bottom,
            R.drawable.sky_box_back,
            R.drawable.sky_box_front,
    };


    public SkyBox(Camera camera) {
        super(camera.getPosition(), 0, 0, 0, 1);
        //super(new Vector3F(0,0,0),0,0,0,1);
        super.setTexture(new CubeMapTexture(TEXTURE_IDS_1));
        RenderStatesHolder renderStatesHolder = new RenderStatesHolder();
        renderStatesHolder.getFaceCulling().setCullFace(ByteFlags.GL_BACK);
        renderStatesHolder.getDepthTest().enable();
        super.setRenderStateHolder(renderStatesHolder);

       camera.setOnCameraChangeListener(new Camera.CameraChangeListener() {
           @Override
           public void onPositionChanged(Vector3F position) {
               setPosition(position);
           }
       });
    }


    @Override
    public void onCreate() {

        float[] vertices = {
                -SIZE,  SIZE, -SIZE,
                -SIZE, -SIZE, -SIZE,
                SIZE, -SIZE, -SIZE,
                SIZE, -SIZE, -SIZE,
                SIZE,  SIZE, -SIZE,
                -SIZE,  SIZE, -SIZE,

                -SIZE, -SIZE,  SIZE,
                -SIZE, -SIZE, -SIZE,
                -SIZE,  SIZE, -SIZE,
                -SIZE,  SIZE, -SIZE,
                -SIZE,  SIZE,  SIZE,
                -SIZE, -SIZE,  SIZE,

                SIZE, -SIZE, -SIZE,
                SIZE, -SIZE,  SIZE,
                SIZE,  SIZE,  SIZE,
                SIZE,  SIZE,  SIZE,
                SIZE,  SIZE, -SIZE,
                SIZE, -SIZE, -SIZE,

                -SIZE, -SIZE,  SIZE,
                -SIZE,  SIZE,  SIZE,
                SIZE,  SIZE,  SIZE,
                SIZE,  SIZE,  SIZE,
                SIZE, -SIZE,  SIZE,
                -SIZE, -SIZE,  SIZE,

                -SIZE,  SIZE, -SIZE,
                SIZE,  SIZE, -SIZE,
                SIZE,  SIZE,  SIZE,
                SIZE,  SIZE,  SIZE,
                -SIZE,  SIZE,  SIZE,
                -SIZE,  SIZE, -SIZE,

                -SIZE, -SIZE, -SIZE,
                -SIZE, -SIZE,  SIZE,
                SIZE, -SIZE, -SIZE,
                SIZE, -SIZE, -SIZE,
                -SIZE, -SIZE,  SIZE,
                SIZE, -SIZE,  SIZE
        };

        VertexBufferCollection vbos = new VertexBufferCollection(vertices, null, null, ByteFlags.GL_TRIANGLES);
       super.mesh = new Mesh(vbos, ByteFlags.COUNTERCLOCKWISE);
    }

    @Override
    public void render(ShaderProgramGL3x shaderProgram) {
        SkyBoxShaderProgram skyBoxShaderProgram = (SkyBoxShaderProgram) shaderProgram;
        skyBoxShaderProgram.loadTextureIdentifier();

        skyBoxShaderProgram.loadModelMatrix(getModelMatrix());

        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_CUBE_MAP, getTexture0().getTextureIDGl3x());

        mesh.draw();
    }


}
