package com.mygdx.pairanimalgame;

import java.util.ArrayList;
import java.util.List;

public
class Path {
    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    List<Point> points = new ArrayList<>();

    void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    @Override
    public String toString() {
        return "Path" + points.toString();
    }

    public void print() {
        System.out.println(this);
    }
}
