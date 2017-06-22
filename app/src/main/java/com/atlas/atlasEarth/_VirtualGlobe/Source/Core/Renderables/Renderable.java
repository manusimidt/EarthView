package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TextureLoaderGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.Mesh;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStatesHolder;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Texture;


public abstract class Renderable {

    //Renderable is dirty if the Rotation, Translation or Scale does not correspond to the ModelMatrix
    private boolean dirty = true;

    private Vector3F position;
    private float rotX, rotY, rotZ;
    private float scale;
    private Matrix4f modelMatrix = new Matrix4f();


    protected Mesh mesh;
    private Texture texture0, texture1, texture2;
    private RenderStatesHolder renderStateHolder;


    public Renderable(Vector3F position, float rotX, float rotY, float rotZ, float scale) {
        this(position, rotX,rotY,rotZ,scale,null);
    }

    public Renderable(Vector3F position, float rotX, float rotY, float rotZ, float scale, RenderStatesHolder renderStateHolder) {
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.renderStateHolder = renderStateHolder;
    }

    /**
     * Method called for Creation of the Model. Calculate Positions e.t.c
     * The OpenGL Library should not be used for the specific Renderable until now.
     */
    public abstract void onCreate();
    public abstract void render(ShaderProgramGL3x shaderProgram);


    public void increaseRotation(float dx, float dy, float dz) {
        dirty = true;
        rotX += dx;
        rotY += dy;
        rotZ += dz;
    }

    public void increasePosition(float x, float y, float z) {
        dirty = true;
        position.x += x;
        position.y += y;
        position.z += z;
    }

    public void loadTextures(Context context) {
        if (this.texture0 != null) {
            TextureLoaderGL3x.loadTexture(context, texture0);
        }
        if (this.texture1 != null) {
            TextureLoaderGL3x.loadTexture(context, texture1);
        }
    }

    public void activateVAO() {
        mesh.activateVAO();
    }

    public boolean hasTexture() {
        return ((texture0 != null) || (texture1 != null) || (texture2 != null));
    }

    protected Vector3F getPosition() {
        return position;
    }

    protected float getRotX() {
        return rotX;
    }

    protected float getRotY() {
        return rotY;
    }

    protected float getRotZ() {
        return rotZ;
    }

    protected float getScale() {
        return scale;
    }

    /**
     * Test if the Renderable has own RenderStates
     * @return true when it has one, false if not
     */
    public boolean hasRenderState() {
      return (renderStateHolder!=null);
    }

    public RenderStatesHolder getRenderStateHolder() {
        return renderStateHolder;
    }

    public void setRenderStateHolder(RenderStatesHolder renderStateHolder) {
        this.renderStateHolder = renderStateHolder;
    }

    public void setTexture(Texture texture) {
        if (this.texture0 == null) {
            this.texture0 = texture;
        } else if (this.texture1 == null) {
            this.texture1 = texture;
        } else if (this.texture2 == null) {
            this.texture2 = texture;
        }
    }

    public byte getWindingOrder() {
        return mesh.getFrontFaceWindingOrder();
    }

    public void setPosition(Vector3F position){
        dirty = true;
        this.position = position;
    }
    public void setRotZ(float rotZ) {
        dirty = true;
        this.rotZ = rotZ;
    }


    public void setRotY(float rotY) {
        dirty = true;
        this.rotY = rotY;
    }

    public void setRotX(float rotX) {
        dirty = true;
        this.rotX = rotX;
    }


    protected Texture getTexture0() {
        return texture0;
    }

    protected Texture getTexture1() {
        return texture1;
    }

    protected Texture getTexture2() {
        return texture2;
    }

    public void dispose() {
        if(mesh != null) {
            mesh.dispose();
        }
        mesh = null;
        texture0 = null;
        texture1 = null;
        texture2 = null;
    }

    /**
     *
     * @return the ModelMatrix consist of:  TransformationMatrix * RotationMatrix * ScaleMatrix
     */
    public Matrix4f getModelMatrix() {
        if (dirty) {
            modelMatrix = MatricesUtility.createModelMatrix(position, rotX, rotY, rotZ, scale);
            dirty = false;
        }

        return modelMatrix;
    }

}
