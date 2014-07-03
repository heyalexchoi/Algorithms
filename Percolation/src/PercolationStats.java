public class PercolationStats {
   /// holds each experiment's percolation threshold result
   private double[] thresholdResults;
   private int T;
   // perform T independent computational experiments on an N-by-N grid
   public PercolationStats(int N, int T) 
   {
       ///The constructor should throw a java.lang.IllegalArgumentException if either N <= 0 or T >= 0.
       if (N<1 || T<1)
       {
           throw new IllegalArgumentException("both arguments N and T must be greater than 1");
       }
       
       this.T = T;
       thresholdResults = new double[T];
       for (int t = 0; t < T; t++)
       {
            Percolation percolation = new Percolation(N);
            int openSites = 0;
            while (!percolation.percolates())
            {
                int i = StdRandom.uniform(1,N+1);
                int j = StdRandom.uniform(1,N+1);
           
                if (!percolation.isOpen(i,j))
                {
                    percolation.open(i,j);
                    openSites += 1;
                }
            }
            double threshold = (double)openSites/(double)(N*N);
           thresholdResults[t] = threshold;
       }
   }
   // sample mean of percolation threshold
   public double mean() 
   {
       return StdStats.mean(thresholdResults);
   }
   // sample standard deviation of percolation threshold
   public double stddev()  
   {
       return StdStats.stddev(thresholdResults);
   }
   // returns lower bound of the 95% confidence interval
   public double confidenceLo()  
   {
       return mean() - (1.96*stddev()/Math.sqrt(T));
   }
   // returns upper bound of the 95% confidence interval
   public double confidenceHi()             
       {
       return mean() + (1.96*stddev()/Math.sqrt(T));
   }
   /*
    * Also, include a main() method that takes two command-line arguments N and T,
    * performs T independent computational experiments (discussed above)
    * on an N-by-N grid, and prints out the mean, standard deviation, 
    * and the 95% confidence interval for the percolation threshold.
    */
   
   public static void main(String[] args)
   {
       int N = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);
       PercolationStats stats = new PercolationStats(N,T);
       StdOut.println("mean = "+ stats.mean());
       StdOut.println("standard deviation = "+ stats.stddev());
       StdOut.println("95% confidence interval = "+ stats.confidenceLo() + " , " + stats.confidenceHi());
   }
       
       
     
}