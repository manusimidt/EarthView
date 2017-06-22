package com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables;

import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.DrawState;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States.RenderStatesHolder;

/**
 * Created by Jonas on 3/21/2017.
 */

public class OutlinedPolylineTexture {


    private DrawState drawState;
    private double width;
    private double outlineWidth;


    public OutlinedPolylineTexture()
    {
        RenderStatesHolder renderStates = new RenderStatesHolder();
        renderStates.getFaceCulling().disable();
       // renderState.Blending.Enabled = true;
       // renderState.Blending.SourceRGBFactor = SourceBlendingFactor.SourceAlpha;
       // renderState.Blending.SourceAlphaFactor = SourceBlendingFactor.SourceAlpha;
       // renderState.Blending.DestinationRGBFactor = DestinationBlendingFactor.OneMinusSourceAlpha;
       // renderState.Blending.DestinationAlphaFactor = DestinationBlendingFactor.OneMinusSourceAlpha;

        drawState = new DrawState();
        drawState.setRenderStates(renderStates);

        width = 3;
        outlineWidth = 2;
    }
}
