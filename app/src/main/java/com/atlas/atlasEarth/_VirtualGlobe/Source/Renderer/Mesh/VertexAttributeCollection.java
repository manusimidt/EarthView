package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlasEarth._VirtualGlobe.Source.Core.CustomDataTypes.Vectors.Vector3F;

import java.util.List;


/**
 * Container for all VAO related Data.
 * Used for com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Mesh
 */

public class VertexAttributeCollection {

    private List<Vector3F> positions = null;
    private List<Vector3F> normals = null;
    private List<Vector2F> textureCoordinates = null;
    private byte mode;


    /**
     * @param positions          3D Positions for VAO in Euclidean Space
     * @param normals            3D Normals, could be null
     * @param textureCoordinates 3D Texture Coordinates, could be null
     * @param mode               Draw mode for glDraw...() (ie: GL_TRIANGLES)
     */
    public VertexAttributeCollection(@NonNull List<Vector3F> positions, @Nullable List<Vector3F> normals, @Nullable List<Vector2F> textureCoordinates, byte mode) {
        this.positions = positions;
        this.normals = normals;
        this.textureCoordinates = textureCoordinates;
        this.mode = mode;
    }

    public List<Vector3F> getPositions() {
        return positions;
    }

    public List<Vector3F> getNormals() {
        return normals;
    }

    public List<Vector2F> getTextureCoordinates() {
        return textureCoordinates;
    }

    public byte getMode() {
        return mode;
    }

    public void clear() {
        positions.clear();
        positions = null;

        if (normals != null) {
            normals.clear();
            normals = null;
        }

        if (textureCoordinates != null) {
            textureCoordinates.clear();
            textureCoordinates = null;
        }
        mode = 0;
    }
}
