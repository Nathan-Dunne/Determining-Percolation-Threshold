/******************************************************************************
* Data Structures Assignment 1
*
* Name: Nathan Dunne
* ID: K00211819
* Course: Games Design & Development
*
******************************************************************************/

public class Percolation
{
    private final WeightedQuickUnionUF unionFind;
    private final boolean[][] grid;
    
    private final int conventionOffSet = 1; 
    private final int virtualSiteAmt = 2;
    private final int virtualTop = 0;
    private final int virtualBottom;
    private final int gridLength;
    
    public Percolation(int N)
    {  
        if(!isProgramInputValid(N))
        {
            throwInputException(N);
        }
        
        grid = new boolean[N][N];
        gridLength = N;

        unionFind = new WeightedQuickUnionUF((N*N)+virtualSiteAmt);
        virtualBottom = (N*N)+1;

        fillGridAsClosed(N);
    }
    
    public void open(int i, int j)
    {   
        if(!checkIndex(i, j))
        {
            throwConventionException(i, j);
        }
        
        grid[i-conventionOffSet][j-conventionOffSet] = true;

        connectAnyAdjoiningOpenSites(i, j);        
    }

    public boolean isOpen(int i, int j)
    {     
        if(!checkIndex(i, j))
        {
            throwConventionException(i, j);
        }
         
        return grid[i-conventionOffSet][j-conventionOffSet];    
    }

    public boolean isFull(int i, int j) 
    {
        if(!checkIndex(i, j))
        {
            throwConventionException(i , j);
        }
            
        int siteToCheck = getUnionIndex(i, j);
        
        return unionFind.connected(virtualTop, siteToCheck);   
    }
    
    public boolean percolates()
    {   
        return unionFind.connected(virtualTop, virtualBottom);
    }
    
    // Checks if an index is within a certain range.
    private boolean checkIndex(int i, int j)
    {     
        return (i>=1 && i<=gridLength) && (j>=1 && j<=gridLength);
    }
    
    /* 
      Returns the converted union index given a 2D array position, offset by
      percolation convention and virtual sites.
    */
    private int getUnionIndex(int i, int j)
    {
        if(!checkIndex(i, j))
        {
            throwConventionException(i, j);
        }
        
        int virtualSitesOffSet = 1;

        return gridLength*(i-conventionOffSet)+(j-conventionOffSet)+virtualSitesOffSet; 
    }
    
    /*
        Connects a given cell to any virtual or non-virtual sites in proximity to that cell.
        Integrity is enforced, if misused, with a check to isOpen.
    */ 
    private void connectAnyAdjoiningOpenSites(int i, int j)
    { 
        if(!checkIndex(i, j))  
        {
            throwConventionException(i, j);
        }
        else if(!isOpen(i, j))
        {
            throwIsOpenException();
        }
 
        int openedSite = getUnionIndex(i, j);
        
        
        int inTopRow = 1;
        int inBottomRow = gridLength;
        
        // Connecting to virtual sites. Unsure of whether or not to extract this into its own method.
        if(i == inTopRow)
        {
            unionFind.union(openedSite, virtualTop);
        }
        else if(i == inBottomRow)
        {
            unionFind.union(openedSite, virtualBottom);
        }
       
        int upIndex = i-1;
        int downIndex = i+1;
        int leftIndex = j-1;
        int rightIndex = j+1;
        
        // I've reused checkIndex() to test for bounds here.
        if(checkIndex(upIndex, j) && isOpen(upIndex, j))
        {    
            int upSite = getUnionIndex(upIndex, j); 

            unionFind.union(openedSite, upSite);
        }

        if(checkIndex(downIndex, j) && isOpen(downIndex, j))
        {  
           int downSite = getUnionIndex(downIndex, j);

           unionFind.union(openedSite, downSite);
        }

        if(checkIndex(i, leftIndex) && isOpen(i, leftIndex))
        {        
            int leftSite = getUnionIndex(i, leftIndex); 

            unionFind.union(openedSite, leftSite);
        }
        
        if(checkIndex(i, rightIndex) && isOpen(i, rightIndex))
        {  
            int rightSite = getUnionIndex(i, rightIndex); 

            unionFind.union(openedSite, rightSite);
        }        
    }
    
    // Fills the boolean grid as false, thus closed.
    private void fillGridAsClosed(int N)
    {
        for(int i=0; i<N; i++)
        {
            for(int j=0; j<N;j++)
            {
                grid[i][j] = false;
            }
        }
    }
    
    // Tests against invalid program input.
    private boolean isProgramInputValid(int N)
    {     
        return N >= 1;
    }
    
    // Throws exception regarding invalid range for input parameters.
    private void throwConventionException(int i, int j)
    {
        throw new IndexOutOfBoundsException("Invalid arguments: " + i + " " + j + ". Both arguments must be integers between 1 and N inclusive.");
    }
    
    // Throws exception regarding invalid program input.
    private void throwInputException(int N)
    {
        throw new IllegalArgumentException("Invalid input: " + N + "."+" N must be greater than 0.");
    }
    
    // Throws exception regarding invalid operation on a closed site.
    private void throwIsOpenException()
    {
        throw new IllegalArgumentException("Site must be open for this operation.");
    }   
}
