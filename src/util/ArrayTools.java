package util;

public class ArrayTools {
    private ArrayTools() {};

    public static <T> T mappedElement(double value, double min, double max, T[] array) {
        double n = (value - min) / (max - min);
        int index = (int)(n * array.length);
        return array[index];
    }

    public static char mappedElement(double value, double min, double max, char[] array) {
        double n = (value - min) / (max - min);
        int index = (int)(n * array.length);
        return array[index];
    }
}
