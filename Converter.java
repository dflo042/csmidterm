import java.util.Scanner;

// Problem 1 Part A
public class Converter {

    static final int BAD = -1000;
    static final int OVER = -2000;

    static int digitValue(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'a' && c <= 'f') return c - 'a' + 10;
        if (c >= 'A' && c <= 'F') return c - 'A' + 10;
        return -1;
    }

    static int parseUnsigned(String s, int base) {
        if (s.length() == 0) return BAD;
        for (int i = 0; i < s.length(); i++) {
            int d = digitValue(s.charAt(i));
            if (d < 0 || d >= base) return BAD;
        }
        int value = 0;
        for (int i = 0; i < s.length(); i++) {
            value = value * base + digitValue(s.charAt(i));
            if (value > 255) return OVER;
        }
        return value;
    }

    static int parseDecimal(String s) {
        boolean negative = false;
        int start = 0;
        if (s.length() > 0 && s.charAt(0) == '-') {
            negative = true;
            start = 1;
        }
        if (start == s.length()) return BAD;
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return BAD;
        }
        int value = 0;
        for (int i = start; i < s.length(); i++) {
            value = value * 10 + (s.charAt(i) - '0');
            if (value > 128) return OVER;
        }
        if (negative) {
            if (value == 0) return 0;
            return 256 - value;
        }
        if (value > 127) return OVER;
        return value;
    }

    static String toBase(int b, int base, int width) {
        char[] digits = "0123456789ABCDEF".toCharArray();
        char[] out = new char[width];
        for (int i = width - 1; i >= 0; i--) {
            out[i] = digits[b % base];
            b = b / base;
        }
        return new String(out);
    }

    static String toSignedDecimal(int b) {
        int v = b;
        if ((b & 0x80) != 0) v = b - 256;
        if (v == 0) return "0";
        String s = "";
        int n = v < 0 ? -v : v;
        while (n > 0) {
            s = (char) ('0' + n % 10) + s;
            n = n / 10;
        }
        return v < 0 ? "-" + s : s;
    }

    static void printRow(String str, String dec, String oct, String bin, String hex) {
        System.out.printf("%-10s%-10s%-10s%-10s%-10s%n", str, dec, oct, bin, hex);
    }

    static void printByte(int b) {
        String str = (b >= 32 && b <= 126) ? "" + (char) b : "INVALID";
        printRow(str, toSignedDecimal(b), toBase(b, 8, 3), toBase(b, 2, 8), toBase(b, 16, 2));
    }

    static void printError(String word, String type, String input) {
        printRow(word,
                type.equals("dec") ? input : word,
                type.equals("oct") ? input : word,
                type.equals("bin") ? input : word,
                type.equals("hex") ? input : word);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        printRow("STRING", "DECIMAL", "OCTAL", "BINARY", "HEX");
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.length() < 4) continue;
            String type = line.substring(0, 3);
            String input = line.substring(4);

            if (type.equals("str")) {
                for (int i = 0; i < input.length(); i++) {
                    int c = input.charAt(i);
                    if (c < 32 || c > 126) printRow("" + (char) c, "INVALID", "INVALID", "INVALID", "INVALID");
                    else printByte(c);
                }
                continue;
            }

            int b;
            if (type.equals("dec")) b = parseDecimal(input);
            else if (type.equals("oct")) b = parseUnsigned(input, 8);
            else if (type.equals("bin")) b = parseUnsigned(input, 2);
            else if (type.equals("hex")) b = parseUnsigned(input, 16);
            else continue;

            if (b == BAD) printError("INVALID", type, input);
            else if (b == OVER) printError("OVERFLOW", type, input);
            else printByte(b);
        }
    }
}
