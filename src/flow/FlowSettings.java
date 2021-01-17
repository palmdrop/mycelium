package flow;

import flow.generation.bubbleField.settings.BubbleFieldSettings;
import flow.generation.particle.ParticleSettings;
import flow.generation.position.PositionGenerator;
import flow.gravity.Gravity;
import flow.render.ParticleRenderer;
import flow.sketch.SketchSettings;
import processing.core.PGraphics;

public abstract class FlowSettings {
    public PGraphics background;

    public SketchSettings sketch;

    public PositionGenerator position;

    public ParticleSettings particle;

    public BubbleFieldSettings bubbles;

    public Gravity gravity;

    public ParticleRenderer renderer;

}
