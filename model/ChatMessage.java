package model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage {

    private final String sender; 
    private final String receiver;
    private final String plaintext; 
    private final String ciphertext; 
    private final String decrypted; 
    private final String cipherName; 
    private final String key;  
    private final String timestamp;    

    public ChatMessage(String sender, String receiver,
                       String plaintext, String ciphertext,
                       String decrypted, String cipherName, String key) {
        this.sender     = sender;
        this.receiver   = receiver;
        this.plaintext  = plaintext.toUpperCase();
        this.ciphertext = ciphertext.toUpperCase();
        this.decrypted  = decrypted.toUpperCase();
        this.cipherName = cipherName;
        this.key        = key.toUpperCase();
        this.timestamp  = LocalTime.now()
                                   .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getSender()     { return sender; }
    public String getReceiver()   { return receiver; }
    public String getPlaintext()  { return plaintext; }
    public String getCiphertext() { return ciphertext; }
    public String getDecrypted()  { return decrypted; }
    public String getCipherName() { return cipherName; }
    public String getKey()        { return key; }
    public String getTimestamp()  { return timestamp; }

    public String toDisplayString() {
        String separator = "─".repeat(48);
        return String.format(
            "[%s]  [ %s | KEY: %s ]%n" +
            "%-10s> %s%n" +
            "Encrypted > %s%n" +
            "%s received...%n" +
            "Decrypted > %s%n" +
            "%s%n",
            timestamp,
            cipherName,
            key,
            sender,
            plaintext,
            ciphertext,
            receiver,
            decrypted,
            separator
        );
    }

    @Override
    public String toString() {
        return String.format("[%s] %s → %s | %s | Cipher: %s",
            timestamp, sender, receiver, plaintext, cipherName);
    }
}