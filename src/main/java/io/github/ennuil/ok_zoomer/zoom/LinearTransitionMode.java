package io.github.ennuil.ok_zoomer.zoom;

import io.github.ennuil.libzoomer.api.TransitionMode;
import io.github.ennuil.ok_zoomer.utils.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

// The implementation of the linear transition
public class LinearTransitionMode implements TransitionMode {
    private static final ResourceLocation TRANSITION_ID = ModUtils.id("linear_transition");
    private boolean active;
    private final double minimumLinearStep;
    private final double maximumLinearStep;
    private double fovMultiplier;
    private float internalMultiplier;
    private float lastInternalMultiplier;

    public LinearTransitionMode(double minimumLinearStep, double maximumLinearStep) {
        this.active = false;
        this.minimumLinearStep = minimumLinearStep;
        this.maximumLinearStep = maximumLinearStep;
        this.internalMultiplier = 1.0F;
        this.lastInternalMultiplier = 1.0F;
    }

    @Override
    public ResourceLocation getId() {
        return TRANSITION_ID;
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
    public double applyZoom(double fov, float tickDelta) {
        fovMultiplier = Mth.lerp(tickDelta, this.lastInternalMultiplier, this.internalMultiplier);
        return fov * fovMultiplier;
    }

    @Override
    public void tick(boolean active, double divisor) {
        double zoomMultiplier = 1.0D / divisor;

        this.lastInternalMultiplier = this.internalMultiplier;

        double linearStep = Mth.clamp(zoomMultiplier, this.minimumLinearStep, this.maximumLinearStep);
        this.internalMultiplier = Mth.approach(this.internalMultiplier, (float)zoomMultiplier, (float)linearStep);

        if (active || fovMultiplier == this.internalMultiplier) {
            this.active = active;
        }
    }

    @Override
    public double getInternalMultiplier() {
        return this.internalMultiplier;
    }
}
