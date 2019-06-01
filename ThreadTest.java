/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package percolation;

/**
 *
 * @author Cloudwolfe
 */
public class ThreadTest extends Thread
{
    private PercolationStats ps;
    private double mean;
    private double stddev;


    private int N;
    private int T;
    
    public void run()
    {
        N = 100;
        T = 5000;
        //T = 100;
        
        ps = new PercolationStats(N, T);
        
        mean = ps.mean();
        stddev = ps.stddev();
    }
    
    public double getMean()
    {
        return mean;
    }
    
    public double getStddev()
    {
        return stddev;
    }
    
    public int getT() 
    {
        return T;
    }
    
    public int getN() 
    {
        return N;
    }
    
    public static void main (String[] args)
    {
        ThreadTest t1 = new ThreadTest();
        ThreadTest t2 = new ThreadTest();
        //ThreadTest t3 = new ThreadTest();
        
        t1.start();
        t2.start();
        //t3.start();
        
        boolean alive = true;
        double mean = 0;
        double stddev = 0;
        
        while(alive)
        {
            if(!t1.isAlive() && !t2.isAlive()) //&& !t3.isAlive())
            {
                mean = (t1.getMean() + t2.getMean())/2; //+ t3.getMean())/3;   
                stddev = (t1.getStddev() + t2.getStddev())/2;// + t3.getStddev())/3; 
                alive = false;
            }
        }
        
        System.out.println( "Thread 1 Mean: " + t1.getMean());
        System.out.println( "Thread 2 Mean: " + t2.getMean());
        //System.out.println( "Thread 3 Mean: " + t3.getMean());
        
   
        System.out.println( "\nThread 1 Dev: " + t1.getStddev());
        System.out.println( "Thread 2 Dev: " + t2.getStddev());
        //System.out.println( "Thread 3 Dev: " + t3.getStddev());
        
        
        System.out.println("\nGrid size: " + t1.getN() + "*" + t1.getN());
        System.out.println("Total combined passes: " + (t1.getT()+t2.getT()));
        System.out.println( "\nCombined Mean: " + mean);
        System.out.println( "Combined Std Dev: " + stddev);
      
    }
}
