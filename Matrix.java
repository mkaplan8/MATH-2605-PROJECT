import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * Matrix class to represent 2D arrays as matrices
 * Uses code from http://math.nist.gov/javanumerics/jama/ to implement
 * basic matrix operations.
 * @author Chingyeu Andy Leekung, Joeseph Lesniak, Jacob Goodpasture
 * @version 1.0
 */
public class Matrix implements Cloneable, java.io.Serializable  {

	/* -----------------------------------------------------------------------
	 * This code is from http://math.nist.gov/javanumerics/jama/
	 * @author The MathWorks, Inc. and the National
	 * 		   Institute of Standards and Technology.
	 * @version 5 August 1998
	 * Only code for the functions allowed in the project is used
	 * ----------------------------------------------------------------------*/

	/* ------------------------
	   Class variables
	 * ------------------------ */

	/** Array for internal storage of elements.
	 * @serial internal array storage.
	 */
	private double[][] A;

	/** Row and column dimensions.
	 * @serial row dimension.
	 * @serial column dimension.
	 */
	private int m, n;

	/* ------------------------
	   Constructors
	 * ------------------------ */

	/** Construct an m-by-n matrix of zeros.
	 * @param m    Number of rows.
	 * @param n    Number of colums.
	 */

	public Matrix (int m, int n) {
		this.m = m;
		this.n = n;
		A = new double[m][n];
	}

	/** Construct an m-by-n constant matrix.
	 * @param m    Number of rows.
	 * @param n    Number of colums.
	 * @param s    Fill the matrix with this scalar value.
	 */

	public Matrix (int m, int n, double s) {
		this.m = m;
		this.n = n;
		A = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = s;
			}
		}
	}

	/** Construct a matrix from a 2-D array.
	 * @param A    Two-dimensional array of doubles.
	 * @exception  IllegalArgumentException All rows must have the same length
	 * @see        #constructWithCopy
	 */

	public Matrix (double[][] A) {
		m = A.length;
		n = A[0].length;
		for (int i = 0; i < m; i++) {
			if (A[i].length != n) {
				throw new IllegalArgumentException("All rows must have the same length.");
			}
		}
		this.A = A;
	}

	/** Construct a matrix quickly without checking arguments.
	 * @param A    Two-dimensional array of doubles.
	 * @param m    Number of rows.
	 * @param n    Number of colums.
	 */

	public Matrix (double[][] A, int m, int n) {
		this.A = A;
		this.m = m;
		this.n = n;
	}

	/** Construct a matrix from a one-dimensional packed array
	 * @param vals One-dimensional array of doubles, packed by columns (ala Fortran).
	 * @param m    Number of rows.
	 * @exception  IllegalArgumentException Array length must be a multiple of m.
	 */

	public Matrix (double vals[], int m) {
		this.m = m;
		n = (m != 0 ? vals.length/m : 0);
		if (m*n != vals.length) {
			throw new IllegalArgumentException("Array length must be a multiple of m.");
		}
		A = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = vals[i+j*m];
			}
		}
	}
	/* ------------------------
	   Public Methods
	 * ------------------------ */

	/** Construct a matrix from a copy of a 2-D array.
	 * @param A    Two-dimensional array of doubles.
	 * @exception  IllegalArgumentException All rows must have the same length
	 */

	public static Matrix constructWithCopy(double[][] A) {
		int m = A.length;
		int n = A[0].length;
		Matrix X = new Matrix(m,n);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			if (A[i].length != n) {
				throw new IllegalArgumentException
				("All rows must have the same length.");
			}
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j];
			}
		}
		return X;
	}

	/** Make a deep copy of a matrix
	 */

	public Matrix copy () {
		Matrix X = new Matrix(m,n);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j];
			}
		}
		return X;
	}

	/** Clone the Matrix object.
	 */

	public Object clone () {
		return this.copy();
	}

	/** Access the internal two-dimensional array.
	 * @return     Pointer to the two-dimensional array of matrix elements.
	 */

	public double[][] getArray () {
		return A;
	}

	/** Copy the internal two-dimensional array.
	 * @return     Two-dimensional array copy of matrix elements.
	 */

	public double[][] getArrayCopy () {
		double[][] C = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j];
			}
		}
		return C;
	}

	/** Make a one-dimensional column packed copy of the internal array.
	 * @return     Matrix elements packed in a one-dimensional array by columns.
	 */

	public double[] getColumnPackedCopy () {
		double[] vals = new double[m*n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				vals[i+j*m] = A[i][j];
			}
		}
		return vals;
	}

	/** Make a one-dimensional row packed copy of the internal array.
	 * @return     Matrix elements packed in a one-dimensional array by rows.
	 */

	public double[] getRowPackedCopy () {
		double[] vals = new double[m*n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				vals[i*n+j] = A[i][j];
			}
		}
		return vals;
	}

	/** Get row dimension.
	 * @return     m, the number of rows.
	 */

	public int getRowDimension () {
		return m;
	}

	/** Get column dimension.
	 * @return     n, the number of columns.
	 */

	public int getColumnDimension () {
		return n;
	}

	/** Get a single element.
	 * @param i    Row index.
	 * @param j    Column index.
	 * @return     A(i,j)
	 * @exception  ArrayIndexOutOfBoundsException
	 */

	public double get (int i, int j) {
		return A[i][j];
	}

	/** Get a submatrix.
	 * @param i0   Initial row index
	 * @param i1   Final row index
	 * @param j0   Initial column index
	 * @param j1   Final column index
	 * @return     A(i0:i1,j0:j1)
	 * @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */

	public Matrix getMatrix (int i0, int i1, int j0, int j1) {
		Matrix X = new Matrix(i1-i0+1,j1-j0+1);
		double[][] B = X.getArray();
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = j0; j <= j1; j++) {
					B[i-i0][j-j0] = A[i][j];
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}

	/** Get a submatrix.
	 * @param r    Array of row indices.
	 * @param c    Array of column indices.
	 * @return     A(r(:),c(:))
	 * @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */

	public Matrix getMatrix (int[] r, int[] c) {
		Matrix X = new Matrix(r.length,c.length);
		double[][] B = X.getArray();
		try {
			for (int i = 0; i < r.length; i++) {
				for (int j = 0; j < c.length; j++) {
					B[i][j] = A[r[i]][c[j]];
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}

	/** Get a submatrix.
	 * @param i0   Initial row index
	 * @param i1   Final row index
	 * @param c    Array of column indices.
	 * @return     A(i0:i1,c(:))
	 * @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */

	public Matrix getMatrix (int i0, int i1, int[] c) {
		Matrix X = new Matrix(i1-i0+1,c.length);
		double[][] B = X.getArray();
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = 0; j < c.length; j++) {
					B[i-i0][j] = A[i][c[j]];
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}

	/** Get a submatrix.
	 * @param r    Array of row indices.
	 * @param j0   Initial column index
	 * @param j1   Final column index
	 * @return     A(r(:),j0:j1)
	 * @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */

	public Matrix getMatrix (int[] r, int j0, int j1) {
		Matrix X = new Matrix(r.length,j1-j0+1);
		double[][] B = X.getArray();
		try {
			for (int i = 0; i < r.length; i++) {
				for (int j = j0; j <= j1; j++) {
					B[i][j-j0] = A[r[i]][j];
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}

	/** Set a single element.
	 * @param i    Row index.
	 * @param j    Column index.
	 * @param s    A(i,j).
	 * @exception  ArrayIndexOutOfBoundsException
	 */

	public void set (int i, int j, double s) {
		A[i][j] = s;
	}

	/** Set a submatrix.
	 * @param i0   Initial row index
	 * @param i1   Final row index
	 * @param j0   Initial column index
	 * @param j1   Final column index
	 * @param X    A(i0:i1,j0:j1)
	 * @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */

	public void setMatrix (int i0, int i1, int j0, int j1, Matrix X) {
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = j0; j <= j1; j++) {
					A[i][j] = X.get(i-i0,j-j0);
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}

	/** Set a submatrix.
	 * @param r    Array of row indices.
	 * @param c    Array of column indices.
	 * @param X    A(r(:),c(:))
	 * @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */

	public void setMatrix (int[] r, int[] c, Matrix X) {
		try {
			for (int i = 0; i < r.length; i++) {
				for (int j = 0; j < c.length; j++) {
					A[r[i]][c[j]] = X.get(i,j);
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}

	/** Set a submatrix.
	 * @param r    Array of row indices.
	 * @param j0   Initial column index
	 * @param j1   Final column index
	 * @param X    A(r(:),j0:j1)
	 * @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */

	public void setMatrix (int[] r, int j0, int j1, Matrix X) {
		try {
			for (int i = 0; i < r.length; i++) {
				for (int j = j0; j <= j1; j++) {
					A[r[i]][j] = X.get(i,j-j0);
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}

	/** Set a submatrix.
	 * @param i0   Initial row index
	 * @param i1   Final row index
	 * @param c    Array of column indices.
	 * @param X    A(i0:i1,c(:))
	 * @exception  ArrayIndexOutOfBoundsException Submatrix indices
	 */

	public void setMatrix (int i0, int i1, int[] c, Matrix X) {
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = 0; j < c.length; j++) {
					A[i][c[j]] = X.get(i-i0,j);
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
	}

	/** Matrix transpose.
	 * @return    A'
	 */

	public Matrix transpose () {
		Matrix X = new Matrix(n,m);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[j][i] = A[i][j];
			}
		}
		return X;
	}
	/**  Unary minus
	 * @return    -A
	 */

	public Matrix uminus () {
		Matrix X = new Matrix(m,n);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = -A[i][j];
			}
		}
		return X;
	}

	/** C = A + B
	 * @param B    another matrix
	 * @return     A + B
	 */

	public Matrix plus (Matrix B) {
		checkMatrixDimensions(B);
		Matrix X = new Matrix(m,n);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] + B.A[i][j];
			}
		}
		return X;
	}

	/** A = A + B
	 * @param B    another matrix
	 * @return     A + B
	 */

	public Matrix plusEquals (Matrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] + B.A[i][j];
			}
		}
		return this;
	}

	/** C = A - B
	 * @param B    another matrix
	 * @return     A - B
	 */

	public Matrix minus (Matrix B) {
		checkMatrixDimensions(B);
		Matrix X = new Matrix(m,n);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] - B.A[i][j];
			}
		}
		return X;
	}

	/** A = A - B
	 * @param B    another matrix
	 * @return     A - B
	 */

	public Matrix minusEquals (Matrix B) {
		checkMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = A[i][j] - B.A[i][j];
			}
		}
		return this;
	}

	/** Multiply a matrix by a scalar, C = s*A
	 * @param s    scalar
	 * @return     s*A
	 */

	public Matrix times (double s) {
		Matrix X = new Matrix(m,n);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = s*A[i][j];
			}
		}
		return X;
	}

	/** Multiply a matrix by a scalar in place, A = s*A
	 * @param s    scalar
	 * @return     replace A by s*A
	 */

	public Matrix timesEquals (double s) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = s*A[i][j];
			}
		}
		return this;
	}

	/** Generate identity matrix
	 * @param m    Number of rows.
	 * @param n    Number of columns.
	 * @return     An m-by-n matrix with ones on the diagonal and zeros elsewhere.
	  */
	   public static Matrix identity (int m, int n) {
	      Matrix A = new Matrix(m,n);
	      double[][] X = A.getArray();
	      for (int i = 0; i < m; i++) {
	         for (int j = 0; j < n; j++) {
	            X[i][j] = (i == j ? 1.0 : 0.0);
	         }
	      }
	      return A;
	   }

	/** Print the matrix to stdout.   Line the elements up in columns
	 * with a Fortran-like 'Fw.d' style format.
	 * @param w    Column width.
	 * @param d    Number of digits after the decimal.
	 */

	public void print (int w, int d) {
		print(new PrintWriter(System.out,true),w,d); }

	/** Print the matrix to the output stream.   Line the elements up in
	 * columns with a Fortran-like 'Fw.d' style format.
	 * @param output Output stream.
	 * @param w      Column width.
	 * @param d      Number of digits after the decimal.
	 */

	public void print (PrintWriter output, int w, int d) {
		DecimalFormat format = new DecimalFormat();
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		format.setMinimumIntegerDigits(1);
		format.setMaximumFractionDigits(d);
		format.setMinimumFractionDigits(d);
		format.setGroupingUsed(false);
		print(output,format,w+2);
	}

	/** Print the matrix to stdout.  Line the elements up in columns.
	 * Use the format object, and right justify within columns of width
	 * characters.
	 * Note that is the matrix is to be read back in, you probably will want
	 * to use a NumberFormat that is set to US Locale.
   @param format A  Formatting object for individual elements.
   @param width     Field width for each column.
   @see java.text.DecimalFormat#setDecimalFormatSymbols
	 */

	public void print (NumberFormat format, int width) {
		print(new PrintWriter(System.out,true),format,width); }

	// DecimalFormat is a little disappointing coming from Fortran or C's printf.
	// Since it doesn't pad on the left, the elements will come out different
	// widths.  Consequently, we'll pass the desired column width in as an
	// argument and do the extra padding ourselves.

	/** Print the matrix to the output stream.  Line the elements up in columns.
	 * Use the format object, and right justify within columns of width
	 * characters.
	 * Note that is the matrix is to be read back in, you probably will want
	 * to use a NumberFormat that is set to US Locale.
   @param output the output stream.
   @param format A formatting object to format the matrix elements
   @param width  Column width.
   @see java.text.DecimalFormat#setDecimalFormatSymbols
	 */

	public void print (PrintWriter output, NumberFormat format, int width) {
		output.println();  // start on new line.
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				String s = format.format(A[i][j]); // format the number
				int padding = Math.max(1,width-s.length()); // At _least_ 1 space
				for (int k = 0; k < padding; k++)
					output.print(' ');
				output.print(s);
			}
			output.println();
		}
		output.println();   // end with blank line.
	}

	/** Read a matrix from a stream.  The format is the same the print method,
	 * so printed matrices can be read back in (provided they were printed using
	 * US Locale).  Elements are separated by
	 * whitespace, all the elements for each row appear on a single line,
	 * the last row is followed by a blank line.
	 * @param input the input stream.
	 */

	public static Matrix read (BufferedReader input) throws java.io.IOException {
		StreamTokenizer tokenizer= new StreamTokenizer(input);

		// Although StreamTokenizer will parse numbers, it doesn't recognize
		// scientific notation (E or D); however, Double.valueOf does.
		// The strategy here is to disable StreamTokenizer's number parsing.
		// We'll only get whitespace delimited words, EOL's and EOF's.
		// These words should all be numbers, for Double.valueOf to parse.

		tokenizer.resetSyntax();
		tokenizer.wordChars(0,255);
		tokenizer.whitespaceChars(0, ' ');
		tokenizer.eolIsSignificant(true);
		java.util.Vector<Double> vD = new java.util.Vector<Double>();

		// Ignore initial empty lines
		while (tokenizer.nextToken() == StreamTokenizer.TT_EOL);
		if (tokenizer.ttype == StreamTokenizer.TT_EOF)
			throw new java.io.IOException("Unexpected EOF on matrix read.");
		do {
			vD.addElement(Double.valueOf(tokenizer.sval)); // Read & store 1st row.
		} while (tokenizer.nextToken() == StreamTokenizer.TT_WORD);

		int n = vD.size();  // Now we've got the number of columns!
		double row[] = new double[n];
		for (int j=0; j<n; j++)  // extract the elements of the 1st row.
			row[j]=vD.elementAt(j).doubleValue();
		java.util.Vector<double[]> v = new java.util.Vector<double[]>();
		v.addElement(row);  // Start storing rows instead of columns.
		while (tokenizer.nextToken() == StreamTokenizer.TT_WORD) {
			// While non-empty lines
			v.addElement(row = new double[n]);
			int j = 0;
			do {
				if (j >= n) throw new java.io.IOException
				("Row " + v.size() + " is too long.");
				row[j++] = Double.valueOf(tokenizer.sval).doubleValue();
			} while (tokenizer.nextToken() == StreamTokenizer.TT_WORD);
			if (j < n) throw new java.io.IOException
			("Row " + v.size() + " is too short.");
		}
		int m = v.size();  // Now we've got the number of rows.
		double[][] A = new double[m][];
		v.copyInto(A);  // copy the rows out of the vector
		return new Matrix(A);
	}


	/* ------------------------
       Private Methods
	 * ------------------------ */

	/** Check if size(A) == size(B) **/

	private void checkMatrixDimensions (Matrix B) {
		if (B.m != m || B.n != n) {
			throw new IllegalArgumentException("Matrix dimensions must agree.");
		}
	}

	private static final long serialVersionUID = 1;

	/* ------------------------
       End code from the JAMA Library
	 * ------------------------ */

	/* -----------------------------------------------------------------------
	 * This code is coded from the original authors of the project
	 * and implements the functions the project is based on
	 * ----------------------------------------------------------------------*/

	/* TODO
	 * LU
	 * QR
	 * Solving triangular systems, Determinant, Trace, Eigenvalues
	 * Eigenvectors, Rotate, reflect, or project a vector
	 * Solve using backwards substitution
	 * Jacobi and Gauss-Seidel iteration
	 * Power Method - also calculate the number of iterations given the tolerance
	 */

	/* ------------------------
	   Public Methods
	 * ------------------------ */

	/**
	 * Multiply two matrices, C = A * B
	 * @param B Second matrix
	 * @return Resultant matrix  A * B
	 */
	public Matrix multiply(Matrix B) {
		checkMultiplyDimensions(B);
		Matrix X = new Matrix(this.m, B.n);
		double[][] C = X.getArray();
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < B.n; j++) {
				C[i][j] = dotProduct(this.getRowVector(i), B.getColumnVector(j));
			}
		}
		return X;
	}

	/**
	 * Multiply two matrices in place, A = A * B
	 * @param B Second Matrix
	 * @return Resultant Matrix A
	 */
	public Matrix multiplyEquals(Matrix B) {
		checkMultiplyDimensions(B);
		Matrix X = new Matrix(this.m, B.n);
		double[][] C = X.getArray();
		for (int i = 0; i < this.m; i++) {
			for (int j = 0; j < B.n; j++) {
				C[i][j] = dotProduct(this.getRowVector(i), B.getColumnVector(j));
			}
		}
		this.A = X.getArray();
		this.m = X.m;
		this.n = X.n;
		return this;
	}
	
	/**
	 * Multiply a matrix with a vector
	 * @param b Vector to multiply with
	 * @return Resultant vector
	 */
	public double[] multiplyVector(double[] b) {
		checkMultiplyVectorDimensions(b);
		double[] c = new double[b.length];
		for (int i = 0; i < b.length; i++) {
			c[i] = dotProduct(this.getRowVector(i), b);
		}
		return c;
	}

	/**
	 * Gets determinant of matrix
	 * @return determinant of the matrix
	 */
	public double determinant() throws IllegalArgumentException {
		if (!(m == n)) {
			throw new IllegalArgumentException("Determinents for square matrices only");
		}
		double ans = 0;
		if (m == 1) {
			ans = A[m][n];
		}
		if (n == 2) {
			ans = ((A[0][0] * A[1][1]) - (A[0][1] * A[1][0]));
		} else {
			for (int i = 0; i < 1; i++) {
				double[][] det = new double[m-1][n-1];
				double scal = 0;
				for (int j = 0; j < n; j++) {
					scal = A[i][j];
					int mcount = 0;
					for (int k = 1; k < m; k++) {
						int ncount = 0;
						for (int l = 0; l < n; l++) {
							if (!(l == j)) {
								det[mcount][ncount] = A[k][l];
								ncount += 1;
							}
						}
						mcount += 1;
					}
					Matrix step = new Matrix(det);
					double neg = 1;
					if (j%2 == 1) {
						neg = -1;
					}
					ans = ans + (neg*(step.determinant() * scal));
				}
			}
		}
		return ans;
	}
	
	/**
	 * Calculates the max norm of a matrix
	 * @return The greatest element in the matrix
	 */
	public double maxNorm() {
		double norm = maxElement(this.getRowVector(0));
		for (int i = 1; i < m; i++) {
			double maxElem = maxElement(this.getRowVector(i));
			norm = Math.max(Math.abs(norm), Math.abs(maxElem));
		}
		return norm;
	}
	
	
	
	/**
	 * Hilbert Matrix
	 * @param n Dimension
	 * @return a n x n Hilbert Matrix
	 */
	public static Matrix hilbertMatrix(int n) {
		double[][] hilArray = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				hilArray[i][j] = 1. / (1 + j + i);
			}
		}
		Matrix hilbert = new Matrix(hilArray);
		return hilbert;
	}

	/**
	 * Get an instance of the QR decomposition class using householder
	 * reflections
	 * @return An instance of the QR decomposition with householders
	 */
	public QRDecompHouseHolder qrHouseHolder() {
		return new QRDecompHouseHolder(this);
	}

	/**
	 * Get an instance of the QR decomposition class using givens
	 * rotations
	 * @return An instance of the QR decomposition with givens
	 */
	public QRDecompGivens qrGivens() {
		return new QRDecompGivens(this);
	}

	/* ------------------------
       Private Methods
	 * ------------------------ */
	private void checkMultiplyDimensions(Matrix B) {
		if (this.n != B.m) {
			throw new IllegalArgumentException("First matrix n must equal second matrix m.");
		}
	}
	
	protected void checkMultiplyVectorDimensions(double[] b) {
		if (m != b.length) {
			throw new IllegalArgumentException("Matrix and vector must be same length");
		}
	}

	/* ------------------------
	   Vector Stuff
	 * ------------------------ */

	/**
	 * Get a row vector from the matrix
	 * @param mIndex index of the column to start at
	 * @param nIndex index of the row to start at
	 * @return Row vector
	 */
	public double[] getRowVector(int mIndex, int nIndex) {
	    try {
	        double[] rowVector = new double[this.n - nIndex];
	        for (int i = nIndex; i < this.n; i++) {
	        	rowVector[i - nIndex] = this.A[mIndex][i];
	        }
	        //rowVector = A[colIndex];
	        return rowVector;
	    } catch (ArrayIndexOutOfBoundsException e) {
	        throw new ArrayIndexOutOfBoundsException("Index is not valid");
	    }
	}

	/**
	 * Gets the whole row vector from the matrix
	 * @param mIndex M index to find the row vector of
	 * @return Complete row vector from matrix
	 */
	public double[] getRowVector(int mIndex) {
		return this.getRowVector(mIndex, 0);
	}

	/**
	 * Get a column vector from the matrix
	 * @param mIndex index of the column to start at
	 * @param nIndex index of the row to start at
	 * @return Column vector
	 */
	public double[] getColumnVector(int mIndex, int nIndex) {
	    try {
	        double[] colVector = new double[this.m - mIndex];
	        for (int i = mIndex; i < this.m; i++) {
	            colVector[i - mIndex] = this.A[i][nIndex];
	        }
	        return colVector;
	    } catch (ArrayIndexOutOfBoundsException e) {
	        throw new ArrayIndexOutOfBoundsException("Index is not valid");
	    }
	}

	/**
	 * Gets the whole column vector from the matrix
	 * @param nIndex N index to find the row vector of
	 * @return Complete column vector from matrix
	 */
	public double[] getColumnVector(int nIndex) {
		return this.getColumnVector(0, nIndex);
	}

	/**
	 * Gets the dot product of two vectors
	 * @param a First vector
	 * @param b Second vector
	 * @return The scalar answer to the dot product
	 */
	public static double dotProduct(double[] a, double[] b) {
		if (a.length != b.length) {
			throw new IllegalArgumentException("Vectors are not the same length.");
		}
		double ans = 0;
		for (int i = 0; i < a.length; i++) {
			ans += a[i] * b[i];
		}
		return ans;
	}

	/**
	 * Gets the norm of a vector
	 * @param a Vector a
	 * @return Norm of the vector
	 */
	public static double norm(double[] a) {
		double norm = 0;
		for (int i = 0; i < a.length; i++) {
			norm += a[i] * a[i];
		}
		norm = Math.sqrt(norm);
		return norm;
	}

	/**
	 * Return the addition of two vectors
	 * @param a First vector
	 * @param b Second vector
	 * @return Return the resultant vector
	 */
	public static double[] plus(double[] a, double[] b) {
		if (a.length != b.length) {
			throw new IllegalArgumentException("Vectors are not the same length.");
		}
		double[] c = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			c[i] = a[i] + b[i];
		}
		return c;
	}

	/**
	 * Return the subtraction of two vectors
	 * @param a First vector
	 * @param b Second vector
	 * @return Return the resultant vector
	 */
	public static double[] minus(double[] a, double[] b) {
		if (a.length != b.length) {
			throw new IllegalArgumentException("Vectors are not the same length.");
		}
		double[] c = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			c[i] = a[i] - b[i];
		}
		return c;
	}

	/**
	 * Multiply a vector by a scalar
	 * @param a Vector a
	 * @param s Scalar s
	 * @return Resultant vector
	 */
	public static double[] multiply(double[] a, double s) {
		double[] c = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			c[i] = s * a[i];
		}
		return c;
	}

	/**
	 * Multiply two vectors, assuming one is a row and the other is a column
	 * @param a First vector, column vector
	 * @param b Second vector, row vector
	 * @return Matrix from multiplying the two vectors
	 */
	public static Matrix multiplyVectors(double[] a, double[] b) {
		Matrix X = new Matrix(a.length, b.length);
		double[][] c = X.getArray();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b.length; j++) {
				c[i][j] = a[i] * b[j];
			}
		}
		return X;
	}
	
	/**
	 * Returns the b for the hilbert matrix 
	 * b = (0.1)^(n/3)* (1, 1,...,1)^t
	 * @param n The length of the hilbert matrix n
	 * @return b for the hilbert matrix
	 */
	public static double[] hilbertB(int n) {
		double[] b = new double[n];
		double x = Math.pow(0.1, n/3.);
		for (int i = 0; i < b.length; i++) {
			b[i] = x;
		}
		return b;
	}
	
	/**
	 * Gets the max element in an array
	 * @param arr Array to find
	 * @return Max element
	 */
	public static double maxElement(double[] arr) {
		double max = Math.abs(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			max = Math.max(Math.abs(arr[i]), Math.abs(max));
		}
		return max;
	}
	/* ------------------------
	   End Vector Stuff
	 * ------------------------ */

}
