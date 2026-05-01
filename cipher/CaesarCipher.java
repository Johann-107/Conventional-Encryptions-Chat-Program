package cipher;
public class CaesarCipher implements Cipher {

    @Override
    public String encrypt(String text, String key) {
        int shift = Integer.parseInt(key);
        return encrypt(text, shift);
    }

    @Override
    public String decrypt(String text, String key) {
        int shift = Integer.parseInt(key);
        return decrypt(text, shift);
    }

    @Override
    public String getName() {
        return "Caesar Cipher";
    }

    public String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char encryptedChar = (char) (((c - 'A' + shift) % 26) + 'A');
                result.append(encryptedChar);
            } else if (Character.isLowerCase(c)) {
                char encryptedChar = (char) (((c - 'a' + shift) % 26) + 'a');
                result.append(encryptedChar);
            } else {
                result.append(c); 
            }
        }
        return result.toString();
    }

    public String decrypt(String text, int shift) {
        return encrypt(text, 26 - (shift % 26));
    }
}
