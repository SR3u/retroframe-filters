package org.sr3u.retroframe.filters.rgb;

import java.util.Random;

public class Autochrome extends ChannelIntensity {

    private static final Random RANDOM = new Random();

    public Autochrome() {
        super();
    }


    public float[] getChannelIntensityMask() {
        float[] mask = {0.0f, 0.0f, 0.0f, 1.0f};
        mask[RANDOM.nextInt(mask.length - 1)] = 1.0f;
        return mask;
    }

}
