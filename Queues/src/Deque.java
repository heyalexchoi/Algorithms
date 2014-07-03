/**
 * Created by alexchoi1 on 7/2/14.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

    private int N; // number of elements in Deque
    private Node<Item> first; // first element in Deque
    private Node<Item> last; // last element in Deque

    // a helper double linked list data type
    private class Node<Item> {
        Item item;
        Node<Item> previous;
        Node<Item> next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (N==0);
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item==null) {throw new NullPointerException();}
        Node<Item> newFirst = new Node<Item>();
        newFirst.item = item;
        newFirst.previous = null;
        newFirst.next = first;

        if (isEmpty()) {
            last = newFirst;
        }
        else {
            first.previous = newFirst;
        }

        first = newFirst;
        N++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item==null) {throw new NullPointerException();}
        Node<Item> newLast = new Node<Item>();
        newLast.item = item;
        newLast.previous = last;
        newLast.next = null;

        if (isEmpty()) {
            first = newLast;
        }
        else {
            last.next = newLast;
        }

        last = newLast;
        N++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.previous = null;
        }
        return item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = last.item;
        last = last.previous;
        N--;
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return item;
    }

    private String AXCToString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        Node<Item> current;
        DequeIterator(Node<Item> first)     { current = first;                            }
        public boolean hasNext()     { return current != null;                     }
        public void remove()         { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }
    // unit testing
    public static void main(String[] args)
    {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("1");
        StdOut.println("addfirst to string: " + deque.AXCToString());
        deque.addFirst("2");
        StdOut.println("addfirst to string: " + deque.AXCToString());
        deque.addFirst("3");
        StdOut.println("addfirst to string: " + deque.AXCToString());
        deque.addFirst("4");
        StdOut.println("addfirst to string: " + deque.AXCToString());
        deque.addFirst("5");
        StdOut.println("addfirst to string: " + deque.AXCToString());
        deque.removeFirst();
        StdOut.println("removefirst to string: "+deque.AXCToString());
        deque.removeFirst();
        StdOut.println("removefirst to string: "+deque.AXCToString());
        deque.removeFirst();
        StdOut.println("removefirst to string: "+deque.AXCToString());
        deque.removeFirst();
        StdOut.println("removefirst to string: "+deque.AXCToString());
        deque.removeFirst();
        StdOut.println("removefirst to string: "+deque.AXCToString());
        deque.addLast("1");
        StdOut.println("addlast to string: "+deque.AXCToString());
        deque.addLast("2");
        StdOut.println("addlast to string: "+deque.AXCToString());
        deque.addLast("3");
        StdOut.println("addlast to string: "+deque.AXCToString());
        deque.addLast("4");
        StdOut.println("addlast to string: "+deque.AXCToString());
        deque.addLast("5");
        StdOut.println("addlast to string: "+deque.AXCToString());
        deque.removeLast();
        StdOut.println("removelast to string: "+ deque.AXCToString());
        deque.removeLast();
        StdOut.println("removelast to string: "+ deque.AXCToString());
        deque.removeLast();
        StdOut.println("removelast to string: "+ deque.AXCToString());
        deque.removeLast();
        StdOut.println("removelast to string: "+ deque.AXCToString());
        deque.removeLast();
        StdOut.println("removelast to string: "+ deque.AXCToString());
        //deque.addLast(null);
        //deque.addFirst(null);
        ///deque.removeLast();
        //deque.removeFirst();



    }
}
