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
        array[randomIndex] = array[0];
        array[0] = null;
        N--;
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
            return N>0;
        }
        public void remove()         { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int randomIndex = StdRandom.uniform(N);
            Item item = copiedArray[randomIndex];
            copiedArray[randomIndex] = copiedArray[0];
            copiedArray[0] = null;
            copiedN--;
            return item;
        }

    }

    // unit testing
    public static void main(String[] args) {

    }
}
