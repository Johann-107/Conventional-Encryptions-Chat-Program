package cipher;

public class RailFenceCipher {
    public static String encrypt(String text, int rails) {
        if (rails <= 1) return text; 

        StringBuilder[] rail = new StringBuilder[rails];
        for (int i = 0; i < rails; i++) {
            rail[i] = new StringBuilder();
        }

        int direction = 1; 
        int row = 0;

        for (char c : text.toCharArray()) {
            rail[row].append(c);
            row += direction;

            if (row == 0 || row == rails - 1) {
                direction *= -1; 
            }
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder r : rail) {
            result.append(r);
        }
        return result.toString();
    }

    public static String decrypt(String text, int rails) {
        if (rails <= 1) return text; 

        boolean[][] mark = new boolean[rails][text.length()];
        int direction = 1;
        int row = 0;

        for (int i = 0; i < text.length(); i++) {
            mark[row][i] = true;
            row += direction;

            if (row == 0 || row == rails - 1) {
                direction *= -1;
            }
        }

        char[] result = new char[text.length()];
        int index = 0;

        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < text.length(); j++) {
                if (mark[i][j] && index < text.length()) {
                    result[j] = text.charAt(index++);
                }
            }
        }

        return new String(result);
    }
}
