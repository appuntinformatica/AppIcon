package com.mibesoft.appicon;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import lombok.*;

@AllArgsConstructor
public class ImagePreviewActionListener implements ActionListener {
        
    int width;
    int height;    
    JLabel lblimage;
    JTextField templateTextField;

    @Override
    public void actionPerformed(ActionEvent e) {
        XStream xstream = new XStream(new DomDriver("UTF-8"));
        xstream.processAnnotations(Template.class);
        Template template = (Template) xstream.fromXML(new File(this.templateTextField.getText()));           
        System.out.println(template.toString());
        
        AppIcon appIcon = new AppIcon(template.getWindow().getWidth(), 
                template.getWindow().getHeight(), 
                template.getWindow().getBorderRadius(), 
                template.getWindow().getGradient1(),
                template.getWindow().getGradient2());
        
        for (Template.Command command : template.getCommands()) {
            System.out.println(command.toString());
        
            switch (command.getName()) {
                case "drawGradientCircle":
                    try {
                        int padding = Integer.parseInt(command.getParams().get(0));
                        Color gradient1 = new Color(Integer.parseInt(command.getParams().get(1), 16));
                        Color gradient2 = new Color(Integer.parseInt(command.getParams().get(2), 16));
                        appIcon = appIcon.drawGradientCircle(padding, gradient1, gradient2);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }                        
                    break;
                case "drawText":
                    String fontName = command.getParams().get(0);
                    Color fontColor = new Color(Integer.parseInt(command.getParams().get(1), 16));
                    int fontSize = Integer.parseInt(command.getParams().get(2));
                    String text = command.getParams().get(3);
                    if ( command.getParams().size() > 4 ) {
                        int x = Integer.parseInt(command.getParams().get(4));
                        int y = Integer.parseInt(command.getParams().get(5));
                        appIcon = appIcon.drawText(fontName, fontColor, fontSize, text, x, y);
                    } else {
                        appIcon = appIcon.drawText(fontName, fontColor, fontSize, text);
                    }
                    break;
                case "addImage":
                    try {
                        String filename = template.getFolderPath() + "/" + command.getParams().get(0);
                        int x = Integer.parseInt(command.getParams().get(1));
                        int y = Integer.parseInt(command.getParams().get(2));
                        switch (command.getParams().size()) {
                            case 4:
                                int percReduced = Integer.parseInt(command.getParams().get(3));
                                appIcon = appIcon.addImage(filename, x, y, percReduced);
                                break;
                            case 5:
                                int width = Integer.parseInt(command.getParams().get(3));
                                int height = Integer.parseInt(command.getParams().get(4));
                                appIcon = appIcon.addImage(filename, x, y, width, height);
                                break;
                            default:
                                appIcon = appIcon.addImage(filename, x, y);
                                break;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "drawLine":
                    try {
                        int x1 = Integer.parseInt(command.getParams().get(0));
                        int y1 = Integer.parseInt(command.getParams().get(1));
                        int x2 = Integer.parseInt(command.getParams().get(2));
                        int y2 = Integer.parseInt(command.getParams().get(3));
                        int lineWidth = Integer.parseInt(command.getParams().get(4));
                        Color color = new Color(Integer.parseInt(command.getParams().get(5), 16));
                        appIcon = appIcon.drawLine(x1, y1, x2, y2, lineWidth, color);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "drawOval":
                    try {
                        int x = Integer.parseInt(command.getParams().get(0));
                        int y = Integer.parseInt(command.getParams().get(1));
                        int width = Integer.parseInt(command.getParams().get(2));
                        int height = Integer.parseInt(command.getParams().get(3));
                        int borderRadius = Integer.parseInt(command.getParams().get(4));
                        Color backgroundColor = new Color(Integer.parseInt(command.getParams().get(5), 16));
                        appIcon = appIcon.drawOval(x, y, width, height, borderRadius, backgroundColor);   
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "drawGradientOval":
                    try {
                        int centerX = Integer.parseInt(command.getParams().get(0));
                        int centerY = Integer.parseInt(command.getParams().get(1));
                        int width = Integer.parseInt(command.getParams().get(2));
                        int height = Integer.parseInt(command.getParams().get(3));
                        Color gradient1 = new Color(Integer.parseInt(command.getParams().get(4), 16));
                        Color gradient2 = new Color(Integer.parseInt(command.getParams().get(5), 16));
                        appIcon = appIcon.drawGradientOval(centerX, centerY, width, height, gradient1, gradient2);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "drawPolygon":
                    try {
                        int x1 = Integer.parseInt(command.getParams().get(0));
                        int y1 = Integer.parseInt(command.getParams().get(1));
                        Color colorG1 = new Color(Integer.parseInt(command.getParams().get(2), 16));
                        int x2 = Integer.parseInt(command.getParams().get(3));
                        int y2 = Integer.parseInt(command.getParams().get(4));
                        Color colorG2 = new Color(Integer.parseInt(command.getParams().get(5), 16));
                        int roundedBorder = Integer.parseInt(command.getParams().get(6));

                        ArrayList<Point> points = new ArrayList<>();
                        
                        for (int index = 7; index < command.getParams().size(); index++) {
                            int x = Integer.parseInt(command.getParams().get(index).split(" ")[0]);
                            int y = Integer.parseInt(command.getParams().get(index).split(" ")[1]);
                            Point point = new Point(x, y);
                            points.add(point);
                        }
                        
                        appIcon = appIcon.drawPolygon(x1, y1, colorG1, x2, y2, colorG2, roundedBorder, points);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("COMANDO INESISTENTE!");
                    break;
            }     
        }
        try {
            appIcon.build(template.getFolderPath() + "/" + template.getFilename(), template.getExtension());
            
            ImageIcon imageIcon = new ImageIcon();
            Image image = ImageIO.read(new File(template.getFolderPath(), template.getFilename() + "." + template.getExtension())).getScaledInstance(width, height, Image.SCALE_DEFAULT);
            imageIcon.setImage(image);
            lblimage.setIcon(imageIcon);            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }   
}