package com.qsoftware.forgemod.modules.ui.common;

import com.qsoftware.forgemod.common.FloatSize;
import lombok.Getter;

public class AspectRatio {
    @Getter private final float ratio;
    @Getter private final float relativeRatio;
    @Getter private final Orientation orientation;
    @Getter private final float sourceWidth;
    @Getter private final float sourceHeight;

    public AspectRatio(float width, float height) {
        this.ratio = width / height;
        
        if (width > height) {
            this.relativeRatio = width / height;
            this.orientation = Orientation.LANDSCAPE;
        } else if (width < height) {
            this.relativeRatio = height / width;
            this.orientation = Orientation.PORTRAIT;
        } else {
            this.relativeRatio = 1;
            this.orientation = Orientation.SQUARE;
        }
        
        this.sourceWidth = width;
        this.sourceHeight = height;
    }
    
    public FloatSize thumbnail(float maxWidth, float maxHeight) {
        float aspectRatio;
        float width;
        float height;
        
        if (sourceWidth > sourceHeight) {
            aspectRatio = (float) (sourceWidth / (double) sourceHeight);

            width = maxWidth;
            height = (int) (width / aspectRatio);

            if (height > maxHeight) {
                aspectRatio = (float) (sourceHeight / (double) sourceWidth);

                height = maxHeight;
                width = (int) (height / aspectRatio);
            }
        } else {
            aspectRatio = (float) (sourceHeight / (double) sourceWidth);

            height = maxHeight;
            width = (int) (height / aspectRatio);
            if (width > maxWidth) {
                aspectRatio = (float) (sourceWidth / (double) sourceHeight);

                width = maxWidth;
                height = (int) (width / aspectRatio);
            }
        }

        return new FloatSize(width, height);
    }

    /**
     * Aspect ratio orientation.
     */
    public enum Orientation {
        LANDSCAPE,
        SQUARE,
        PORTRAIT
    }
}
