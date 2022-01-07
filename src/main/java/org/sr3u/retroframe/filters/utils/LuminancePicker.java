package org.sr3u.retroframe.filters.utils;

import java.awt.*;

public class LuminancePicker extends BruteForcePicker {

    @Override
    public double distance(final Color a, final Color b) {
        final double luminanceDistance = luminanceDistance(a, b);
        final double diffR = (a.getRed() - b.getRed());
        final double diffG = (a.getGreen() - b.getGreen());
        final double diffB = (a.getBlue() - b.getBlue());
        return (diffR * diffR * 0.299 + diffG * diffG * 0.587 + diffB * diffB * 0.114) * 0.75
                + luminanceDistance * luminanceDistance;
    }

    public static double luminanceDistance(Color a, Color b) {
        // Compare the difference of two RGB values, weigh by CCIR 601 luminosity:
        final double luminance1 = (a.getRed() * 299 + a.getGreen() * 587 + a.getBlue() * 114) / 1000.0;
        final double luminance2 = (b.getRed() * 299 + b.getGreen() * 587 + b.getBlue() * 114) / 1000.0;
        return luminance1 - luminance2;
    }

    @Override
    public String getName() {
        return "luminance";
    }
}
