/**
 * Created by alexchoi1 on 7/2/14.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array; // array of items in Deque
    private int N; // count of items in Deque

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[])new Object[2];
        N = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return (N==0);
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (N == array.length) {
            resize(array.length*2);
        }
        array[N++] = item;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(N);
        Item item = array[randomIndex];
        array[randomIndex] = array[N-1];
        array[N-1] = null;
        N--;
        if ( N > 0 && N == array.length/4 ) {
            resize(array.length/2);
        }
        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(N);
        Item item = array[randomIndex];
        return item;
    }

    private String AXCToString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedArrayIterator();
    }

    private class RandomizedArrayIterator implements Iterator<Item> {

        private int copiedN;
        private Item[] copiedArray;

        public RandomizedArrayIterator() {
            copiedArray = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                copiedArray[i] = array[i];
            }
            copiedN = N;
        }


        public boolean hasNext()     {
            return copiedN>0;
        }
        public void remove()         { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int randomIndex = StdRandom.uniform(copiedN);
            Item item = copiedArray[randomIndex];
            copiedArray[randomIndex] = copiedArray[copiedN-1];
            copiedArray[copiedN-1] = null;
            copiedN--;
            return item;
        }

    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        queue.enqueue(2);
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        queue.enqueue(3);
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        queue.enqueue(4);
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        queue.enqueue(5);
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        queue.enqueue(6);
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        queue.enqueue(7);
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        queue.enqueue(8);
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        queue.enqueue(9);
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        queue.enqueue(10);
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.AXCToString());
        StdOut.println(queue.AXCToString());


    }
}
