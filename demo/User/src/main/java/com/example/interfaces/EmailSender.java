package com.example.interfaces;

import com.example.entity.User;

public interface EmailSender {
    void sendEmailAlert(String email, Integer cod);
}
