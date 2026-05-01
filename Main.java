import java.util.Scanner;

import cipher.CaesarCipher;
import cipher.VigenereCipher;
import cipher.RailFenceCipher;
import cipher.PlayfairCipher;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        CaesarCipher caesar = new CaesarCipher();
        VigenereCipher vigenere = new VigenereCipher();
        RailFenceCipher railFence = new RailFenceCipher();
        PlayfairCipher playfair = new PlayfairCipher();

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
            String encrypted = caesar.encrypt(text, shift);
            System.out.println("Encrypted > " + encrypted);
            String decrypted = caesar.decrypt(encrypted, shift);
            System.out.println("User B received > " + decrypted);
        } else if (choice == 2) {
            System.out.print("User A sends > ");
            String text = scan.nextLine();
            System.out.print("Enter the keyword: ");
            String keyword = scan.nextLine();
            String encrypted = vigenere.encrypt(text, keyword);
            System.out.println("Encrypted > " + encrypted);
            String decrypted = vigenere.decrypt(encrypted, keyword);
            System.out.println("User B received > " + decrypted);
        } else if (choice == 3) {
            System.out.print("User A sends > ");
            String text = scan.nextLine();
            System.out.print("Enter the number of rails: ");
            int rails = scan.nextInt();
            String encrypted = railFence.encrypt(text, rails);
            System.out.println("Encrypted > " + encrypted);
            String decrypted = railFence.decrypt(encrypted, rails);
            System.out.println("User B received > " + decrypted);
        } else if (choice == 4) {
            System.out.print("User A sends > ");
            String text = scan.nextLine();
            System.out.print("Enter the keyword: ");
            String keyword = scan.nextLine();
            String encrypted = playfair.encrypt(text, keyword);
            System.out.println("Encrypted > " + encrypted);
            String decrypted = playfair.decrypt(encrypted, keyword);
            System.out.println("User B received > " + decrypted);
        } else {
            System.out.println("Invalid choice.");
        }

        scan.close();
    }
}
