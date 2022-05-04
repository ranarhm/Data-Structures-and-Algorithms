import java.util.Objects;

//import java.util.Objects;

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
 * The RectangleFigure class is responsible for holding information about
 * Rectangles
 *
 * @author Joan Piayet Perez Lozano (joanperezl)
 * @author Raena Rahimi Bafrani (raenar)
 * @version April 16, 2022
 */
public class RectangleFigure {
    private String name;
    private int xCoord;
    private int yCoord;
    private int width;
    private int height;

    /**
     * Constructor that creates a rectangle object
     * 
     * @param name
     *            the name of the rectangle
     * @param xCoord
     *            the x position of the rectangle
     * @param yCoord
     *            the y position of the rectangle
     * @param width
     *            the width of the rectangle
     * @param height
     *            the height of the rectangle
     * 
     */
    public RectangleFigure(
        String name,
        int xCoord,
        int yCoord,
        int width,
        int height) {
        if (!validProperties(name, xCoord, yCoord, width, height)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.width = width;
        this.height = height;

        System.out.println("Rectangle inserted: (" + name + ", " + xCoord + ", "
            + yCoord + ", " + width + ", " + height + ")");
    }


    /**
     * Returns the name of the rectangle
     * 
     * @return name the name of the rectangle
     * 
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the x position of the rectangle
     * 
     * @return xCoord the x position of the rectangle
     * 
     */
    public int getxCoord() {
        return xCoord;
    }


    /**
     * Gets the y position of the rectangle
     * 
     * @return yCoord the y position of the rectangle
     * 
     */
    public int getyCoord() {
        return yCoord;
    }


    /**
     * Gets the width of the rectangle
     * 
     * @return width the width of the rectangle
     * 
     */
    public int getWidth() {
        return width;
    }


    /**
     * Gets the height of the rectangle
     * 
     * @return height the height of the rectangle
     * 
     */
    public int getHeight() {
        return height;
    }


    /**
     * Validates the the correctness of the info for the rectangle.
     * 
     * @param n
     *            the name of the rectangle
     * @param x
     *            the x position of the rectangle
     * @param y
     *            the y position of the rectangle
     * @param w
     *            the width of the rectangle
     * @param h
     *            the height of the rectangle
     */
    private boolean validProperties(String n, int x, int y, int w, int h) {

        return !(!nameIsValid(n) || x < 0 || x >= 1024 || y < 0 || y >= 1024
            || w <= 0 || h <= 0 || (x + w) > 1024 || (y + h) > 1024);

    }


    /**
     * Validates the the correctness of the naming convention for the rectangle.
     * 
     * @param rectName
     *            the name of the rectangle
     * @return true if name is valid
     */
    private boolean nameIsValid(String rectName) {
        if (rectName.length() < 1) {
            // Requires at least one character
            // System.out.println("less than 1");
            return false;
        }

        // check for correctness of characters
        for (int i = 0; i < rectName.length(); i++) {
            int currChar = rectName.charAt(i);
            // if first character is not a letter
            if (i == 0 && !Character.isLetter(currChar)) {
                // System.out.println("first character is not a letter");
                return false;
            }

            if (i >= 1 && !Character.isLetterOrDigit(currChar)
                && currChar != '_') {
                // System.out.println("some remaining character is not a letter,
                // digit neither _");
                return false;
            }
        }
        return true;
    }


    /**
     * Gets the string of info
     * 
     * @return string of necessary info
     */
    public String toString() {
        return name + ", " + xCoord + ", " + yCoord + ", " + width + ", "
            + height;
    }

    /**
     * @param obj
     *            object that is supposed to be a rectangle
     * @return true if this rectangle and other rectangle have same dimensions
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RectangleFigure other = (RectangleFigure)obj;
        return height == other.height && Objects.equals(name, other.name)
            && width == other.width && xCoord == other.xCoord
            && yCoord == other.yCoord;
    }

}
