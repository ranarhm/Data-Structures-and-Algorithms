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
 * This class represents a command parser that reads through inputs and based on
 * them executes actions for the Skip List.
 *
 * @author Joan Piayet Perez Lozano (joanperezl)
 * @author Raena Rahimi Bafrani (raenar)
 * @version April 16, 2022
 */
public class Command {

    private String cmd;
    private Rectangle1 rectangle;

    /**
     * Constructor for Command class, initializes rectangle and scanner for
     * reading file
     * 
     * @param input
     *            the input file to be read
     * 
     * @param rectangle
     *            the rectangle into skip list
     */
    public Command(String input, Rectangle1 rectangle) {
        this.cmd = input;
        this.rectangle = rectangle;

    }


    /**
     * Runs the commands in the given file
     */
    public void executeCommandOperation() {

        String[] currentLineArr = cmd.trim().split(" +");

        if (currentLineArr[0].equalsIgnoreCase("insert")) {
            rectangle.insert(currentLineArr[1], Integer.valueOf(
                currentLineArr[2]), Integer.valueOf(currentLineArr[3]), Integer
                    .valueOf(currentLineArr[4]), Integer.valueOf(
                        currentLineArr[5]));
        }
        else if (currentLineArr[0].equalsIgnoreCase("remove")) {
            if (currentLineArr.length == 2) {
                // remove by matching name
                rectangle.remove(currentLineArr[1]);
            }
            else {
                // remove by matching coordinates + dimensions
                rectangle.remove(Integer.valueOf(currentLineArr[1]), Integer
                    .valueOf(currentLineArr[2]), Integer.valueOf(
                        currentLineArr[3]), Integer.valueOf(currentLineArr[4]));
            }
        }
        else if (currentLineArr[0].equalsIgnoreCase("regionsearch")) {
            rectangle.regionSearch(Integer.valueOf(currentLineArr[1]), Integer
                .valueOf(currentLineArr[2]), Integer.valueOf(currentLineArr[3]),
                Integer.valueOf(currentLineArr[4]));
        }
        else if (currentLineArr[0].equalsIgnoreCase("intersections")) {
            rectangle.intersections();
        }
        else if (currentLineArr[0].equalsIgnoreCase("search")) {
            rectangle.search(currentLineArr[1]);
        }
        else if (currentLineArr[0].equalsIgnoreCase("dump")) {
            rectangle.dump();
        }

    }

}
