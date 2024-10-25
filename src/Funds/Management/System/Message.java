package Funds.Management.System;

/*
 * @author: Divine Grace G. Garduque
 * @date: October 23 - 25, 2024
 *
 * This class represents a message in the school funds management system, including:
 * - senderId: The ID of the sender.
 * - recipientId: The ID of the recipient.
 * - content: The content of the message.
 * - senderName: The name of the sender for better identification.
 * - recipientName: The name of the recipient for better identification.
 *
 * The class provides methods to access the message properties, including sender and recipient information.
 */

public class Message {

    private int senderId;
    private int recipientId;
    private String content;
    private String senderName;  // Add sender name
    private String recipientName; // Add recipient name

    // Updated constructor to include sender and recipient names
    public Message(int senderId, String senderName, int recipientId, String recipientName, String content) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.content = content;
    }

    // Getters for the new properties
    public String getSenderName() {
        return senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public String getContent() {
        return content;
    }
}
