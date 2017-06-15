package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables;

import android.content.Context;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TextureLoaderGL3x;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh.Mesh;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStatesHolder;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Texture;


public abstract class Renderable {

    private Vector3F position;
    private float rotX, rotY, rotZ;
    private float scale;
    protected Mesh mesh;
    private Texture texture0, texture1, texture2;
    private boolean ready = false;
    private boolean hasTexture = false;
    private RenderStatesHolder renderStateHolder;

    public Renderable(Vector3F position, float rotX, float rotY, float rotZ, float scale) {
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        mesh = new Mesh();
        renderStateHolder = null;
    }

    public Renderable(Vector3F position, float rotX, float rotY, float rotZ, float scale, RenderStatesHolder renderStateHolder) {
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        mesh = new Mesh();
        this.renderStateHolder = renderStateHolder;
    }
    public abstract void onCreate();
    public abstract void render(ShaderProgramGL3x shaderProgram);


    public void increaseRotation(float dx, float dy, float dz) {
        rotX += dx;
        rotY += dy;
        rotZ += dz;
    }
    public void increasePosition(float x, float y, float z) {
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
        return hasTexture;
    }

    public Vector3F getPosition() {
        return position;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    protected float getScale() {
        return scale;
    }
    public boolean hasRenderstate(){
        if(renderStateHolder == null){
            return false;
        }else{
            return true;
        }
    }

    public RenderStatesHolder getRenderStateHolder(){
        return renderStateHolder;
    }

    public void setTexture(Texture texture) {
        hasTexture = true;
        if (this.texture0 == null) {
            this.texture0 = texture;
        } else if (this.texture1 == null) {
            this.texture1 = texture;
        } else if (this.texture2 == null) {
            this.texture2 = texture;
        }
    }

    public byte getWindingOrder(){
        return mesh.getFrontFaceWindingOrder();
    }
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }


    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public void setRotX(float rotX) {
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
        mesh.dispose();
        mesh = null;
        texture0 = null;
        texture1 = null;
        texture2 = null;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady() {
        this.ready = true;
    }
}
