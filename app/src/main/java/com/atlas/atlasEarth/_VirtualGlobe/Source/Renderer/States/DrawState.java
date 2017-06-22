package com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.States;

import com.atlas.atlasEarth._VirtualGlobe.Source.Core.Renderables.Renderable;
import com.atlas.atlasEarth._VirtualGlobe.Source.Renderer.Shader.EarthShaderProgram;


public class DrawState {

    private RenderStatesHolder renderStates;
    private EarthShaderProgram shaderProgram;
    private Renderable renderable;

    public DrawState() {
        renderStates = new RenderStatesHolder();
    }

    public DrawState(RenderStatesHolder renderStates, EarthShaderProgram earthShaderProgram, Renderable renderable) {
        this.renderStates = renderStates;
        this.shaderProgram = earthShaderProgram;
        earthShaderProgram.start();
        this.renderable = renderable;

    }

    public RenderStatesHolder getRenderStates() {
        return renderStates;
    }

    public void setRenderStates(RenderStatesHolder renderStates) {
        this.renderStates = renderStates;
    }

    public EarthShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void setShaderProgram(EarthShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public Renderable getRenderable() {
        return renderable;
    }

    public void setRenderable(Renderable renderable) {
        this.renderable = renderable;
    }
}

