package com.qsoftware.forgemod.common;

import net.minecraft.world.Explosion;

/**
 * TNT-Properties.
 * @author Qboi123
 */
public class TNTProperties {
    private final float radius;
    private final Explosion.Mode mode;
    private final int fuse;
    private final boolean causesFire;

    /**
     * Get tnt-properties builder.
     *
     * @return builder.
     * @see Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * TNT-Properties constructor.
     *  @param radius explosion radius in blocks.
     * @param mode explosion mode.
     * @param fuse fuse time in ticks.
     * @param causesFire
     */
    protected TNTProperties(float radius, Explosion.Mode mode, int fuse, boolean causesFire) {
        this.radius = radius;
        this.mode = mode;
        this.fuse = fuse;
        this.causesFire = causesFire;
    }

    /**
     * Get explosion radius.
     *
     * @return radius.
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Get explosion mode: {@link Explosion.Mode#BREAK break} / {@link Explosion.Mode#DESTROY destory}
     *
     * @return explosion mode.
     */
    public Explosion.Mode getMode() {
        return mode;
    }

    /**
     * Get fuse time in ticks.
     *
     * @return fuse time.
     */
    public int getFuse() {
        return fuse;
    }

    public boolean getCausesFire() {
        return causesFire;
    }

    /**
     * TNT-Properties builder.
     * @author Qboi123
     * @see TNTProperties
     */
    public static class Builder {
        float radius;
        Explosion.Mode mode;
        int fuse;
        boolean causesFire;

        /**
         * TNT-Properties builder.
         * @since 1.1-release2
         */
        protected Builder() {
            this.radius = 4.0f;
            this.mode = Explosion.Mode.BREAK;
            this.fuse = 80;
            this.causesFire = false;
        }

        /**
         * @param radius radius in blocks.
         * @return builder.
         * @since 1.1-release2
         */
        public Builder radius(float radius) {
            this.radius = radius;
            return this;
        }

        /**
         * @param fuse fuse time in ticks.
         * @return builder.
         * @since 1.1-release2
         */
        public Builder fuse(int fuse) {
            this.fuse = fuse;
            return this;
        }

        /**
         * @param fuse fuse time in ticks.
         * @return builder.
         * @since 1.1-release2
         */
        public Builder causesFire() {
            this.causesFire = true;
            return this;
        }

        /**
         * @param mode explosion mode: break / destroy.
         * @return builder.
         * @since 1.1-release2
         * @see Explosion.Mode
         */
        public Builder mode(Explosion.Mode mode) {
            this.mode = mode;
            return this;
        }

        /**
         * @return the properties.
         * @since 1.1-release2
         * @see TNTProperties
         */
        public TNTProperties build() {
            return new TNTProperties(radius, mode, fuse, causesFire);
        }
    }
}
