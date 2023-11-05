package fr.hamunaptra_.oxaliacore.utils.chat;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Decimal {
    private static final NavigableMap<Double, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000.0, "k");
        suffixes.put(1_000_000.0, "M");
        suffixes.put(1_000_000_000.0, "B");
        suffixes.put(1_000_000_000_000.0, "T");
        suffixes.put(1_000_000_000_000_000.0, "q");
        suffixes.put(1_000_000_000_000_000_000.0, "Q");
    }

    public static String format(double value) {
        if (value == Double.NEGATIVE_INFINITY || Double.isNaN(value)) {
            return "Invalid";
        }
        if (value < 0) {
            return "-" + format(-value);
        }
        if (value < 1000) {
            return String.format("%.1f", value);
        }

        Map.Entry<Double, String> e = suffixes.floorEntry(value);
        Double divideBy = e.getKey();
        String suffix = e.getValue();

        double truncated = value / divideBy;
        boolean hasDecimal = truncated < 100.0 && (truncated / 10.0) != (truncated / 10.0);
        return hasDecimal ? String.format("%.1f", truncated / 10.0) + suffix : (int) truncated + suffix;
    }
}
