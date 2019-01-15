package com.example.alexeyz.fragmenttesting.ui.main;

class LoginMessage {
    MessageType messageType;
    String message;

    public LoginMessage(MessageType messageType, String message) {

        this.messageType = messageType;
        this.message = message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }

    public enum MessageType {
        ERROR,
        REMOVE_ERROR,
        ENABLE_BUTTON,
        DISABLE_BUTTON,
        CLEAR_FIELDS
    }
}
