
/**
 * Created by alexchoi1 on 7/2/14.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            queue.enqueue(string);
        }

        for (int i = 0; i < k; i ++) {
            StdOut.println(queue.dequeue());
        }

    }
}
