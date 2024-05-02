package org.example;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PolygonGenerator {
    private Random rand = new Random();

    public Polygon[] generateMultiplePolygons(int nFigures, int minSize, int maxSize) {
        List<Polygon> polygons = new ArrayList<>();

        for (int i = 0; i < nFigures; i++) {
            double centerX = rand.nextDouble() * 1000;
            double centerY = rand.nextDouble() * 1000;
            int nPoints = rand.nextInt(maxSize - minSize + 1) + minSize;

            List<Double> angles = new ArrayList<>();
            for (int j = 0; j < nPoints; j++) {
                angles.add(rand.nextDouble() * 2 * Math.PI);
            }
            angles.sort(Double::compare);

            List<Double> radii = new ArrayList<>();
            for (int j = 0; j < nPoints; j++) {
                radii.add(rand.nextDouble() * 200 + 10);
            }

            int[] pointsX = new int[nPoints];
            int[] pointsY = new int[nPoints];
            for (int j = 0; j < nPoints; j++) {
                pointsX[j] = (int) (centerX + radii.get(j) * Math.cos(angles.get(j)));
                pointsY[j] = (int) (centerY + radii.get(j) * Math.sin(angles.get(j)));
            }

            polygons.add(new Polygon(pointsX, pointsY, nPoints));
        }
        return polygons.toArray(new Polygon[0]);
    }
}