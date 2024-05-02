package org.example;

import java.awt.*;
import java.util.Arrays;
import java.util.function.BiFunction;

public class PolygonCountFunction implements BiFunction<Polygon[], Rectangle, Long> {
    @Override
    public Long apply(Polygon[] polygons, Rectangle rectangle) {
        return Arrays.stream(polygons)
                .filter(polygon ->
                        polygon.contains(rectangle)
                                || polygon.intersects(rectangle)
                                || rectangle.contains(polygon.getBounds())).count();
    }
}
