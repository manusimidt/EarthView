package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Testing;

import android.content.Context;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;
import android.util.Log;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Tools.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


public class PointInWorldSpace {

    private Vector3F[] position;
    private Matrix4f projectionMatrix;
    private Camera camera;
    private int programID;
    private int vaoID;
    private int uniform_projectionM;
    private int uniform_viewMatrix;
    private int uniform_transmat;
    private int mode;
    private int vbo0ID;

    public PointInWorldSpace(Camera camera, Context context, Vector3F... position) {
        this.position = position;
        this.camera = camera;
        projectionMatrix = MatricesUtility.createProjectionMatrix(context);
        prepare();

        if (position.length % 3 == 0) {
            mode = GLES31.GL_TRIANGLES;
        } else {
            mode = GLES31.GL_POINTS;
        }
    }

    private void prepare() {
        vaoID = createVAO();

        bindAndEnableVAO();
        storeDataInVertexArray(BufferUtils.convertVector3FArrayToFloatBuffer(position));
        unbindAndDisableVAO();

        programID = createProgramm();
        GLES31.glUseProgram(programID);
        loadMatrix(uniform_projectionM, projectionMatrix);
        GLES31.glUseProgram(0);
    }


    public void render() {
        GLES31.glUseProgram(programID);
        GLES31.glBindVertexArray(vaoID);

        loadMatrix(uniform_viewMatrix, MatricesUtility.createViewMatrix(camera));
        loadMatrix(uniform_transmat, MatricesUtility.createTransformationMatrix(new Vector3F(0, 0, 0), 0, 0, 0, 1));

        bindAndEnableVAO();
       // if (position.length == 3) {
       //     GLES31.glDrawElements(mode, 3, GLES31.GL_UNSIGNED_SHORT, getIndicesBuffer());
       // } else {
            GLES31.glDrawArrays(mode, 0, position.length);
       // }
        unbindAndDisableVAO();
        GLES31.glUseProgram(0);

    }

    private int createProgramm() {
        int id = GLES31.glCreateProgram();
        GLES31.glAttachShader(id, createShader(GLES31.GL_VERTEX_SHADER));
        GLES31.glAttachShader(id, createShader(GLES31.GL_FRAGMENT_SHADER));
        GLES31.glBindAttribLocation(id, 0, "position");
        GLES31.glLinkProgram(id);
        Log.d("Program", "Node Log: \n" + GLES31.glGetProgramInfoLog(id));
        uniform_projectionM = getUniformLocation(id, "projectionMatrix");
        uniform_transmat = getUniformLocation(id, "transformationMatrix");
        uniform_viewMatrix = getUniformLocation(id, "viewMatrix");
        return id;
    }

    private void loadMatrix(int location, Matrix4f matrix4f) {
        GLES31.glUniformMatrix4fv(location, 1, false, matrix4f.getArray(), 0);
    }


    private int createShader(int shaderType) {
        int id = GLES31.glCreateShader(shaderType);
        GLES31.glShaderSource(id, (shaderType == GLES31.GL_VERTEX_SHADER) ? vertexShader : fragmentShader);
        GLES31.glCompileShader(id);
        Log.d("Program", "Shader Type: " + shaderType + ", CompileLog: \n" + GLES31.glGetShaderInfoLog(id));
        return id;
    }

    private int getUniformLocation(int programID, String name) {
        return GLES31.glGetUniformLocation(programID, name);
    }
    private int createVAO() {
        int[] id = new int[1];
        GLES31.glGenVertexArrays(1, id, 0);
        return id[0];
    }

    private void storeDataInVertexArray(FloatBuffer vertexData) {
        int[] id = new int[1];
        GLES31.glGenBuffers(1, id, 0);
        vbo0ID = id[0];
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER, vbo0ID);
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER, position.length * 3 * 4, vertexData, GLES31.GL_STATIC_DRAW);
        GLES31.glVertexAttribPointer(0, 3, GLES31.GL_FLOAT, false, 0, 0);
    }


    private void bindAndEnableVAO() {
        GLES31.glBindVertexArray(vaoID);
        GLES31.glEnableVertexAttribArray(0);
    }

    private static void unbindAndDisableVAO() {
        GLES31.glDisableVertexAttribArray(0);
        GLES31.glBindVertexArray(0);
    }


    private final String vertexShader =
            "#version 300 es \n" +
                    "in vec4 position;\n" +
                    "uniform mat4 projectionMatrix;" +
                    "uniform mat4 viewMatrix;" +
                    "uniform mat4 transformationMatrix;" +

                    "void main(){" +
                    "gl_PointSize = 30.0;"+
                    "gl_Position = projectionMatrix * viewMatrix * transformationMatrix * position;" +
                    "}";

    private final String fragmentShader =
            "#version 300 es \n" +
                    "uniform vec4 color;\n " +
                    "out vec4 fragmentColor;\n" +
                    "void main(){fragmentColor = vec4(1,0.2,0.2,1);}";
    public ShortBuffer getIndicesBuffer() {
        short[] data = new short[]{0, 1, 2};
        return BufferUtils.convertShortArrayToShortBuffer(data);
    }
}

