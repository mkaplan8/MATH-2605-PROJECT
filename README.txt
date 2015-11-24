---------------------- PART 1 INSTRUCTIONS ----------------------

To run part 1 of the project from the command line
    1. Navigate to the project directory
    2. Compile all the files using the command javac *.java
    3. Run each method individually using the command:
        - java qr_fact_givens
        - java qr_fact_househ
        - java solve_lu_b
        - java solve qr_b
        - java pascal
        - java pascalplot <-- a screenshot is also included in written_component.docx
    You will be given the option to enter your matrix details and calculation should
    output.
    **NOTE: Although the directions state that for solving LU & QR, calculating inverse
    matrices were not allowed, I have used Cramer's rule to solve for x. This does not
    use an inverse matrix** See: http://www.intmath.com/matrices-determinants/1-determinants.php


---------------------- PART 2 INSTRUCTIONS ----------------------

To Run part 2 of the project from the command line
    1. Navigate to the project directoy
    2. Compile all the files using the command javac 'filename'.java
    3. Run the IterativeMethodsTester by using the command java IterativeMethodsTest

Results from Running about command in order
    1. Randomly generated vector x(0)
    2. Jacobi output for x(N)
    3. Number of iterations needed to attain x(N)
    4. Gauss-Seidel output for x(N)
    5. Number of iterations needed to attain x(N)

After 100 random vectors generated
    1. Average number of Jacobi Iterations
    2. Average vector result after 100 Jacobi Iterations
    3. Average number of Gauss-Seidel Iterations
    4. Average vector result afte r100 Gauss-Seidel Iterations
    5. Exact vector solution
    6. Approximate error between average jacobi vector and exact solution
    7. Approximate error between average gauss-seidel vector and exact solution
    8. Ratio of jacobi iterations to gauss-seidel iterations

---------------------- PART 3 INSTRUCTIONS ----------------------

To run Part Three question 1 from the command line
	1. Navigate to the project directory
	2. Compile all the files using the command javac *.java
	3. Run java powerMethodTester
	4. This will print out randomly generated matricies along with their eigenvalues, eigenvectors, and amount of iterations
		(100 iterations max and error bound of 0.00005)
To run Part Three question 2
	1. Navigate to the project directory
	2. Compile all the files using the command javac *.java
	3. Run java GraphMaker.java
	4. Run java InverseGraphMaker.java
	This will generate both the graph with the normal matricies (GraphMaker) and the graph with
		the inverse matricies (InverseGraphMaker.java)