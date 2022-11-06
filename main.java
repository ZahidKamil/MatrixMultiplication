package project1;

import java.util.Random;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// Set up variables for input
		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		String invalidInput = "Invalid choice, please try again!";

		String matrixSize = "Matrix Multiplication\n" + "Please Enter integer n to specify matrix (n*n): ";

		String menu = "Please enter one of the following options:\n" + "\ta) Classic Multiplication\n"
				+ "\tb) Divide and Conquer\n" + "\tc) Strassen's Algorithm\n\n" + "Enter option: ";

		int n;
		char userInput;
		final int TIMES = 5;
		long startT, endT, totalTimeC = 0;

		// Print menu and get number of equations
		System.out.print(matrixSize);
		n = kb.nextInt();

		// Number of equations input must be between 2 and 10 inclusive
		// If not valid then reprint menu
		while (n < 2) {
			System.out.println("\n" + invalidInput + "\n\n" + matrixSize);
			n = kb.nextInt();
		}
		System.out.println();

		// Print option menu and take in user input
		System.out.print(menu);
		userInput = kb.next().toLowerCase().charAt(0);

		// If user option is not valid, prompt user again
		while (userInput != 'a' && userInput != 'b' && userInput != 'c') {
			System.out.println("\n" + invalidInput + "\n\n" + menu);
			userInput = kb.next().toLowerCase().charAt(0);
		}

		// Option A: Brute Force
		if (userInput == 'a') {
			System.out.println("-----------------------------------------------------");
			System.out.println("Classic Multiplication: ");

			for (int j = 0; j < TIMES; j++) {
				System.out.println();
				System.out.println("Random Matrix A: ");
				int a[][] = generateMatrix(n);
				printMatrix(a, n);

				System.out.println("\nRandom Matrix B: ");
				int b[][] = generateMatrix(n);
				printMatrix(b, n);

				startT = System.currentTimeMillis();
				int res[][] = classicMultiply(n, a, b);
				endT = System.currentTimeMillis();
				long tempTime = endT - startT;
				totalTimeC += endT - startT;
				System.out.println("\nResulting Matrix: ");
				printMatrix(res, res.length);
				System.out.println("\nTest Case " + j + " time: " + tempTime + " ms");
				System.out.println("-----------------------------------------------------");
			}
			totalTimeC = totalTimeC / TIMES;
			System.out.println(
					"For n=" + n + ": Total Avg Time \n\tClassic Multiplication Avg. time: " + totalTimeC + " ms\n");

		}
		
		// Option B: Divide and Conquer
		else if (userInput == 'b') {
			System.out.println("-----------------------------------------------------");
			System.out.println("Divide & Conquer: ");

			for (int j = 0; j < TIMES; j++) {
				System.out.println();
				System.out.println("Random Matrix A: ");
				int a[][] = generateMatrix(n);
				printMatrix(a, n);

				System.out.println("\nRandom Matrix B: ");
				int b[][] = generateMatrix(n);
				printMatrix(b, n);

				startT = System.currentTimeMillis();
				int res[][] = DCmultiply(a, b, 0, 0, 0, 0, a.length);
				endT = System.currentTimeMillis();
				long tempTime = endT - startT;
				totalTimeC += endT - startT;
				System.out.println("\nResulting Matrix: ");
				printMatrix(res, res.length);
				System.out.println("\nTest Case " + j + " time: " + tempTime + " ms");
				System.out.println("-----------------------------------------------------");
			}
			totalTimeC = totalTimeC / TIMES;
			System.out.println(
					"For n=" + n + ": Total Avg Time \n\tDivide & Conquer Multiplication Avg. time: " + totalTimeC + " ms\n");
		}

		// Option C: Strassen
		else if (userInput == 'c') {
			System.out.println("-----------------------------------------------------");
			System.out.println("Strassen: ");

			for (int j = 0; j < TIMES; j++) {
				System.out.println();
				System.out.println("Random Matrix A: ");
				int a[][] = generateMatrix(n);
				printMatrix(a, n);

				System.out.println("\nRandom Matrix B: ");
				int b[][] = generateMatrix(n);
				printMatrix(b, n);

				startT = System.currentTimeMillis();
				int res[][] = strassen(n, a, b);
				endT = System.currentTimeMillis();
				long tempTime = endT - startT;
				totalTimeC += endT - startT;
				System.out.println("\nResulting Matrix: ");
				printMatrix(res, res.length);
				System.out.println("\nTest Case " + j + " time: " + tempTime + " ms");
				System.out.println("-----------------------------------------------------");
			}
			totalTimeC = totalTimeC / TIMES;
			System.out.println(
					"For n=" + n + "\nStrassen Multiplication Avg. time: " + totalTimeC + " ms\n");
		}
	}

	// Method to generate Matrix
	private static int[][] generateMatrix(int n) {
		Random randInt = new Random();
		int generateMatrix[][] = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				generateMatrix[i][j] = randInt.nextInt(50);
			}
		}
		return generateMatrix;
	}

	// Method to print Matrix
	private static void printMatrix(int print[][], int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(print[i][j] + "\t");
			}
			System.out.println();
		}
	}

	// Classic Algorithm
	private static int[][] classicMultiply(int n, int a[][], int b[][]) {
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

	// Divide and Conquer Algorithm
	private static int[][] DCmultiply(int a[][], int b[][], int rA, int cA, int rB, int cB, int n) {
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

	// Strassen Algorithm
	private static int[][] strassen(int n, int A[][], int B[][]) {
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


	// Add method for divide and conquer
	public static void add(int a[][], int b[][], int c[][], int rC, int cC) {
		int size = b.length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				a[i + rC][j + cC] = b[i][j] + c[i][j];
			}
		}
	}
	
	// Split matrix
	public static void split(int m[][], int c[][] , int r, int s) {
		for (int iC = 0, iM = r; iC < c.length; iC++, iM++)
			for (int jC = 0, jM = s; jC < c.length; jC++, jM++)
				c[iC][jC] = m[iM][jM];
	}

	// Add method for Strassen
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

	// Subtract matrix
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

	// Join two matrix
	public static void join(int c [][] , int m[][], int r, int s) {
		for (int iC = 0, iM = r; iC < c.length; iC++, iM++)
			for (int j1 = 0, j2 = s; j1 < c.length; j1++, j2++)
				m[iM][j2] = c[iC][j1];
	}
}
