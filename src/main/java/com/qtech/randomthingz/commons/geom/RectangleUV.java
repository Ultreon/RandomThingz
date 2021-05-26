package com.qtech.randomthingz.commons.geom;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class RectangleUV extends Rectangle2D implements Serializable {
    public float u;
    public float v;
    public int uWidth;
    public int vHeight;

    public RectangleUV(float u, float v, int uWidth, int vHeight) {
        this.u = u;
        this.v = v;
        this.uWidth = uWidth;
        this.vHeight = vHeight;
    }

    @Override
    public void setRect(double u, double v, double w, double h) {
        this.u = (float) u;
        this.v = (float) v;
        this.uWidth = (int) w;
        this.vHeight = (int) h;
    }

    @Override
    public int outcode(double u, double v) {
        int out = 0;
        if (this.uWidth <= 0) {
            out |= OUT_LEFT | OUT_RIGHT;
        } else if (u < this.u) {
            out |= OUT_LEFT;
        } else if (u > this.u + this.uWidth) {
            out |= OUT_RIGHT;
        }
        if (this.vHeight <= 0) {
            out |= OUT_TOP | OUT_BOTTOM;
        } else if (v < this.v) {
            out |= OUT_TOP;
        } else if (v > this.v + this.vHeight) {
            out |= OUT_BOTTOM;
        }
        return out;
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Float(u, v, uWidth, vHeight);
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    public Rectangle2D createIntersection(Rectangle2D r) {
        Rectangle2D dest = new Rectangle2D.Double();
        Rectangle2D.intersect(this, r, dest);
        return dest;
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    public Rectangle2D createUnion(Rectangle2D r) {
        Rectangle2D dest = new Rectangle2D.Double();
        Rectangle2D.union(this, r, dest);
        return dest;
    }

    @Override
    public double getX() {
        return u;
    }

    @Override
    public double getY() {
        return v;
    }

    @Override
    public double getWidth() {
        return uWidth;
    }

    @Override
    public double getHeight() {
        return vHeight;
    }

    @Override
    public boolean isEmpty() {
        return uWidth == 0 && vHeight == 0;
    }
}
