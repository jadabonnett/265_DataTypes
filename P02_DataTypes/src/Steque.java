import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Steque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int itemSize = 0;
    private int version = 0;
    private int oldVersion;
    private class Node{
        Item item;
        Node next;
        public Node(Item item, Node next){
                this.item = item;
                this.next = next;
        }
    }

    public Steque() {

    }

    // returns the number of items stored
    public int size() {
        return itemSize;
    }

    // returns true if steque is empty
    public boolean isEmpty() {
        return (itemSize == 0);
}

    // enqueues item to bottom of steque
    public void enqueue(Item item) {
        if (itemSize == 0){
            last = new Node(item, null);
            last.item = item;
            first = last;
        }
        else {
            Node oldlast = last;
            last = new Node(item, oldlast);
            last.item = item;
            last.next = null;
            oldlast.next = last;
        }
        itemSize ++;
    }

    // pushes item to top of steque
    public void push(Item item) {
        if (itemSize == 0){
            last = new Node(item, null);
            last.item = item;
            first = last;
        }
        else {
            Node oldfirst = first;
            first = new Node(item, oldfirst);
            first.item = item;
            first.next = oldfirst;
        }
        itemSize ++;
    }

    // pops and returns top item
    public Item pop() throws NoSuchElementException {
        Item item = first.item;
        first = first.next;
        itemSize --;
        return item;
    }

    // returns new Iterator<Item> that iterates over items in steque
    @Override
    public Iterator<Item> iterator() {
        oldVersion = version;
        return new ListIterator() ;
    }

    public class ListIterator implements Iterator<Item> {
        Node current = first;
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
            return oldcurrent.item;
        }
    }


    // perform unit testing here
    public static void main(String[] args) throws NoSuchElementException {
        Steque mySteque = new Steque<Integer>();
        onlyPush(mySteque);

        Iterator myIterator = mySteque.iterator();
        while(myIterator.hasNext()){
            StdOut.println(myIterator.next());
        }
    }
    public static void onlyPush( Steque<Integer> mySteque){
        mySteque.push(4);
        mySteque.pop();
        mySteque.enqueue(145);
    }
}