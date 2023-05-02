package org.sr3u.retroframe.filters.rgb;

import org.sr3u.retroframe.filters.ImageFilter;
import org.sr3u.retroframe.filters.utils.ArgParser;

import java.awt.*;
import java.util.List;


public class ChannelIntensity extends RgbFilter {

    private static final float[] IDENTITY = {1.0f, 1.0f, 1.0f, 1.0f};

    public float[] getChannelIntensityMask() {
        return IDENTITY;
    }


    public ChannelIntensity() {
        super(null);
        setColorMapper(this::maskColor);
    }

    Color maskColor(Color c) {
        return RgbFilter.applyIntensityMask(this.getChannelIntensityMask(), c);
    }

    @Override
    public ImageFilter init(List<String> parameters) {
        ArgParser param = new ArgParser(parameters);
        for (int i = 0; i < getChannelIntensityMask().length; i++) {
            getChannelIntensityMask()[i] = (float) param.doubleAt(i).orElse(getChannelIntensityMask()[i]);
        }
        return super.init(parameters);
    }
}
