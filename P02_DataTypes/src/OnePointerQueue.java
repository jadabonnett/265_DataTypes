import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OnePointerQueue<Item> implements Iterable<Item> {

    private static int itemSize = 0;
    private Node last = null;
    private int version = 0;
    private int oldVersion;
    private static int counter;

    private class Node{
        Item item;
        Node next;
        public Node(Item item, Node next){
            this.item = item;
            this.next = next;
        }
    }
    public OnePointerQueue() {

    }

    // returns the number of items stored
    public int size() {
        return itemSize;
    }

    // returns true iff empty
    public boolean isEmpty() {
        return (itemSize == 0);
    }

    // enqueue item to "back"
    public void enqueue(Item item) {
        if (itemSize == 0){
            last = new Node(item, null);
            last.item = item;
            last.next = last;
        }
        else {
            Node oldlast = last;
            Node oldNext = last.next;
            last = new Node(item, oldlast);
            last.item = item;
            last.next = oldNext;
            oldlast.next = last;
        }
        itemSize ++;
    }

    // dequeue item from "front"
    public Item dequeue() throws NoSuchElementException {
        Item returnedItem = last.next.item;
        if (itemSize == 1){
            last = null;
        }
        else {
            last.next = last.next.next;
        }
        // create if for edge cases of only itemSize of 1 (null out last)
        itemSize--;
        return returnedItem;
    }

    // returns new Iterator<Item> that iterates over items
    @Override
    public Iterator<Item> iterator() {
        oldVersion = version;
        counter = 0;
        return new ListIterator() ;
    }

    public class ListIterator implements Iterator<Item> {
        Node current = last.next;
        public boolean hasNext(){
            if (version != oldVersion){
                throw new ConcurrentModificationException();
            }
            return (current != null);
        }

        public Item next(){
            if (version != oldVersion){
                throw new ConcurrentModificationException();
            }
            Node oldcurrent = current;
            current = current.next;
            counter ++;
            return oldcurrent.item;
        }
    }

    // perform unit testing here
    public static void main(String[] args) {
        OnePointerQueue myQueue = new OnePointerQueue<Integer>();
        tester(myQueue);

        Iterator myIterator = myQueue.iterator();
        while(myIterator.hasNext() && (counter < itemSize)){
            StdOut.println(myIterator.next());
        }
    }
    public static void tester( OnePointerQueue<Integer> myQueue) {
        myQueue.enqueue(5);
        myQueue.enqueue(7);
        myQueue.dequeue();
    }
}
