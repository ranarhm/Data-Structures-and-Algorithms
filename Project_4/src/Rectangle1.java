import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * The Rectangle class represents a Skip List data structure that stores...(to
 * elaborate)
 *
 * @author Joan Piayet Perez Lozano (joanperezl)
 * @author Raena Rahimi Bafrani (raenar)
 * @version April 16, 2022
 */
public class Rectangle1 {

    private SkipList<String, RectangleFigure> skipList;

    /**
     * Constructor that creates a Skip List data structure
     * 
     */
    public Rectangle1() {
        skipList = new SkipList<>();
    }


    /**
     * Inserts a rectangle into the skip list
     * 
     * @param name
     *            the name of the rectangle
     * @param x
     *            the x position of the rectangle
     * @param y
     *            the y position of the rectangle
     * @param w
     *            the width of the rectangle
     * @param h
     *            the height of the rectangle
     *
     */
    public void insert(String name, int x, int y, int w, int h) {
        try {
            RectangleFigure newRectangle = new RectangleFigure(name, x, y, w,
                h);
            // if object creation succeeded, insert rectangle
            skipList.insert(name, newRectangle);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Rectangle rejected: (" + name + ", " + x + ", "
                + y + ", " + w + ", " + h + ")");
        }
    }


    /**
     * Remove rectangle with input name, if there is more than one match, remove
     * the first found.
     * 
     * @param name
     *            the name of the rectangle to be removed
     */
    public void remove(String name) {
        RectangleFigure removedRectangle = skipList.remove(name);

        if (removedRectangle == null) {
            System.out.println("Rectangle not removed: " + name);
        }
        else {
            System.out.println("Rectangle removed: (" + removedRectangle + ")");
        }
    }


    /**
     * Remove rectangle with x,y, width and height, if there is more than one
     * match, remove the first found.
     * 
     * @param x
     *            the x position of the rectangle to be removed
     * @param y
     *            the y position of the rectangle to be removed
     * @param width
     *            the width of the rectangle to be removed
     * @param height
     *            the height of the rectangle to be removed
     */
    public void remove(int x, int y, int width, int height) {
        if (x < 0 || y < 0 || width <= 0 || height <= 0) {
            System.out.println("Rectangle rejected: (" + x + ", " + y + ", "
                + width + ", " + height + ")");
            return;
        }
        else {
            Iterator<RectangleFigure> itr = skipList.iterator();
            while (itr.hasNext()) {
                RectangleFigure currRect = itr.next();
                // if exists, remove it
                if (currRect.getxCoord() == x && currRect.getyCoord() == y

                    && currRect.getWidth() == width && currRect

                        .getHeight() == height) {

                    RectangleFigure rectRemoved = skipList.remove(currRect

                        .getName());

                    System.out.println("Rectangle removed: (" + rectRemoved

                        + ")");
                    return;
                }
            }
            System.out.println("Rectangle not removed: (" + x + ", " + y + ", "
                + width + ", " + height + ")");

        }

    }

    /**
     * Searches for coordinates in skip list
     * 
     * @param x
     *            the x position of the region search rectangle
     * @param y
     *            the y position of the region search rectangle
     * @param width
     *            the width of the region search rectangle
     * @param height
     *            the height of the region search rectangle
     */
    public void regionSearch(int x, int y, int width, int height) {
        // validate params
        if (width <= 0 || height <= 0) {
            System.out.println("Rectangle rejected: (" + x + ", " + y + ", "
                + width + ", " + height + ")");
            return;
        }

        // bottom left and top right coordinates of region search rectangle
        int bottomLeft1X = x;
        int bottomLeft1Y = y;
        int topRight1X = x + width;
        int topRight1Y = y + height;

        System.out.println("Rectangles intersecting region (" + x + ", " + y
            + ", " + width + ", " + height + "):");
        // iterate through elements and print if intersects
        Iterator<RectangleFigure> itr = skipList.iterator();
        while (itr.hasNext()) {
            // bottom left and top right coordinates of current rectangle
            RectangleFigure currRect = itr.next();
            int bottomLeft2X = currRect.getxCoord();
            int bottomLeft2Y = currRect.getyCoord();
            int topRight2X = currRect.getxCoord() + currRect.getWidth();
            int topRight2Y = currRect.getyCoord() + currRect.getHeight();

            // skip non-intersects
            if (bottomLeft1X > topRight2X || topRight1X < bottomLeft2X) {
                continue;
            }

            if (bottomLeft1Y > topRight2Y || topRight1Y < bottomLeft2Y) {
                continue;
            }
            System.out.println(currRect); // print intersect
        }
    }


    /**
     * Finds intersections and prints them out
     */
    public void intersections() {
        System.out.println("Intersection pairs:");
        Iterator<RectangleFigure> outterItr = skipList.iterator();
        while (outterItr.hasNext()) {
            RectangleFigure outterRect = outterItr.next();
            Iterator<RectangleFigure> innerItr = skipList.iterator();
            while (innerItr.hasNext()) {
                RectangleFigure innerRect = innerItr.next();
                // if both point to same object, skip
                if (outterRect == innerRect) {
                    continue;
                }

                // iterate through elements and print if intersect
                // outterRect corners
                int bottomLeft1X = outterRect.getxCoord();
                int bottomLeft1Y = outterRect.getyCoord();
                int topRight1X = outterRect.getxCoord() + outterRect.getWidth();
                int topRight1Y = outterRect.getyCoord() + outterRect
                    .getHeight();

                // innerRect corners
                int bottomLeft2X = innerRect.getxCoord();
                int bottomLeft2Y = innerRect.getyCoord();
                int topRight2X = innerRect.getxCoord() + innerRect.getWidth();
                int topRight2Y = innerRect.getyCoord() + innerRect.getHeight();

                // skip non-intersects
                if (bottomLeft1X >= topRight2X || topRight1X <= bottomLeft2X) {
                    continue;
                }

                if (bottomLeft1Y >= topRight2Y || topRight1Y <= bottomLeft2Y) {
                    continue;
                }

                System.out.println("(" + outterRect + " | " + innerRect + ")");
            }
        }
    }


    /**
     * Search for the name of the rectangle
     * 
     * @param rectangleName
     *            name to be searched
     */
    public void search(String rectangleName) {
        SinglyLinkedList<RectangleFigure> rectList = skipList.find(
            rectangleName);
        if (rectList != null) {
            SinglyLinkedList<RectangleFigure>.ListNode<RectangleFigure> 
                currNode = rectList.getHead();
            while (currNode != null) {
                System.out.println("(" + currNode.getValue().toString() + ")");
                currNode = currNode.getNext();
            }
        }
        else {
            System.out.println("Rectangle not found: " + rectangleName);
        }
    }


    /**
     * Prints out skip list with associated element
     * 
     */
    public void dump() {
        skipList.printNodes();
    }


    /**
     * The main method that processes input file through scanFile
     * 
     * @param args
     *            input file
     */
    public static void main(String[] args) {
        Rectangle1 rectangleControl = new Rectangle1();
        // Read commands
        Scanner scannedFile = scanFile(args[0]);
        while (scannedFile.hasNext()) {
            String line = trimWhitespaceOf(scannedFile.nextLine());
            if (!line.isEmpty()) {
                Command currCommand = new Command(line, rectangleControl);
                currCommand.executeCommandOperation();
            }
        }
        scannedFile.close();
    }


    /**
     * Trims leading and trailing whitespace. And also extra space that might be
     * present in between the commands.
     * 
     * @param lineRead
     *            a String containing the commands to be trimmed.
     * @return a String with the extra whitespace removed.
     */
    private static String trimWhitespaceOf(String lineRead) {
        String cleanedLine = lineRead.trim().replaceAll("\\s+", " ");
        return cleanedLine;
    }


    /**
     * Scans the file to be read
     * 
     * @param filePath
     *            path to the file to be read
     * @return scanned file as a scanner
     */
    public static Scanner scanFile(String filePath) {
        try {
            FileReader file = new FileReader(filePath);
            return new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
