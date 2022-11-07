package com.company;

import java.util.Random;

public class Matrix {

    /**
     * Generate a matrix of size n with random integers within n.
     * @param n integer for the size of the matrix to be generated
     * @return matrix of size of n
     */
    public int[][]GenerateMatrix(int n)
    {
        Random rand = new Random();
        int[][]Matrix = new int[n][n];
        for(int i=0; i<n;i++)
        {
            for (int j=0; j<n;j++)
            {
                Matrix[i][j] = rand.nextInt(n);
            }
        }
        return Matrix;
    }

    /**
     * Gets a partition of a larger matrix
     * @param InputMatrix 2D Input matrix
     * @param m index of row
     * @param n index of column
     * @return partitioned matrix based on the index of row and column
     */
    public int[][]GetPartitionMatrix(int[][]InputMatrix, int m, int n)
    {
        int[][]Matrix = new int[m][n];
        for(int i=0;i<m;i++)
        {
            for (int j=0;j<n;j++)
            {
                Matrix[i][j] = InputMatrix[i][j];
            }
        }
        return Matrix;
    }

    /**
     * Prints the matrix on the terminal
     * @param a input matrix a
     */
    public static void printMatrix(int [][]a)
    {
        System.out.println("Printing Matrix:");
        for (int i=0; i<a.length;i++)
        {
            for (int j=0; j<a[0].length; j++)
            {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Brute force algorithm
     * @param n size of matrix
     * @param a input matrix a
     * @param b input matrix b
     * @return matrix multiplication of a and b
     */
    public int[][] classicMultiply(int n, int a[][], int b[][]) {
        int c[][] = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    c[i][j] = c[i][j] + a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    /**
     * Divide and Conquer Algorithm
     * @param a input matrix a
     * @param b input matrix b
     * @param rA index of row of matrix a
     * @param cA index of col of matrix a
     * @param rB index of row of matrix b
     * @param cB index of col of matrix b
     * @param n size of matrix
     * @return  Multiplication of A and B
     */
    public int[][] DCmultiply(int a[][], int b[][], int rA, int cA, int rB, int cB, int n) {
        int c[][] = new int[n][n];
        if (n == 1) {
            c[0][0] = a[rA][cA] * b[rB][cB];
        } else {
            //Compute Mid
            int mid = n / 2;

            //Recursive Calls
            add(c, DCmultiply(a, b, rA, cA, rB, cB, mid), DCmultiply(a, b, rA, cA + mid, rB + mid, cB, mid), 0, 0);
            add(c, DCmultiply(a, b, rA, cA, rB, cB + mid, mid), DCmultiply(a, b, rA, cA + mid, rB + mid, cB + mid, mid), 0, mid);
            add(c, DCmultiply(a, b, rA + mid, cA, rB, cB, mid), DCmultiply(a, b, rA + mid, cA + mid, rB + mid, cB, mid), mid, 0);
            add(c, DCmultiply(a, b, rA + mid, cA, rB, cB + mid, mid), DCmultiply(a, b, rA + mid, cA + mid, rB + mid, cB + mid, mid), mid, mid);
        }
        return c;
    }

    /**
     * Adds two matrices
     * @param a output matrix a
     * @param b input matrix b
     * @param c input matrix c
     * @param rC index of row to start
     * @param cC index of column to start
     */
    public static void add(int a[][], int b[][], int c[][], int rC, int cC) {
        int size = b.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                a[i + rC][j + cC] = b[i][j] + c[i][j];
            }
        }
    }

    /**
     * Adds two matrices for strassen algorithm
     * @param a input matrix a
     * @param b input matrix b
     * @return output matrix = a+b
     */
    public static int[][] addS(int[][] a, int[][] b) {
        int n = a.length;
        int[][] c = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        return c;
    }

    /**
     * Sbudivides a matrix
     * @param m matrix
     * @param c subdivided matrix
     * @param r index of the row
     * @param s index of the column
     */
    public static void split(int m[][], int c[][] , int r, int s) {
        for (int iC = 0, iM = r; iC < c.length; iC++, iM++)
            for (int jC = 0, jM = s; jC < c.length; jC++, jM++)
                c[iC][jC] = m[iM][jM];
    }


    /**
     * Subtract matrix
     * @param a Input matrix a
     * @param b Input matrix b
     * @return a-b
     */
    public static int[][] subtract(int[][] a, int[][] b) {
        int n = a.length;
        int[][] c = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = a[i][j] - b[i][j];
            }
        }
        return c;
    }



    /**
     * Strassen Algorithm
     * @param n size of matrix
     * @param A Input matrix A
     * @param B Input matrix B
     * @return Multiplication of A and B
     */
    public int[][] strassen(int n, int A[][], int B[][]) {
        n = A.length;
        int[][] c = new int[n][n];

        if (n == 1)
            c[0][0] = A[0][0] * B[0][0];
        else {
            //double mid = n/2;
            // Partitioning A into four sub-matrices
            int[][] A11 = new int[n/2][n/2];
            int[][] A12 = new int[n/2][n/2];
            int[][] A21 = new int[n/2][n/2];
            int[][] A22 = new int[n/2][n/2];
            // Partitioning B into four sub-matrices
            int[][] B11 = new int[n/2][n/2];
            int[][] B12 = new int[n/2][n/2];
            int[][] B21 = new int[n/2][n/2];
            int[][] B22 = new int[n/2][n/2];

            // Split matrix A into 4
            split(A, A11, 0, 0);
            split(A, A12, 0, n/2);
            split(A, A21, n/2, 0);
            split(A, A22, n/2, n/2);
            // Split matrix B into 4 halves
            split(B, B11, 0, 0);
            split(B, B12, 0, n/2);
            split(B, B21, n/2, 0);
            split(B, B22, n/2, n/2);

            // Recursive Calls
            int[][] s1 = strassen(n, addS(A11, A22), addS(B11, B22));
            int[][] s2 = strassen(n, addS(A21, A22), B11);
            int[][] s3 = strassen(n, A11, subtract(B12, B22));
            int[][] s4 = strassen(n, A22, subtract(B21, B11));
            int[][] s5 = strassen(n, addS(A11, A12), B22);
            int[][] s6 = strassen(n, subtract(A21, A11), addS(B11, B12));
            int[][] s7 = strassen(n, subtract(A12, A22), addS(B21, B22));

            int[][] C11 = addS(subtract(addS(s1, s4), s5), s7);
            int[][] C12 = addS(s3, s5);
            int[][] C21 = addS(s2, s4);
            int[][] C22 = addS(subtract(addS(s1, s3), s2), s6);

            // Step 3: Join 4 halves into one result matrix
            join(C11, c, 0, 0);
            join(C12, c, 0, n/2);
            join(C21, c, n/2, 0);
            join(C22, c, n/2, n/2);
        }

        // Step 4: Return result
        return c;
    }

    /**
     * Adds the submatrices to the large output matrix
     * @param c Input matrix
     * @param m Output matrix
     * @param r index of the row
     * @param s index of the column
     */
    public static void join(int c [][] , int m[][], int r, int s) {
        for (int iC = 0, iM = r; iC < c.length; iC++, iM++)
            for (int j1 = 0, j2 = s; j1 < c.length; j1++, j2++)
                m[iM][j2] = c[iC][j1];
    }

}
