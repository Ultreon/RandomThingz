package com.qtech.randomthingz.modules.ui.common;

import com.qtech.randomthingz.commons.FloatSize;
import lombok.Getter;

public class Resizer {
    @Getter private final float ratio;
    @Getter private final float relativeRatio;
    @Getter private final Orientation orientation;
    @Getter private final float sourceWidth;
    @Getter private final float sourceHeight;

    public Resizer(float srcWidth, float srcHeight) {
        this.ratio = srcWidth / srcHeight;
        
        if (srcWidth > srcHeight) {
            this.relativeRatio = srcWidth / srcHeight;
            this.orientation = Orientation.LANDSCAPE;
        } else if (srcWidth < srcHeight) {
            this.relativeRatio = srcHeight / srcWidth;
            this.orientation = Orientation.PORTRAIT;
        } else {
            this.relativeRatio = 1;
            this.orientation = Orientation.SQUARE;
        }
        
        this.sourceWidth = srcWidth;
        this.sourceHeight = srcHeight;
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
