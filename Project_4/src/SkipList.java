import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

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
 * 
 *
 * @author Joan Piayet Perez Lozano (joanperezl)
 * @author Raena Rahimi Bafrani (raenar)
 * @version April 16, 2022
 * @param <K>
 *            the key value
 * @param <E>
 *            the value to be stored with the key
 */
class SkipList<K extends Comparable<K>, E>
    implements Dictionary<K, E>, Iterable<E> {

    /**
     * Skip node class to handle the nodes of the skip list
     * 
     * @param <K>
     *            the type
     * @param <E>
     *            the type
     */
    @SuppressWarnings("hiding")
    public class SkipNode<K extends Comparable<K>, E> {
        private KVPair<K, E> rec;
        private SkipNode<K, E>[] forward;

        /**
         * @return value the value of the skip node
         */
        public E element() {
            return rec.value();
        }


        /**
         * 
         * @return key of the skip node
         */
        public K key() {
            return rec.key();
        }


        /**
         * Constructor for skip node
         * 
         * @param key
         *            the key for the skip node
         * @param elem
         *            the element for the skip node
         * @param level
         *            the amount of skip nodes this skip node point to
         */
        @SuppressWarnings("unchecked")
        public SkipNode(K key, E elem, int level) {
            rec = new KVPair<K, E>(key, elem);
            forward = new SkipNode[level + 1];
            for (int i = 0; i < level; i++) {
                forward[i] = null;
            }
        }
    }

    private SkipNode<K, E> head;
    private int level;
    private int size;
    static private Random ran = new Random(); // Hold the Random class object

    /**
     * Constructor for skip list
     * 
     */
    public SkipList() {
        head = new SkipNode<K, E>(null, null, 0);
        level = -1;
        size = 0;
    }


    /**
     * Picks a level using a geometric distribution
     * 
     * @return level random level from 0 to 2
     */
    public int randomLevel() {
        int lev;
        // ran is random generator
        for (lev = 0; Math.abs(ran.nextInt()) % 2 == 0; lev++) {
            // Do nothing
        }
        return lev;
    }


    /**
     * Adjusts head node of skip list
     * 
     * @param newLevel
     *            new depth of head node
     */
    private void adjustHead(int newLevel) {
        SkipNode<K, E> temp = head;
        head = new SkipNode<K, E>(null, null, newLevel);
        for (int i = 0; i <= level; i++) {
            head.forward[i] = temp.forward[i];
        }
        level = newLevel;
    }


    /** Insert a key, element pair into the skip list */

    /**
     * Insert a key, element pair into the skip list
     * 
     * @param key
     *            key that will be inserted into list
     * @param elem
     *            the element that will inserted into the list
     */
    @SuppressWarnings("unchecked")
    @Override
    public void insert(K key, E elem) {
        int newLevel = randomLevel(); // New node's level
        if (newLevel > level) { // If new node is deeper
            adjustHead(newLevel);
        } // adjust the header
          // Track end of level
        SkipNode<K, E>[] update = new SkipNode[level + 1];
        SkipNode<K, E> x = head; // Start at header node
        for (int i = level; i >= 0; i--) { // Find insert position
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(
                key) < 0)) {
                x = x.forward[i];
            }
            update[i] = x; // Track end at level i
        }
        x = new SkipNode<K, E>(key, elem, newLevel);
        for (int i = 0; i <= newLevel; i++) { // Splice into list
            x.forward[i] = update[i].forward[i]; // Who x points to
            update[i].forward[i] = x; // Who points to x
        }
        size++; // Increment dictionary size
    }


    /**
     * Removes skip node from list
     *
     * @param key
     *            determines the node to be removed
     * @return skip node that was removed, null if no node can be found
     */
    @Override
    public E remove(K key) {
        SkipNode<K, E> currNode = head; // Dummy header node
        SkipNode<K, E> prevNode = null;
        for (int i = level; i >= 0; i--) { // For each level...
            while ((currNode.forward[i] != null) && (currNode.forward[i].key()
                .compareTo(key) < 0)) { // go forward
                prevNode = currNode;
                currNode = currNode.forward[i]; // Go one last step
            }
        }
        prevNode = currNode;
        currNode = currNode.forward[0]; // currNode is now node to remove

        // if current node is null, we did not find anything
        if (currNode == null) {
            return null;
        }

        // check all references that prevNode has
        for (int i = 0; i < prevNode.forward.length; i++) {
            // if at ith level prevNode points to currNode, re reference to next
            if (prevNode.forward[i] == currNode) {
                prevNode.forward[i] = currNode.forward[i];
            }
        }
        // update size
        this.size--;
        return currNode.element();
    }


    /**
     * Finds all KVPairs given a search key
     * 
     * @param key
     *            key used to find results
     * @return list of all KVPairs found using searchKey
     */
    @Override
    public SinglyLinkedList<E> find(K key) {
        SkipNode<K, E> currNode = head; // Dummy header node
        for (int i = level; i >= 0; i--) { // For each level...
            while ((currNode.forward[i] != null) && (currNode.forward[i].key()
                .compareTo(key) < 0)) { // go forward
                currNode = currNode.forward[i];
            } // Go one last step
        }
        currNode = currNode.forward[0]; // Move to actual record, if it exists
        SinglyLinkedList<E> rectList = new SinglyLinkedList<>();
        // Collect all matches
        while ((currNode != null) && (currNode.key().compareTo(key) == 0)) {
            rectList.addLast(currNode.element());
            currNode = currNode.forward[0];
        }
        return rectList.size() > 0 ? rectList : null;
    }


    /**
     * @return size of the list
     */
    @Override
    public int size() {
        return this.size;
    }


    /**
     * Print nodes in Skip list
     * 
     */
    public void printNodes() {
        SkipNode<K, E> currNode = this.head;
        System.out.println("SkipList dump:");
        while (currNode != null) {
            System.out.println("Node has depth " + currNode.forward.length
                + ", Value (" + currNode.element() + ")");
            currNode = currNode.forward[0];
        }
        System.out.println("SkipList size is: " + size());
    }


    /**
     * Creates an Iterator object.
     *
     * @return new skip list Iterator Object.
     */
    @Override
    public Iterator<E> iterator() {
        return new SkipListIterator();
    }

    /**
     * Iterator Implementation of SkipList.
     * 
     */
    public class SkipListIterator implements Iterator<E> {

        private SkipNode<K, E> currNode = head;

        /**
         * Boolean Method to check if there are more elements left In List.
         *
         * @return T/F based upon if elements are left in List.
         */
        @Override
        public boolean hasNext() {
            return (currNode.forward[0] != null);
        }


        /**
         * Next Value in the List.
         *
         * @return The Next Value in the list.
         * @throws NoSuchElementException
         *             If No Nodes are left.
         */
        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            else {
                currNode = currNode.forward[0];
                return currNode.element();
            }
        }

    }
}
