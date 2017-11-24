package com.mibesoft.appicon;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImagePreview {
    
    private final int width = 700;
    private final int height = 700;
    private final int headerPanelHeight = 100;
    
    private final int imageWidth = 512;
    private final int imageHeight = 512;

    public void displayGUI() throws Exception {
        
        JFrame frame = new JFrame();
        frame.setLayout(null);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, width, height);
        
        JPanel headerPanel = new JPanel();        
        headerPanel.setLayout(null);
        headerPanel.setBackground(new Color(233, 240, 240));
        headerPanel.setBounds(0, 0, width, headerPanelHeight);
        
        JLabel templateLabel = new JLabel("Template File:");
        templateLabel.setBounds(30, 0, 100, 30);
        headerPanel.add(templateLabel);
        
        JTextField templateTextField = new JTextField();
        templateTextField.setBounds(130, 0, 400, 30);
        headerPanel.add(templateTextField);
        
        JButton chooserFile = new JButton("Chooce");
        chooserFile.setBounds(540, 0, 100, 30);
        chooserFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Xml Template FILES", "xml", "xml"));
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    templateTextField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        headerPanel.add(chooserFile);
        
        JLabel positionLabel = new JLabel();
        JLabel lblimage = new JLabel();
        
         
        lblimage.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) { }
            @Override
            public void mouseMoved(MouseEvent e) {
                positionLabel.setText("X = " + e.getX() + ", Y = " + e.getY());
            }
        });
        lblimage.setBackground(Color.white);
        lblimage.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(30, 35, 100, 30);

        refreshButton.addActionListener(new ImagePreviewActionListener(imageWidth, imageHeight, lblimage, templateTextField));        
        headerPanel.add(refreshButton);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setBounds(0, headerPanelHeight, width, height - headerPanelHeight);        

        positionLabel.setBounds(20, 10, imageWidth, 20);
        mainPanel.add(positionLabel);
                
        lblimage.setBounds(20, 40, imageWidth, imageHeight);        
        mainPanel.add(lblimage);
        

        frame.add(headerPanel);
        frame.add(mainPanel);
        
        frame.setVisible(true);
    }
}