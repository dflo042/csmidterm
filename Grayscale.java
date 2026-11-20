import java.io.FileOutputStream;
import java.io.IOException;

// Problem 2 Part A
public class Grayscale {

    static void put(byte[] buf, int pos, int value, int count) {
        for (int i = 0; i < count; i++) {
            buf[pos + i] = (byte) (value >> (8 * i));
        }
    }

    public static void main(String[] args) throws IOException {
        int levels = 16;
        int width = levels;
        int height = 1;
        int rowBytes = width * 3;
        int padding = (4 - rowBytes % 4) % 4;
        int dataSize = (rowBytes + padding) * height;
        int fileSize = 54 + dataSize;

        byte[] bmp = new byte[fileSize];

        bmp[0] = 'B';
        bmp[1] = 'M';
        put(bmp, 2, fileSize, 4);
        put(bmp, 10, 54, 4);

        put(bmp, 14, 40, 4);
        put(bmp, 18, width, 4);
        put(bmp, 22, height, 4);
        put(bmp, 26, 1, 2);
        put(bmp, 28, 24, 2);
        put(bmp, 30, 0, 4);
        put(bmp, 34, dataSize, 4);
        put(bmp, 38, 2835, 4);
        put(bmp, 42, 2835, 4);

        int pos = 54;
        for (int x = 0; x < width; x++) {
            int level = 15 - x;
            int value = level * 17;
            bmp[pos++] = (byte) value;
            bmp[pos++] = (byte) value;
            bmp[pos++] = (byte) value;
        }

        FileOutputStream out = new FileOutputStream("grayscale.bmp");
        out.write(bmp);
        out.close();
    }
}
