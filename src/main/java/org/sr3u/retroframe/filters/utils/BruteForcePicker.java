package org.sr3u.retroframe.filters.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class BruteForcePicker implements ColorPicker {

    private static final Logger log = LogManager.getLogger(BruteForcePicker.class);
    private final long colorCacheSize;

    private final Map<Integer, Color> selectionCache = new ConcurrentHashMap<>();
    private final AtomicLong cleanupsCount = new AtomicLong(0);
    private final AtomicLong resetsCount = new AtomicLong(0);

    public BruteForcePicker() {
        colorCacheSize = getColorCacheSize();
    }

    @Override
    public Color closestColor(int rgb, Color[] palette) {
        return selectionCache.computeIfAbsent(rgb, k -> {
            Color c = new Color(rgb);
            Color closest = palette[0];
            for (Color n : palette) {
                if (distance(n, c) < distance(closest, c)) {
                    closest = n;
                }
            }
            return closest;
        });
    }

    @Override
    public Color cachedColor(int rgb) {
        return selectionCache.get(rgb);
    }

    @Override
    public void reset() {
        long resetsCount = this.resetsCount.incrementAndGet();
        if (selectionCache.size() > colorCacheSize) {
            log.warn("clearing selectionCache, as it was larger than the threshold (" + selectionCache.size() + " > " + colorCacheSize + ")");
            selectionCache.clear();
            long cleanupsCount = this.cleanupsCount.incrementAndGet();
            log.warn("Color cache cleanups: " + cleanupsCount + " / " + resetsCount + " (" + (cleanupsCount * 100) / resetsCount + "%)");
        }
    }

    private long getColorCacheSize() {
        try {
            String property = System.getProperty("org.sr3u.retroframe.colorCacheSize");
            return Long.parseLong(property);
        } catch (Exception e) {
            return 1024;
        }
    }

    @Override
    public double distance(Color c1, Color c2) {
        return squareDistance(c1, c2);
    }

    public static int squareDistance(Color c1, Color c2) {
        int RedDiff = c1.getRed() - c2.getRed();
        int greenDiff = c1.getGreen() - c2.getBlue();
        int blueDiff = c1.getGreen() - c2.getBlue();
        return RedDiff * RedDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }

    public static double normalizedDistance(Color c1, Color c2) {
        return squareDistance(c1, c2) / 195075.0;
    }

    @Override
    public String getName() {
        return "bruteforce";
    }
}
