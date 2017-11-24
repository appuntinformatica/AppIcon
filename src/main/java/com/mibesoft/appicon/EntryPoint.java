package com.mibesoft.appicon;

import javax.swing.SwingUtilities;

public class EntryPoint {
    public static void main(String[] args) throws Exception { 
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ImagePreview().displayGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
