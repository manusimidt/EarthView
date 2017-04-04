package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Post;

import android.graphics.Bitmap;
import android.opengl.GLES31;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Geometry.cartesianCS.Indices.TriangleIndicesShort;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Rendables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.Mesh;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.VertexAttributeCollection;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.PostShaderProgram;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Texture;

import java.util.ArrayList;
import java.util.List;


public class Post extends Renderable {


    public Post(Vector3F position, Bitmap image, String name, String date) {
        super(position, 0,0,0,1);
        super.setTexture(new Texture(image));
        calculateMesh();
    }

    private void calculateMesh() {
        //Polygon 1
        List<Vector3F> positions = new ArrayList<>();
        List<Vector3F> normals = new ArrayList<>();
        List<Vector2F> textureCoords = new ArrayList<>();
        List<TriangleIndicesShort> indices = new ArrayList<>();

       positions.add(new Vector3F(-0.5f, 0.5f, -0.2f));     //P0 //0
       positions.add(new Vector3F(-0.5f, -0.5f, -0.2f));    //P1 //1
       positions.add(new Vector3F(0.5f, -0.5f, -0.2f));     //P2 //2
       positions.add(new Vector3F(0.5f, 0.5f, -0.2f));      //P3 //3

       indices.add(new TriangleIndicesShort((short) 0, (short) 3, (short) 1));//1
       indices.add(new TriangleIndicesShort((short) 3, (short) 2, (short) 1));//2

        normals.add(new Vector3F(0, 0, 1f));
        normals.add(new Vector3F(0, 0, 1f));
        normals.add(new Vector3F(0, 0, 1f));
        normals.add(new Vector3F(0, 0, 1f));

        textureCoords.add(new Vector2F(1.0f, 0.0f));
        textureCoords.add(new Vector2F(1.0f, 1.0f));
        textureCoords.add(new Vector2F(0.0f, 1.0f));
        textureCoords.add(new Vector2F(0.0f, 0.0f));


        //Polygon 2
      positions.add(new Vector3F(-0.5f, 0.5f, 0.2f));      //P4 //4
      positions.add(new Vector3F(-0.5f, -0.5f, 0.2f));     //P5 //5
      positions.add(new Vector3F(0.5f, -0.5f, 0.2f));      //P6 //6
      positions.add(new Vector3F(0.5f, 0.5f, 0.2f));       //P7 //7

        indices.add(new TriangleIndicesShort((short) 4, (short) 5, (short) 7));//3
        indices.add(new TriangleIndicesShort((short) 7, (short) 5, (short) 6));//4

        normals.add(new Vector3F(0, 0, -1f));
        normals.add(new Vector3F(0, 0, -1f));
        normals.add(new Vector3F(0, 0, -1f));
        normals.add(new Vector3F(0, 0, -1f));

      // textureCoords.add(new Vector2F(0.0f, 0.0f));
      // textureCoords.add(new Vector2F(0.0f, 1.0f));
      // textureCoords.add(new Vector2F(1.0f, 1.0f));
      // textureCoords.add(new Vector2F(1.0f, 0.0f));


        //Polygon 3
       positions.add(new Vector3F(0.5f, 0.5f,  -0.2f));      //P3 //8
       positions.add(new Vector3F(0.5f, -0.5f, -0.2f));     //P2 //9
       positions.add(new Vector3F(0.5f, -0.5f,  0.2f));      //P6 //10
       positions.add(new Vector3F(0.5f, 0.5f,   0.2f));       //P7 //11

        indices.add(new TriangleIndicesShort((short) 10, (short) 9, (short) 8));//5
        indices.add(new TriangleIndicesShort((short) 11, (short) 10, (short) 8));//6

       normals.add(new Vector3F(-1f, 0, 0));
       normals.add(new Vector3F(-1f, 0, 0));
       normals.add(new Vector3F(-1f, 0, 0));
       normals.add(new Vector3F(-1f, 0, 0));

      // textureCoords.add(new Vector2F(1.0f, 0.0f));
      // textureCoords.add(new Vector2F(1.0f, 1.0f));
      // textureCoords.add(new Vector2F(0.0f, 1.0f));
      // textureCoords.add(new Vector2F(0.0f, 0.0f));


        //Polygon 4
        positions.add(new Vector3F(-0.5f, 0.5f,  -0.2f));     //P0 //12
        positions.add(new Vector3F(-0.5f, -0.5f, -0.2f));    //P1 //13
        positions.add(new Vector3F(-0.5f, -0.5f,  0.2f));     //P5 //14
        positions.add(new Vector3F(-0.5f, 0.5f,   0.2f));      //P4 //15

        indices.add(new TriangleIndicesShort((short) 12, (short) 13, (short) 15));
        indices.add(new TriangleIndicesShort((short) 15, (short) 13, (short) 14));

        normals.add(new Vector3F(1f, 0, 0));
        normals.add(new Vector3F(1f, 0, 0));
        normals.add(new Vector3F(1f, 0, 0));
        normals.add(new Vector3F(1f, 0, 0));

       //textureCoords.add(new Vector2F(0.0f, 0.0f));
       //textureCoords.add(new Vector2F(0.0f, 1.0f));
       //textureCoords.add(new Vector2F(1.0f, 1.0f));
       //textureCoords.add(new Vector2F(1.0f, 0.0f));


        //Polygon 5
       positions.add(new Vector3F(-0.5f, 0.5f,  0.2f));      //P4 //16
       positions.add(new Vector3F(-0.5f, 0.5f, -0.2f));     //P0 //17
       positions.add(new Vector3F(0.5f, 0.5f,  -0.2f));      //P3 //18
       positions.add(new Vector3F(0.5f, 0.5f,   0.2f));       //P7 //19

        indices.add(new TriangleIndicesShort((short) 16, (short) 19, (short) 17));
        indices.add(new TriangleIndicesShort((short) 19, (short) 18, (short) 17));

        normals.add(new Vector3F(0, -1, 0));
        normals.add(new Vector3F(0, -1, 0));
        normals.add(new Vector3F(0, -1, 0));
        normals.add(new Vector3F(0, -1, 0));

      // textureCoords.add(new Vector2F(1.0f, 0.0f));
      // textureCoords.add(new Vector2F(1.0f, 1.0f));
      // textureCoords.add(new Vector2F(0.0f, 1.0f));
      // textureCoords.add(new Vector2F(0.0f, 0.0f));


        //Polygon 6
        positions.add(new Vector3F(-0.5f, -0.5f,  0.2f));     //P5 //20
        positions.add(new Vector3F(-0.5f, -0.5f, -0.2f));    //P1 //21
        positions.add(new Vector3F(0.5f, -0.5f,  -0.2f));     //P2 //22
        positions.add(new Vector3F(0.5f, -0.5f,   0.2f));      //P6 //23

        indices.add(new TriangleIndicesShort((short) 20, (short) 21, (short) 23));
        indices.add(new TriangleIndicesShort((short) 23, (short) 21, (short) 22));

        normals.add(new Vector3F(0, 1, 0));
        normals.add(new Vector3F(0, 1, 0));
        normals.add(new Vector3F(0, 1, 0));
        normals.add(new Vector3F(0, 1, 0));

      //  textureCoords.add(new Vector2F(1.0f, 0.0f));
      //  textureCoords.add(new Vector2F(1.0f, 1.0f));
      //  textureCoords.add(new Vector2F(0.0f, 1.0f));
      //  textureCoords.add(new Vector2F(0.0f, 0.0f));

        Mesh mesh = new Mesh();
        mesh.addVertexAttributes(new VertexAttributeCollection(positions, normals, textureCoords));
        mesh.addTriangleIndicesShort(indices);
        super.mesh = mesh;
    }


    @Override
    public void render(ShaderProgramGL3x shaderProgram) {
        PostShaderProgram postShaderProgram = (PostShaderProgram) shaderProgram;

        mesh.getVertexArray().bindAndEnableVAO();
        postShaderProgram.loadTextureIdentifier();

        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D, getTexture0().getTextureID());

        postShaderProgram.loadTransformationMatrix(MatricesUtility.createTransformationMatrix(
                getPosition(),
                getRotX(),
                getRotY(),
                getRotZ(),
                getScale()));

        GLES31.glDrawElements(GLES31.GL_TRIANGLES, getMesh().elementsCount(), GLES31.GL_UNSIGNED_SHORT, getMesh().getIndicesBufferShort());

        VertexArrayNameGL3x.unbindAndDisableVAO();
    }
}
