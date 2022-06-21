
import java.util.HashMap;

public class SizeCalculator {
    private static char[] multipliers = {'B', 'K', 'M', 'G', 'T', 'P', 'E'};
    private static HashMap<Character, Integer> char2multiplier = getMultiplier();

    public static String getReadableSize(long size) {
        for (int i = 0; i < multipliers.length; i++) {
            double value = ((double) size) / Math.pow(1024, i);
            if (value < 1024) {
                return Math.round(value) + " " + multipliers[i] + (i > 0 ? "b" : "");
            }
        }
        return "Very Big!";
    }

    public static Long getFromReadableSize(String size) {
        char sizeFactor = size.replaceAll("[\\d\\s+]+", "").charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        long length = multiplier * Long.parseLong(size.replaceAll("\\D", ""));
        return length;
    }

    private static HashMap<Character, Integer> getMultiplier() {
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for (int i = 0; i < multipliers.length; i++) {
            char2multiplier.put(multipliers[i], (int) Math.pow(1024, i));
        }
        return char2multiplier;
    }

}
