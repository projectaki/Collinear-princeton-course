import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int count;
    private final LineSegment[] ls;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (i != j && points[i] == points[j]) {
                    throw new IllegalArgumentException();
                }

            }
        }

        ls = new LineSegment[points.length * 2];

        for (int p = 0; p < points.length; p++) {
            for (int q = 0; q < points.length; q++) {
                for (int r = 0; r < points.length; r++) {
                    for (int s = 0; s < points.length; s++) {
                        // System.out.println(tempcount++ + ":" + points[p] + "," + points[q] + "," + points[r] + "," + points[s]);


                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])
                                && points[p].slopeTo(points[r]) == points[p].slopeTo(points[s])) {
                            if (points[p].compareTo(points[q]) < 0
                                    && points[q].compareTo(points[r]) < 0
                                    && points[r].compareTo(points[s]) < 0) {
                                ls[count++] = new LineSegment(points[p], points[s]);
                            }
                        }
                    }
                }
            }
        }

    }

    public int numberOfSegments()        // the number of line segments
    {
        return count;
    }

    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] templs = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            templs[i] = ls[i];
        }
        return templs;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }


        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // System.out.println(Arrays.toString(collinear.ls));
        System.out.println("Seggys: " + Arrays.toString(collinear.segments()));
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();


    }


}
