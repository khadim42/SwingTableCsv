package com.kh;

import com.kh.ui.MainFrame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();						//  Create GUI
            frame.setVisible(true);						//  Set True for show GUI
        });
    }
}