package com.company;
///import edu.princeton.cs.algs4.QuickFindUF;
//import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.introcs.StdOut;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
    //question1();
    question2();


    }
    /*
    Q3
    (seed = 755068)
Which of the following id[] array(s) could be the result of running the weighted quick union
algorithm on a set of 10 items? Check all that apply.
     */
    public static void question3() {
        WeightedQuickUnionUF quickUnion = new WeightedQuickUnionUF(10);


    }








    /*
    Q2
    (seed = 555534)
Give the id[] array that results from the following sequence of 9 union
operations on a set of 10 items using the weighted quick-union algorithm from lecture.

    6-2 5-7 4-0 0-8 9-6 7-6 3-1 8-1 1-2

Recall: when joining two trees of equal size, our weighted quick union convention is to
make the root of the second tree point to the root of the first tree. Also, our weighted
quick union algorithm uses union by size (number of nodes), not union by height.
    */
     public static void question2(){
         WeightedQuickUnionUF quickUnion = new WeightedQuickUnionUF(10);
         quickUnion.union(6,2);
         quickUnion.union(5,7);
         quickUnion.union(4,0);
         quickUnion.union(0,8);
         quickUnion.union(9,6);
         quickUnion.union(7,6);
         quickUnion.union(3,1);
         quickUnion.union(8,1);
         quickUnion.union(1,2);

         int[] idArray = quickUnion.id();
         String arrayString = Arrays.toString(idArray);

         StdOut.print("question 2 answer:"+arrayString);

     }

    /*
    Q1
            (seed = 999983)
Give the id[] array that results from the following sequence of 6 union
operations on a set of 10 items using the quick-find algorithm.

    6-3 8-7 1-7 5-7 7-9 2-0

Recall: our quick-find convention for the union operation p-q is to change id[p]
(and perhaps some other entries) but not id[q].
      */
    public static void question1() {
        QuickFindUF quickFind = new QuickFindUF(10);
        quickFind.union(6,3);
        quickFind.union(8,7);
        quickFind.union(1,7);
        quickFind.union(5,7);
        quickFind.union(7,9);
        quickFind.union(2,0);


        int[] idArray = quickFind.id();
        String arrayString = Arrays.toString(idArray);

        StdOut.print("question 1 answer:"+arrayString);

    }
}







