package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x;

import android.opengl.GLES31;
import android.support.annotation.NonNull;

import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStatesHolder;

import static com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TypeConverterGL3x.Category_FACET_CULLING;
import static com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.GL3x.TypeConverterGL3x.Category_WINDING_ORDER;


/**
 * Class for Syncing RenderStates in OpenGL
 */

public class RenderStateAdapterGL3x {

    /**
     * @param defaults RenderStateHolder Object which is assigned by the constructor, these RenderStates will be enable for all
     * Renderables which have no own RenderStates declared
     * @param dirty if another RenderStateHolder is fetched, dirty will be true and the RenderStateAdapter will be fetch
     * the {@param defaults} the next time {@param syncDefaults()} is called
     */
    private RenderStatesHolder defaults;
    private static boolean dirty;


    public RenderStateAdapterGL3x(RenderStatesHolder defaultRenderStates) {
        defaults = defaultRenderStates;
        dirty = true;
    }


    public void syncDefaults() {
        if (dirty) {
            syncRenderStates(defaults);
        }
    }

    public void syncRenderStates(@NonNull RenderStatesHolder renderStates) {
        if (renderStates != defaults) {
            dirty = true;
            defaults.markChangedRenderStates(renderStates);
        }
            if (renderStates.getDepthTest().isDirty()) {
                if (renderStates.getDepthTest().isEnabled()) {
                    GLES31.glEnable(GLES31.GL_DEPTH_TEST);
                    GLES31.glDepthFunc(TypeConverterGL3x.convert(TypeConverterGL3x.Category_DEPTH_TESTING, renderStates.getDepthTest().getDepthTestFunction()));
                    GLES31.glDepthRangef(0.1f, 50f);
                } else {
                    GLES31.glDisable(GLES31.GL_DEPTH_TEST);
                }
                renderStates.getDepthTest().isSynced();
            }

            if (renderStates.getFaceCulling().isDirty()) {
                if (renderStates.getFaceCulling().isEnabled()) {
                    GLES31.glEnable(GLES31.GL_CULL_FACE);
                    GLES31.glCullFace(TypeConverterGL3x.convert(Category_FACET_CULLING, renderStates.getFaceCulling().getCullFace()));
                } else {
                    GLES31.glDisable(GLES31.GL_CULL_FACE);
                }
                renderStates.getFaceCulling().isSynced();
            }
            if (renderStates == defaults) {
                dirty = false;
            } else {
                dirty = true;
            }
        }


    public static void setDirty() {
        dirty = true;
    }

    public void fetchFrontFace(byte frontFace){
        GLES31.glFrontFace(TypeConverterGL3x.convert(Category_WINDING_ORDER, frontFace));
    }


}
