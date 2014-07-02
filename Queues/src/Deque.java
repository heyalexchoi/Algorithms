import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

    private int N;
    private Node<Item> first;
    private Node<Item> last;

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
        Node<Item> newFirst = new Node();
        newFirst.item = item;
        newFirst.previous = null;
        newFirst.next = first;
        first = newFirst;
        N++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        Node<Item> newLast = new Node();
        newLast.item = item;
        newLast.previous = last;
        newLast.next = null;
        last = newLast;
        N++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        Item item = first.item;
        first = first.next;
        first.previous = null;
        N--;
        return item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        Item item = last.item;
        last = last.previous;
        last.next = null;
        N--;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        Node<Item> current;
        DequeIterator(Node first)     { current = first;                            }
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
        StdOut.println("deque main");
    }
}
