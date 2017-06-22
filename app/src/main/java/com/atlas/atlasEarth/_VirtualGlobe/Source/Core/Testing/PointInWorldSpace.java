package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Testing;

import android.content.Context;
import android.opengl.GLES31;
import android.renderscript.Matrix4f;
import android.util.Log;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Scene.Camera.Camera;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Tools.BufferUtils;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.RenderStateAdapterGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.DepthTest;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStates.FaceCulling;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStatesHolder;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


/**
 * External Renderer, for debugging
 */
public class PointInWorldSpace {


    private Vector3F[] positions;
    private Matrix4f projectionMatrix;
    private Camera camera;
    private int programID;
    private int vaoID;

    private int mode;
    private int uniform_projectionMatrix;
    private int uniform_viewMatrix;
    private int uniform_translationMatrix;
    private int uniform_useProjectionMatrix;
    private int uniform_useTranslationMatrix;
    private int uniform_useViewMatrix;
    private boolean useProjectionMatrix;
    private boolean useTransformationMatrix;
    private boolean useViewMatrix;
    private float positionX;
    private float positionY;
    private float positionZ;
    private float rotX;
    private float rotY;
    private float rotZ;
    private RenderStatesHolder renderStatesHolder;

    public PointInWorldSpace(Context context, Camera camera, Vector3F... positions) {
        useProjectionMatrix = true;
        useTransformationMatrix = true;
        renderStatesHolder = new RenderStatesHolder();
        useViewMatrix = true;
        positionX = 0;
        positionY = 0;
        positionZ = 0;
        rotX = 0;
        rotY = 0;
        rotZ = 0;

        this.positions = positions;
        this.camera = camera;

      //  if (positions.length % 3 == 0) {
      //      mode = GLES31.GL_TRIANGLES;
      //  } else {
            mode = GLES31.GL_POINTS;
      //  }

        projectionMatrix = MatricesUtility.createProjectionMatrix(context);
        prepare();
    }


    private void prepare() {

        vaoID = createVAO();

        bindAndEnableVAO();
        storeDataInVertexArray(BufferUtils.convertVector3FArrayToFloatBuffer(positions));
        unbindAndDisableVAO();

        programID = createProgram();

        GLES31.glUseProgram(programID);
        loadMatrix(uniform_projectionMatrix, projectionMatrix);
        GLES31.glUseProgram(0);

    }


    public void render() {
        RenderStateAdapterGL3x.setDirty();
        DepthTest.setDirty();
        FaceCulling.setDirty();
        GLES31.glDisable(GLES31.GL_DEPTH_TEST);
        GLES31.glDisable(GLES31.GL_CULL_FACE);
        GLES31.glUseProgram(programID);
        GLES31.glBindVertexArray(vaoID);


        loadMatrix(uniform_viewMatrix, MatricesUtility.createViewMatrix(camera));
        loadMatrix(uniform_translationMatrix,
                MatricesUtility.createModelMatrix(
                        new Vector3F(positionX, positionY, positionZ), rotX, rotY, rotZ, 1));

        loadBoolean(uniform_useProjectionMatrix, useProjectionMatrix);
        loadBoolean(uniform_useTranslationMatrix, useTransformationMatrix);
        loadBoolean(uniform_useViewMatrix, useViewMatrix);

        bindAndEnableVAO();


        if (positions.length == 3) {
            GLES31.glDrawElements(mode, 3, GLES31.GL_UNSIGNED_SHORT, getIndicesBuffer());
        } else {
            GLES31.glDrawArrays(mode, 0, positions.length);
        }


        unbindAndDisableVAO();
        GLES31.glUseProgram(0);
    }


    private int createProgram() {
        int id = GLES31.glCreateProgram();

        GLES31.glAttachShader(id, createShader(GLES31.GL_VERTEX_SHADER));
        GLES31.glAttachShader(id, createShader(GLES31.GL_FRAGMENT_SHADER));

        GLES31.glBindAttribLocation(id, 0, "position");

        GLES31.glLinkProgram(id);
        Log.d("Program", "Node Log: \n" + GLES31.glGetProgramInfoLog(id));


        uniform_projectionMatrix = getUniformLocation(id, "projectionMatrix");
        uniform_translationMatrix = getUniformLocation(id, "transformationMatrix");
        uniform_viewMatrix = getUniformLocation(id, "viewMatrix");
        uniform_useProjectionMatrix = getUniformLocation(id, "useProjectionMatrix");
        uniform_useTranslationMatrix = getUniformLocation(id, "useTransformationMatrix");
        uniform_useViewMatrix = getUniformLocation(id, "useViewMatrix");
        Log.d("debug", "variables : " + uniform_projectionMatrix + ", " + uniform_translationMatrix + ", " + uniform_viewMatrix);
        Log.d("debug", "variables : " + uniform_useProjectionMatrix + ", " + uniform_useTranslationMatrix + ", " + uniform_useViewMatrix);
        return id;
    }


    private int createShader(int shaderType) {
        int id = GLES31.glCreateShader(shaderType);

        GLES31.glShaderSource(id, (shaderType == GLES31.GL_VERTEX_SHADER) ? vertexShader : fragmentShader);

        GLES31.glCompileShader(id);
        Log.d("Program", "Shader Type xxx: " + shaderType + ", CompileLog: \n" + GLES31.glGetShaderInfoLog(id));
        return id;
    }


    private int createVAO() {
        int[] id = new int[1];
        GLES31.glGenVertexArrays(1, id, 0);
        return id[0];
    }


    private void storeDataInVertexArray(FloatBuffer vertexData) {
        int[] id = new int[1];
        GLES31.glGenBuffers(1, id, 0);

        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER, id[0]);
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER, positions.length * 3 * 4, vertexData, GLES31.GL_STATIC_DRAW);

        GLES31.glVertexAttribPointer(0, 3, GLES31.GL_FLOAT, false, 0, 0);
    }


    public ShortBuffer getIndicesBuffer() {
        short[] data = new short[]{0, 1, 2};
        return BufferUtils.convertShortArrayToShortBuffer(data);
    }


    private int getUniformLocation(int programID, String name) {
        return GLES31.glGetUniformLocation(programID, name);
    }


    private void loadMatrix(int location, Matrix4f matrix4f) {
        GLES31.glUniformMatrix4fv(location, 1, false, matrix4f.getArray(), 0);
    }

    protected void loadBoolean(int location, boolean value) {
        GLES31.glUniform1i(location, value ? 1 : 0);
    }

    private void bindAndEnableVAO() {
        GLES31.glBindVertexArray(vaoID);
        GLES31.glEnableVertexAttribArray(0);
    }


    private static void unbindAndDisableVAO() {
        GLES31.glDisableVertexAttribArray(0);
        GLES31.glBindVertexArray(0);
    }


    private final String vertexShader = "#version 300 es\n" +
            "     in vec4 position;\n" +
            "     uniform mat4 projectionMatrix;\n" +
            "     uniform mat4 viewMatrix;\n" +
            "     uniform mat4 transformationMatrix;\n" +
            "     uniform bool useProjectionMatrix;\n" +
            "     uniform bool useViewMatrix;\n" +
            "     uniform bool useTransformationMatrix;\n" +
            "\n" +
            "     void main(){\n" +
            "\n" +
            "        gl_PointSize = 5.0;\n" +
            "\n" +
            "        if(useProjectionMatrix && useViewMatrix && useTransformationMatrix){\n" +
            "            gl_Position = projectionMatrix * viewMatrix * transformationMatrix * position;\n" +
            "        }else if(useProjectionMatrix && useViewMatrix){\n" +
            "            gl_Position = projectionMatrix * viewMatrix  * position;\n" +
            "        }else if(useViewMatrix && useTransformationMatrix){\n" +
            "            gl_Position = viewMatrix * transformationMatrix * position;\n" +
            "        }else if(useProjectionMatrix && useTransformationMatrix){\n" +
            "            gl_Position = projectionMatrix * transformationMatrix * position;\n" +
            "        }else if(useProjectionMatrix){\n" +
            "            gl_Position = projectionMatrix * position;\n" +
            "        }else if(useViewMatrix){\n" +
            "            gl_Position = viewMatrix * position;\n" +
            "        }else if(useTransformationMatrix){\n" +
            "            gl_Position = transformationMatrix * position;\n" +
            "        }else{\n" +
            "            gl_Position = position;\n" +
            "        }\n" +
            "        }\n";


    private final String fragmentShader =
            "#version 300 es \n" +
                    "precision mediump float;\n" +
                    "uniform vec4 color;\n " +
                    "uniform mat4 viewMatrix;" +
                    "out vec4 fragmentColor;\n" +
                    "void main(){" +
                    "if(viewMatrix == mat4(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)){" +
                    "discard;}" +
                    "fragmentColor = vec4(1,0.2,0.2,1);}";


    public void useProjectionMatrix() {
        this.useProjectionMatrix = !this.useProjectionMatrix;
    }
    public void useTransformationMatrix() {
        this.useTransformationMatrix = !this.useTransformationMatrix;
    }
    public void useViewMatrix() {
        this.useViewMatrix = !this.useViewMatrix;
    }
    public void increasePositionX(float positionX) {
        this.positionX += positionX;
    }
    public void increasePositionY(float positionY) {
        this.positionY += positionY;
    }
    public void increasePositionZ(float positionZ) {
        this.positionZ += positionZ;
    }
    public void increaseRotX(float rotX) {
        this.rotX += rotX;
    }
    public void increaseRotY(float rotY) {
        this.rotY += rotY;
    }
    public void increaseRotZ(float rotZ) {
        this.rotZ += rotZ;
    }
}

