import java.util.Scanner;

import cipher.CaesarCipher;
import cipher.VigenereCipher;
import cipher.RailFenceCipher;
import cipher.PlayfairCipher;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("[1] - Caesar Cipher");
        System.out.println("[2] - Vigenere Cipher");
        System.out.println("[3] - Rail Fence Cipher");
        System.out.println("[4] - Playfair Cipher");
        System.out.print("Select a cipher: ");
        int choice = scan.nextInt();scan.nextLine();
        
        if (choice == 1) {
            System.out.print("User A sends > ");
            String text = scan.nextLine();
            System.out.print("Enter key: ");
            int shift = scan.nextInt();
            String encrypted = CaesarCipher.encrypt(text, shift);
            System.out.println("Encrypted > " + encrypted);
            String decrypted = CaesarCipher.decrypt(encrypted, shift);
            System.out.println("User B received > " + decrypted);
        } else if (choice == 2) {
            System.out.print("User A sends > ");
            String text = scan.nextLine();
            System.out.print("Enter the keyword: ");
            String keyword = scan.nextLine();
            String encrypted = VigenereCipher.encrypt(text, keyword);
            System.out.println("Encrypted > " + encrypted);
            String decrypted = VigenereCipher.decrypt(encrypted, keyword);
            System.out.println("User B received > " + decrypted);
        } else if (choice == 3) {
            System.out.print("User A sends > ");
            String text = scan.nextLine();
            System.out.print("Enter the number of rails: ");
            int rails = scan.nextInt();
            String encrypted = RailFenceCipher.encrypt(text, rails);
            System.out.println("Encrypted > " + encrypted);
            String decrypted = RailFenceCipher.decrypt(encrypted, rails);
            System.out.println("User B received > " + decrypted);
        } else if (choice == 4) {
            System.out.print("User A sends > ");
            String text = scan.nextLine();
            System.out.print("Enter the keyword: ");
            String keyword = scan.nextLine();
            String encrypted = PlayfairCipher.encrypt(text, keyword);
            System.out.println("Encrypted > " + encrypted);
            String decrypted = PlayfairCipher.decrypt(encrypted, keyword);
            System.out.println("User B received > " + decrypted);
        } else {
            System.out.println("Invalid choice.");
        }

        scan.close();
    }
}
