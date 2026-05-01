package cipher;
public class VigenereCipher implements Cipher {
    
    @Override
    public String encrypt(String text, String keyword) {
        StringBuilder result = new StringBuilder();
        int keywordLength = keyword.length();
        int keywordIndex = 0;

        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char k = Character.toUpperCase(keyword.charAt(keywordIndex % keywordLength));
                char encryptedChar = (char) (((c - 'A' + (k - 'A')) % 26) + 'A');
                result.append(encryptedChar);
                keywordIndex++;
            } else if (Character.isLowerCase(c)) {
                char k = Character.toLowerCase(keyword.charAt(keywordIndex % keywordLength));
                char encryptedChar = (char) (((c - 'a' + (k - 'a')) % 26) + 'a');
                result.append(encryptedChar);
                keywordIndex++;
            } else {
                result.append(c); 
            }
        }
        return result.toString();
    }

    @Override
    public String decrypt(String text, String keyword) {
        StringBuilder result = new StringBuilder();
        int keywordLength = keyword.length();
        int keywordIndex = 0;

        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char k = Character.toUpperCase(keyword.charAt(keywordIndex % keywordLength));
                char decryptedChar = (char) (((c - 'A' - (k - 'A') + 26) % 26) + 'A');
                result.append(decryptedChar);
                keywordIndex++;
            } else if (Character.isLowerCase(c)) {
                char k = Character.toLowerCase(keyword.charAt(keywordIndex % keywordLength));
                char decryptedChar = (char) (((c - 'a' - (k - 'a') + 26) % 26) + 'a');
                result.append(decryptedChar);
                keywordIndex++;
            } else {
                result.append(c); 
            }
        }
        return result.toString();
    }

    @Override
    public String getName() {
        return "Vigenère Cipher";
    }
}
