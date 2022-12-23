import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinimumStack<Item extends Comparable> implements Iterable<Item> {
    private int version = 0;
    private int itemSize = 0;
    private int oldVersion;
    Node mini;
    Node oldMini;
    private Node first = null;
    private Node last = null;
    private class Node{
        Item item;
        Node next;
        public Node(Item item, Node next){
            this.item = item;
            this.next = next;
        }
    }
    public MinimumStack() {

    }
    // returns the number of items stored
    public int size() {
        return itemSize;
    }

    // returns true if empty
    public boolean isEmpty() {
        return (itemSize == 0);
    }

    // push item onto stack
    public void push(Item item) {

        if (itemSize == 0){
            last = new Node(item, null);
            last.item = item;
            first = last;
            mini = first;
        }
        else {
            Node oldfirst = first;
            first = new Node(item, oldfirst);
            first.item = item;
            first.next = oldfirst;
            if (first.item.compareTo(mini.item)==-1){
                oldMini = mini;
                mini = first;
            }
        }
        itemSize ++;
    }

    // pop and return the top item
    public Item pop() throws NoSuchElementException {
        if (first == mini){ // what if size is only 1?
            mini = oldMini;
        }
        Item item = first.item;
        first = first.next;
        itemSize --;
        return item;
    }

    // returns the minimum item in constant time
    public Item minimum() throws NoSuchElementException {
        return mini.item;
    }

    // returns new Iterator<Item> that iterates over items
    @Override
    public Iterator<Item> iterator() {
        int oldVersion = version;
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


    public static void main(String[] args) {
        MinimumStack myMinStack = new MinimumStack<Integer>();
        onlyEnqueue(myMinStack);

        Iterator myIterator = myMinStack.iterator();
        while (myIterator.hasNext()) {
            StdOut.println(myIterator.next());
        }
    }
    public static void onlyEnqueue( MinimumStack<Integer> myMinStack){
            myMinStack.push(6);
            myMinStack.push(14);
            myMinStack.push(37583);
            StdOut.println(myMinStack.minimum());
            myMinStack.push(3);
            StdOut.println(myMinStack.minimum());
            myMinStack.pop();
    }

}

