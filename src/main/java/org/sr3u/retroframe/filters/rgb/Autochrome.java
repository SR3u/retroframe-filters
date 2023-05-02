package org.sr3u.retroframe.filters.rgb;

import java.awt.*;
import java.util.Random;

public class Autochrome extends RgbFilter {

    private static final Random RANDOM = new Random();

    protected Autochrome() {
        super(Autochrome::autochrome);
    }

    private static Color autochrome(Color c) {
        float[] channelIntensityMask = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        channelIntensityMask[RANDOM.nextInt(channelIntensityMask.length)] = 1.0f;
        return applyIntensityMask(channelIntensityMask, c);
    }

}
