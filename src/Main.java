import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static char[] multipliers = {'B', 'K', 'M', 'G', 'T', 'P', 'E'};

    public static void main(String[] args) {

        String folderPath = "C:/Users/User/Desktop/Projects";
        File file = new File(folderPath);

        long start = System.currentTimeMillis();
        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);

        System.out.println(ReadableSize(size));
        System.out.println(getFromReadableSize(ReadableSize(size)));

        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + "ms");

    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }

    public static String ReadableSize(long size) {
        for (int i = 0; i < multipliers.length; i++) {
            double value = size / Math.pow(1024, i);
            if (value < 1024) {
                return Math.round(value) + " " + multipliers[i] + (i > 0 ? "b" : "");
            }
        }
        return "Very Big!";
    }

    public static Long getFromReadableSize(String size) {
        HashMap<Character, Integer> char2multiplier = getMultiplier();
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
