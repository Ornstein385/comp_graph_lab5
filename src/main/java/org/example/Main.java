package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

class PolygonPanel extends JPanel {
    private Polygon[] polygons;
    private Random rand = new Random();
    private PolygonCountFunction polygonCountFunction = new PolygonCountFunction();

    public static int minDemoCellSize = 5;

    public PolygonPanel(Polygon[] polygons) {
        this.polygons = polygons;
        this.setPreferredSize(new Dimension(1024, 1024));
    }

    public void regeneratePolygons(Polygon[] newPolygons) {
        this.polygons = newPolygons;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Polygon poly : polygons) {
            g2d.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            g2d.fillPolygon(poly);
        }
        g.setColor(Color.BLACK);
        drawBorder(g, 0, 0, 1023, 1023);
    }

    private void drawBorder(Graphics g, int x1, int y1, int x2, int y2) {
        if (x2 - x1 < minDemoCellSize || y2 - y1 < minDemoCellSize) {
            return;
        }
        g.drawRect(x1, y1, x2 - x1, y2 - y1);
        Rectangle r = new Rectangle(x1, y1, (x2 - x1) / 2, (y2 - y1) / 2); //левый верхний
        if (polygonCountFunction.apply(polygons, r) > 1) {
            drawBorder(g, x1, y1, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2);
        }
        r = new Rectangle(x1 + (x2 - x1) / 2, y1, (x2 - x1) / 2, (y2 - y1) / 2); //правый верхний
        if (polygonCountFunction.apply(polygons, r) > 1) {
            drawBorder(g, x1 + (x2 - x1) / 2, y1, x2, y1 + (y2 - y1) / 2);
        }
        r = new Rectangle(x1, y1 + (y2 - y1) / 2, (x2 - x1) / 2, (y2 - y1) / 2); //левый нижний
        if (polygonCountFunction.apply(polygons, r) > 1) {
            drawBorder(g, x1, y1 + (y2 - y1) / 2, x1 + (x2 - x1) / 2, y2);
        }
        r = new Rectangle(x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, (x2 - x1) / 2, (y2 - y1) / 2); //правый нижний
        if (polygonCountFunction.apply(polygons, r) > 1) {
            drawBorder(g, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Random Polygons");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 1024);

        PolygonGenerator generator = new PolygonGenerator();
        Polygon[] polygons = generator.generateMultiplePolygons(10, 3, 10);
        PolygonPanel panel = new PolygonPanel(polygons);

        JButton regenerateButton = new JButton("Regenerate");
        regenerateButton.addActionListener(e -> {
            Polygon[] newPolygons = generator.generateMultiplePolygons(10, 3, 10);
            panel.regeneratePolygons(newPolygons);
        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(regenerateButton, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }
}

