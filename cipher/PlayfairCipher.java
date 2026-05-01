package cipher;

public class PlayfairCipher implements Cipher {

    @Override
    public String encrypt(String plaintext, String key) {
        char[][] matrix = buildMatrix(key);
        String prepared = prepareText(plaintext);
        String[][] pairs = splitIntoPairs(prepared);
        return encryptPairs(matrix, pairs);
    }

    @Override
    public String decrypt(String ciphertext, String key) {
        char[][] matrix = buildMatrix(key);
        // Ciphertext should already be in valid pairs; split directly
        String upper = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        String[][] pairs = splitCleanText(upper);
        return decryptPairs(matrix, pairs);
    }

    @Override
    public String getName() {
        return "Playfair";
    }

    private char[][] buildMatrix(String key) {
        String sanitized = key.toUpperCase()
                              .replaceAll("[^A-Z]", "")
                              .replace('J', 'I');

        boolean[] used = new boolean[26];
        StringBuilder sequence = new StringBuilder();

        for (char c : sanitized.toCharArray()) {
            int idx = c - 'A';
            if (!used[idx]) {
                used[idx] = true;
                sequence.append(c);
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue;
            int idx = c - 'A';
            if (!used[idx]) {
                used[idx] = true;
                sequence.append(c);
            }
        }

        char[][] matrix = new char[5][5];
        String seq = sequence.toString();
        for (int i = 0; i < 25; i++) {
            matrix[i / 5][i % 5] = seq.charAt(i);
        }

        return matrix;
    }

    private String prepareText(String plaintext) {
        String clean = plaintext.toUpperCase()
                                .replaceAll("[^A-Z]", "")
                                .replace('J', 'I');

        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < clean.length()) {
            char a = clean.charAt(i);
            sb.append(a);

            if (i + 1 < clean.length()) {
                char b = clean.charAt(i + 1);
                if (a == b) {
                    sb.append('X');
                    i++;
                } else {
                    sb.append(b);
                    i += 2;
                }
            } else {
                sb.append('X');
                i++;
            }
        }

        return sb.toString();
    }

    private String[][] splitIntoPairs(String text) {
        int pairCount = text.length() / 2;
        String[][] pairs = new String[pairCount][2];
        for (int i = 0; i < pairCount; i++) {
            pairs[i][0] = String.valueOf(text.charAt(i * 2));
            pairs[i][1] = String.valueOf(text.charAt(i * 2 + 1));
        }
        return pairs;
    }

    private String[][] splitCleanText(String text) {
        // Ensure even length for safety
        if (text.length() % 2 != 0) text += "X";
        return splitIntoPairs(text);
    }

    private int[] findPosition(char[][] matrix, char c) {
        for (int r = 0; r < 5; r++) {
            for (int col = 0; col < 5; col++) {
                if (matrix[r][col] == c) return new int[]{r, col};
            }
        }
        // Should never reach here with valid input
        throw new IllegalArgumentException("Character not found in matrix: " + c);
    }

    private String encryptPairs(char[][] matrix, String[][] pairs) {
        StringBuilder result = new StringBuilder();
        for (String[] pair : pairs) {
            char a = pair[0].charAt(0);
            char b = pair[1].charAt(0);
            result.append(encryptPair(matrix, a, b));
        }
        return result.toString();
    }

    private String encryptPair(char[][] matrix, char a, char b) {
        int[] posA = findPosition(matrix, a);
        int[] posB = findPosition(matrix, b);

        int rA = posA[0], cA = posA[1];
        int rB = posB[0], cB = posB[1];

        if (rA == rB) {
            // Same row → shift right (wrap around)
            return "" + matrix[rA][(cA + 1) % 5]
                      + matrix[rB][(cB + 1) % 5];
        } else if (cA == cB) {
            // Same column → shift down (wrap around)
            return "" + matrix[(rA + 1) % 5][cA]
                      + matrix[(rB + 1) % 5][cB];
        } else {
            // Rectangle → swap columns
            return "" + matrix[rA][cB]
                      + matrix[rB][cA];
        }
    }

    private String decryptPairs(char[][] matrix, String[][] pairs) {
        StringBuilder result = new StringBuilder();
        for (String[] pair : pairs) {
            char a = pair[0].charAt(0);
            char b = pair[1].charAt(0);
            result.append(decryptPair(matrix, a, b));
        }
        return result.toString();
    }

    private String decryptPair(char[][] matrix, char a, char b) {
        int[] posA = findPosition(matrix, a);
        int[] posB = findPosition(matrix, b);

        int rA = posA[0], cA = posA[1];
        int rB = posB[0], cB = posB[1];

        if (rA == rB) {
            return "" + matrix[rA][(cA + 4) % 5]
                      + matrix[rB][(cB + 4) % 5];
        } else if (cA == cB) {
            return "" + matrix[(rA + 4) % 5][cA]
                      + matrix[(rB + 4) % 5][cB];
        } else {
            return "" + matrix[rA][cB]
                      + matrix[rB][cA];
        }
    }

    public String printMatrix(String key) {
        char[][] matrix = buildMatrix(key);
        StringBuilder sb = new StringBuilder();
        sb.append("Playfair Matrix (key: ").append(key.toUpperCase()).append(")\n");
        sb.append("+---+---+---+---+---+\n");
        for (int r = 0; r < 5; r++) {
            sb.append("| ");
            for (int c = 0; c < 5; c++) {
                sb.append(matrix[r][c]).append(" | ");
            }
            sb.append("\n+---+---+---+---+---+\n");
        }
        return sb.toString();
    }
}