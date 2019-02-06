/***********************************************************************
 * Data Structures Assignment 1
 * 
 * Name: Nathan Dunne
 * ID: K00211819
 * Course: Games Design & Development 
 *
 *****************************************************************************/

public class PercolationStats
{
    private Percolation percolation;
    private double openedSites;
    private final double statStore[];
    
    public PercolationStats(int N, int T)
    {
        validateInput(N, T);

        statStore = new double[T];
        
        for(int k=0; k<T; k++)
        {
            percolation = new Percolation(N);
            openedSites = 0;
            
            while(!percolation.percolates())
            {
                
                int i = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);
               
                if(!percolation.isOpen(i, j))
                {
                    percolation.open(i, j);
                    openedSites++;
                }
               
            }
                       
            statStore[k] = openedSites / (N*N);
            
        }     
    }

    public double mean()
    {    
        return StdStats.mean(statStore);
    }

    public double stddev()
    {
        return StdStats.stddev(statStore);
    }

    public double confidenceLo()
    {
        return StdStats.mean(statStore) - ((1.96*StdStats.stddev(statStore)) / Math.sqrt(openedSites));
    }

    public double confidenceHi()
    {
        return StdStats.mean(statStore) + ((1.96*StdStats.stddev(statStore)) / Math.sqrt(openedSites));
    }
   
    private void validateInput(int N, int T)
    {
        if(N <= 0 || T <= 0)
        {
            throw new IllegalArgumentException("Invalid input: " + N + " " + T
                    + " \n Both inputs must be greater than 0.");
        }   
    }

    public static void main( String [] args)
    {  
        if (args.length != 2)
        {
            System.out.println( "Usage: java PercolationStats <N> <T> \n");
            System.out.println( "where N is the size of the grid to use");
            System.out.println( "and T is the number of experiments to run");
            System.exit(1);
        }
        
        int N = Integer.parseInt( args[0]);
        int T = Integer.parseInt( args[1]);
 
        PercolationStats ps = new PercolationStats(N, T);

        System.out.println( "Mean: " + ps.mean());
        System.out.println( "Std Dev: " + ps.stddev());
        System.out.println(	"95% confidence interval: " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}