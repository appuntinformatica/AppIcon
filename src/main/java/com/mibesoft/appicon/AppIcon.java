package com.mibesoft.appicon;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class AppIcon {
    private final BufferedImage bufferedImage;
    
    public AppIcon(int width, int height, int borderRadius, Color gradient1, Color gradient2) {
        Color background = new Color(1f, 1f, 1f, 1.0f);

        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        
        Point2D pt1 = new Point2D.Float(width / 2, 0);
        Point2D pt2 = new Point2D.Float(width / 2, height);
        
        GradientPaint gradient = new GradientPaint(pt1, gradient1, pt2, gradient2, false);

        graphics.setColor(background);
        graphics.setPaint(gradient);
        graphics.fillRoundRect(0, 0, width, height, borderRadius, borderRadius);
    }

    public AppIcon drawCircle() {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.red);
        int padding = 20;
        int diametre = (bufferedImage.getWidth() <= bufferedImage.getHeight() ? bufferedImage.getWidth() : bufferedImage.getHeight()) - 2 * padding;
        graphics.drawOval(padding, padding, diametre, diametre);        
        return this;
    }
    
    public AppIcon addImage(String filename, int x, int y) {
        Graphics2D graphics = bufferedImage.createGraphics();
        try {
            Image image = ImageIO.read(new File(filename));
            graphics.drawImage(image, x, y, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }
    
    public AppIcon addImage(String filename, int x, int y, int width, int height) {
        Graphics2D graphics = bufferedImage.createGraphics();
        try {
            Image image = ImageIO.read(new File(filename)).getScaledInstance(width, height, Image.SCALE_DEFAULT);
            graphics.drawImage(image, x, y, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }
    
    public AppIcon addImage(String filename, int x, int y, int percReduced) {
        Graphics2D graphics = bufferedImage.createGraphics();
        try {
            Image image = ImageIO.read(new File(filename));
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            width = width * percReduced / 100;
            height = height * percReduced / 100;
            image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            graphics.drawImage(image, x, y, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }
    
    public AppIcon drawLine(int x1, int y1, int x2, int y2, int lineWidth, Color color) {
        Graphics2D graphics = bufferedImage.createGraphics();        
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(lineWidth));
        graphics.drawLine(x1, y1, x2, y2);
        return this;
    }
    
    public AppIcon drawGradientCircle(int padding, Color gradient1, Color gradient2) {
        Graphics2D graphics = bufferedImage.createGraphics();
        
        int diametre = (bufferedImage.getWidth() <= bufferedImage.getHeight() ? bufferedImage.getWidth() : bufferedImage.getHeight());
        diametre -= 2 * padding;
        Point2D pt1 = new Point2D.Float(diametre / 2, 0);
        Point2D pt2 = new Point2D.Float(diametre / 2, diametre);
        
        GradientPaint gradient = new GradientPaint(pt1, gradient1, pt2, gradient2, false);
        graphics.setPaint(gradient);
        
        graphics.fillOval(padding, padding, diametre, diametre);
        return this;
    }
    
    public AppIcon drawGradientOval(int centerX, int centerY, int width, int height, Color gradient1, Color gradient2) {
        Graphics2D graphics = bufferedImage.createGraphics();
        int x = centerX - width / 2;
        int y = centerY - height / 2;
        
        Point2D pt1 = new Point2D.Float(centerX, y);
        Point2D pt2 = new Point2D.Float(centerX, centerY + height / 2);
        
        GradientPaint gradient = new GradientPaint(pt1, gradient1, pt2, gradient2, false);
        graphics.setPaint(gradient);                
        graphics.fillOval(x, y, width, height);
        return this;
    }
        
    public AppIcon drawOval(int x, int y, int width, int height, int borderRadius, Color backgroundColor) {
        Graphics2D graphics = bufferedImage.createGraphics();
        
        graphics.setColor(backgroundColor);
        graphics.fillRoundRect(x, y, width, height, borderRadius, borderRadius);
        
        return this;
    }
    
    public AppIcon drawText(String fontName, Color fontColor, int fontSize, String text, int x, int y) {
        Graphics2D graphics = bufferedImage.createGraphics();

        Font font = new Font(fontName, Font.PLAIN, fontSize);                
        graphics.setFont(font);
        graphics.setColor(fontColor);
        graphics.drawString(text, x, y);
                
        return this;
    }
    
    public AppIcon drawText(String fontName, Color fontColor, int fontSize, String text) {
        Graphics2D graphics = bufferedImage.createGraphics();

        Font font = new Font(fontName, Font.PLAIN, fontSize);
        FontMetrics metrics = graphics.getFontMetrics(font);
        Rectangle rectangle = new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        int x = rectangle.x + (rectangle.width - metrics.stringWidth(text)) / 2;
        int y = rectangle.y + ((rectangle.height - metrics.getHeight()) / 2) + metrics.getAscent();
        graphics.setFont(font);
        graphics.setColor(fontColor);
        graphics.drawString(text, x, y);
                
        return this;
    }
    /*
    public AppIcon drawPolygon(int x1, int y1, Color colorG1, int x2, int y2, Color colorG2, ArrayList<Point> points) {
        Graphics2D graphics = bufferedImage.createGraphics();
        Polygon polygon = new Polygon();
        for (Point point : points) {
            System.out.println(point);
            polygon.addPoint((int) point.getX(), (int) point.getY());
        }
        GradientPaint gp = new GradientPaint(x1, y1, colorG1, x2, y2, colorG2);
        graphics.setPaint(gp);
        graphics.fill(polygon);
        return this;
    }
    */
    public AppIcon drawPolygon(int x1, int y1, Color colorG1, int x2, int y2, Color colorG2, float arcSize, ArrayList<Point> points) {
        Graphics2D graphics = bufferedImage.createGraphics();
        GeneralPath path = RoundedCornerPolygon.getRoundedGeneralPathFromPoints(arcSize, points);
        GradientPaint gp = new GradientPaint(x1, y1, colorG1, x2, y2, colorG2);
        graphics.setPaint(gp);
        graphics.fill(path);
        
        return this;
    }
    
    public Point interpolate(Point p1, Point p2, double t) {
        return new Point((int) Math.round(p1.x * (1 - t) + p2.x * t), (int) Math.round(p1.y * (1 - t) + p2.y * t));
    }
    
    
    public void build(String filename, String extension) throws IOException {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.dispose();
        ImageIO.write(bufferedImage, extension, new File(filename + "." + extension));
    }
}
