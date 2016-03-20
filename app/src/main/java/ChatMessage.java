package com.example.android.chilim;
/**
 * Created by cpro on 19.03.16.
 */
public class ChatMessage {
    private String name;
    private String text;

    public ChatMessage() {
        // necessary for Firebase's deserializer
    }
    public ChatMessage(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}