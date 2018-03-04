import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation 
{
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf1;
    private int opensite;
    
    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        if(n <= 0) throw new IllegalArgumentException("n should be larger than 0");
        grid = new boolean[n][n]; 
        uf = new WeightedQuickUnionUF(n*n+1);
        uf1 = new WeightedQuickUnionUF(n*n+2);
    }
    
    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        
        assertValid(row, col);
        if (isOpen(row, col)) return;
        int n = grid.length ;
        grid[row-1][col-1] = true;
        int index = (row-1)*n + col;
        if (row == 1) { uf.union(0, index); uf1.union(0, index);}
        if (row == n) { uf1.union(n*n+1, index); }
        if (row - 1 >= 1) { if (isOpen(row-1, col)) { uf.union(index - n, index); uf1.union(index - n, index); }}                       
        if (row + 1 <= n) { if (isOpen(row+1, col)) { uf.union(index + n, index); uf1.union(index + n, index); }}
        if (col - 1 >= 1) { if (isOpen(row, col-1)) { uf.union(index - 1, index); uf1.union(index - 1, index); }}
        if (col + 1 <= n) { if (isOpen(row, col+1)) { uf.union(index + 1, index); uf1.union(index + 1, index); }}
        opensite++;     
    }
    
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    { 
        assertValid(row, col);
        return grid[row-1][col-1] == true; 
    }
    
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        assertValid(row, col);
        int n = grid.length;
        return uf.connected(0, (row-1)*n + col);
    }
    public     int numberOfOpenSites()       // number of open sites
    { return opensite; }
    
    public boolean percolates()              // does the system percolate?
    { 
        int n = grid.length;
        return uf1.connected(0, n*n+1); 
    }
        
    private void assertValid(int row, int col)
    {
        int n = grid.length;
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("index out of range");
    }
}