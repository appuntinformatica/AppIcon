/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mibesoft.appicon;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author andrea
 */
public class RoundedCornerPolygon {

    public static GeneralPath getRoundedGeneralPath(Polygon polygon) {
        java.util.List<int[]> l = new ArrayList<int[]>();
        for (int i = 0; i < polygon.npoints; i++) {
            l.add(new int[]{polygon.xpoints[i], polygon.ypoints[i]});
        }
        return getRoundedGeneralPath(l);
    }

    public static GeneralPath getRoundedGeneralPath(java.util.List<int[]> l) {
        java.util.List<Point> list = new ArrayList<Point>();
        for (int[] point : l) {
            list.add(new Point(point[0], point[1]));
        }
        return getRoundedGeneralPathFromPoints(30, list);
    }

    public static GeneralPath getRoundedGeneralPathFromPoints(java.util.List<ArcPoint> l) {
        l.add(l.get(0));
        l.add(l.get(1));
        GeneralPath p = new GeneralPath();
        p.moveTo(l.get(0).x, l.get(0).y);
        for (int pointIndex = 1; pointIndex < l.size() - 1; pointIndex++) {
            Point p1 = new Point(l.get(pointIndex - 1).getX(), l.get(pointIndex - 1).getY());
            Point p2 = new Point(l.get(pointIndex).getX(), l.get(pointIndex).getY());
            Point p3 = new Point(l.get(pointIndex + 1).getX(), l.get(pointIndex + 1).getY());
            Point mPoint = calculatePoint(l.get(pointIndex).getArcSize(), p1, p2);
            p.lineTo(mPoint.x, mPoint.y);
            mPoint = calculatePoint(l.get(pointIndex).getArcSize(), p3, p2);
            p.curveTo(p2.x, p2.y, p2.x, p2.y, mPoint.x, mPoint.y);
        }
        return p;
    }
    
    public static GeneralPath getRoundedGeneralPathFromPoints(float arcSize, java.util.List<Point> l) {
        l.add(l.get(0));
        l.add(l.get(1));
        GeneralPath p = new GeneralPath();
        p.moveTo(l.get(0).x, l.get(0).y);
        for (int pointIndex = 1; pointIndex < l.size() - 1; pointIndex++) {
            Point p1 = l.get(pointIndex - 1);
            Point p2 = l.get(pointIndex);
            Point p3 = l.get(pointIndex + 1);
            Point mPoint = calculatePoint(arcSize, p1, p2);
            p.lineTo(mPoint.x, mPoint.y);
            mPoint = calculatePoint(arcSize, p3, p2);
            p.curveTo(p2.x, p2.y, p2.x, p2.y, mPoint.x, mPoint.y);
        }
        return p;
    }

    private static Point calculatePoint(float arcSize, Point p1, Point p2) {
        double d1 = Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        double per = arcSize / d1;
        double d_x = (p1.x - p2.x) * per;
        double d_y = (p1.y - p2.y) * per;
        int xx = (int) (p2.x + d_x);
        int yy = (int) (p2.y + d_y);
        return new Point(xx, yy);
    }

    public static void main(String args[]) {
        JFrame f = new JFrame("Rounded Corner Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0,
                        getBackground().brighter().brighter(), 0, getHeight(),
                        getBackground().darker().darker());

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                //int[][] a = {{70, 0}, {100, 50}, {50, 100}, {0, 50}};                
                int[][] a = {{10, 10}, {50, 10}, {10, 50}};
                
                GeneralPath p = getRoundedGeneralPath(Arrays.asList(a));
                g2d.setColor(Color.red);
                g2d.fill(p);
                super.paintComponent(grphcs);
            }
        };
        contentPane.setOpaque(false);
        f.setContentPane(contentPane);
        contentPane.add(new JLabel("test"));
        f.setSize(200, 200);
        f.setVisible(true);
    }
}
