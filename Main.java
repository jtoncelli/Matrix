package com.company;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
class Main {
    /**
     * Create and fill a 2D array containing elements
     * read from the supplied Scanner. The number of
     * rows and columns are specified. The elements should
     * be read in row major order. Elements will be separated
     * by whitespace.
     *
     * Precondition: the scanner has rows * columns valid integers
     *
     * For example, supplying the input:
     * "2 4 6 8
     * 1 3 5 7"
     * for 2 rows and 4 columns should return the 2D array:
     * {{2,4,6,8},{1,3,5,7}}
     */
    public static int[][] getMatrix(Scanner scan, int rows, int columns) {
        scan = new Scanner(System.in);
        int[][] matrix = new int[rows][columns];
        for (int i = 0; i<rows; i++){
            String input = scan.nextLine();
            input = input.trim();
            String [] stuff = input.split(" ");
            for (int j = 0; j<columns; j++){
                try{
                    matrix[i][j] = Integer.parseInt(stuff[j]);
                }catch(IllegalArgumentException e){
                    System.out.println("Invalid input.");
                    return null;
                }
            }
        }
        return matrix;
    }

    /**
     * Create a String representation of the matrix.
     * Each row should be on its own line.
     * Do not include a newline after the last line.
     * Columns should be separated by spaces.
     * Precondition: matrix is not jagged
     *
     * The output of this method could be used as input to
     * the getMatrix method.
     *
     * For example, the 2D array with 2 rows and 4 columns:
     * {{2,4,6,8},{1,3,5,7}}
     * should be rendered as a String like:
     * "2 4 6 8
     * 1 3 5 7"
     */
    public static String getMatrixString(int[][] matrix) {
        String print = "";
        for (int row = 0; row<matrix.length; row++){
            for(int col = 0; col<matrix[row].length; col++){
                print += (matrix[row][col] + " ");
            }
            if(row != matrix.length - 1)
                print += "\n";
        }
        return print;
    }

    /**
     * Return the number of negative elements in the array.
     * Precondition: matrix is not jagged
     */
    public static int getNegativeCount(int[][] matrix) {
        int counter = 0;
        for (int row = 0; row<matrix.length; row++){
            for(int col = 0; col<matrix[row].length; col++){
                if(matrix[row][col] < 0){
                    counter++;
                }
            }
        }
        return counter;
    }

    public static int[][] getMinor(int[][]matrix, int index){
        int row = matrix.length;
        int col = matrix[0].length;
        int [][] newMatrix = new int[row - 1][col - 1];
        ArrayList<Integer> info = new ArrayList<Integer>();
        for (int i = 1; i<matrix.length; i++){
            for (int j = 0; j<matrix[i].length; j++){
                if (j != index){
                    info.add(matrix[i][j]);
                }
            }
        }
        int counter = 0;
        for(int i = 0; i<newMatrix.length; i++){
            for(int j = 0; j<newMatrix[i].length; j++){
                newMatrix[i][j] = info.get(counter++);
            }
        }
        return newMatrix;
    }
    /**
     * Return the determinant of a square matrix of integers.
     * Precondition: matrix is square
     *
     * See:
     * https://en.wikipedia.org/wiki/Determinant
     */
    public static int getDeterminant(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int deter = 0;
        if(row == 1 && col== 1){
            return matrix[0][0];
        }
        else if(row ==2 && col == 2){
            return(matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        }
        else{
            for (int i= 0; i<matrix.length; i++){
                int [][] newMatrix = getMinor(matrix, i);
                if(i%2 ==0){
                    deter= deter+ (matrix[0][i] * getDeterminant(newMatrix));
                }
                else{
                    deter= deter- (matrix[0][i] * getDeterminant(newMatrix));
                }
            }
        }
        return deter;
    }

    /**
     * Return the sum of two matrices of integers.
     * Precondition: matrices are not jagged and have the same dimensions
     *
     * See:
     * https://en.wikipedia.org/wiki/Matrix_addition
     */
    public static int[][] getMatrixSum(int[][] matrix1, int[][] matrix2) {
        int[][] sum = matrix1;
        for (int row = 0; row<matrix1.length; row++){
            for(int col = 0; col<matrix1[row].length; col++){
                sum[row][col] = matrix1[row][col] + matrix2[row][col];
            }
        }
        return sum;
    }

    /**
     * Return the product of a scalar and a matrix.
     * Precondition: matrix is not jagged
     *
     * See:
     * https://en.wikipedia.org/wiki/Scalar_multiplication
     */
    public static int[][] getScalarMultiple(int scalar, int[][] matrix) {
        int[][] product = matrix;
        for (int row = 0; row<matrix.length; row++){
            for(int col = 0; col<matrix[row].length; col++){
                product[row][col] = matrix[row][col]* scalar;
            }
        }
        return product;
    }

    /**
     * Return the transposition of a matrix.
     * Precondition: matrix is not jagged
     *
     * See:
     * https://en.wikipedia.org/wiki/Transpose
     */
    public static int[][] getTransposition(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int [][] transposed = new int[col][row];
        for (int i = 0; i<matrix.length; i++){
            for (int j = 0; j<matrix[i].length; j++){
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    /////////////////////////////////////////////////////////////
    // Lines below are for testing using main.
    /////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        HashMap<String,int[][]> table = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the matrix!");

        do {
            System.out.println("Enter command (or " + CMD_HELP + "):");
            String command = scanner.next();
            if (handleCommand(scanner, table, command)) {
                break;
            }
        } while(true);
    }

    private static final String CMD_READ = "mat";
    private static final String HLP_READ = "read matrix to variable";
    private static final String CMD_STRING = "str";
    private static final String HLP_STRING = "show string representation of matrix";
    private static final String CMD_NEGATIVE = "neg";
    private static final String HLP_NEGATIVE = "get negative count of matrix";
    private static final String CMD_DETERMINANT = "det";
    private static final String HLP_DETERMINANT = "calculate determinant of matrix";
    private static final String CMD_ADD = "add";
    private static final String HLP_ADD = "add matrices";
    private static final String CMD_MULTIPLY = "mul";
    private static final String HLP_MULTIPLY = "multiply matrix by scalar";
    private static final String CMD_TRANSPOSE = "trn";
    private static final String HLP_TRANSPOSE = "transpose matrix";
    private static final String CMD_LIST = "lst";
    private static final String HLP_LIST = "list defined matrices";
    private static final String CMD_HELP = "help";
    private static final String CMD_QUIT = "xit";
    private static final String HLP_QUIT = "exit the matrix";

    private static boolean handleCommand(Scanner scanner, HashMap<String,int[][]> table, String command) {
        if (command.equals(CMD_READ)) {
            handleMatrixRead(scanner, table);
        } else if (command.equals(CMD_STRING)) {
            handleMatrixString(scanner, table);
        } else if (command.equals(CMD_NEGATIVE)) {
            handleNegativeCount(scanner, table);
        } else if (command.equals(CMD_DETERMINANT)) {
            handleDeterminant(scanner, table);
        } else if (command.equals(CMD_ADD)) {
            handleSum(scanner, table);
        } else if (command.equals(CMD_MULTIPLY)) {
            handleScalarMultiple(scanner, table);
        } else if (command.equals(CMD_TRANSPOSE)) {
            handleTransposition(scanner, table);
        } else if (command.equals(CMD_LIST)) {
            handleList(scanner, table);
        } else if (command.equals(CMD_HELP)) {
            handleHelp(scanner, table);
        } else if (command.equals(CMD_QUIT)) {
            return true;
        } else {
            System.out.println("Unknown command");
        }

        return false;
    }

    private static void handleMatrixRead(Scanner scanner, HashMap<String,int[][]> table) {
        System.out.println("Enter matrix name to store to:");
        String name = scanner.next();
        if (name == null || name.isEmpty()) {
            System.out.println("Invalid name");
            return;
        }

        try {
            System.out.println("Enter number of rows:");
            int rows = scanner.nextInt();
            System.out.println("Enter number of columns:");
            int columns = scanner.nextInt();
            System.out.println("Enter the matrix:");
            int[][] matrix = getMatrix(scanner, rows, columns);
            if (!isValid(matrix)) {
                System.out.println("getMatrix returned an invalid matrix");
            } else {
                table.put(name, matrix);
            }
        } catch (Exception ex) {
            System.out.println("Error while reading matrix");
        }
    }

    private static void handleMatrixString(Scanner scanner, HashMap<String,int[][]> table) {
        System.out.println("Enter matrix name:");
        String name = scanner.next();
        if (name == null || name.isEmpty()) {
            System.out.println("Invalid name");
        } else {
            int[][] matrix = table.get(name);
            if (matrix == null) {
                System.out.println("Unknown matrix " + name);
            } else {
                String str = getMatrixString(matrix);
                System.out.println("String representation:");
                System.out.println(str);
            }
        }
    }

    private static void handleNegativeCount(Scanner scanner, HashMap<String,int[][]> table) {
        System.out.println("Enter matrix name:");
        String name = scanner.next();
        if (name == null || name.isEmpty()) {
            System.out.println("Invalid name");
        } else {
            int[][] matrix = table.get(name);
            if (matrix == null) {
                System.out.println("Unknown matrix " + name);
            } else {
                int count = getNegativeCount(matrix);
                System.out.println("Negative count:");
                System.out.println(count);
            }
        }
    }

    private static void handleDeterminant(Scanner scanner, HashMap<String,int[][]> table) {
        System.out.println("Enter matrix name:");
        String name = scanner.next();
        if (name == null || name.isEmpty()) {
            System.out.println("Invalid name");
        } else {
            int[][] matrix = table.get(name);
            if (matrix == null) {
                System.out.println("Unknown matrix " + name);
            } else if (!isSquare(matrix)) {
                System.out.println("Cannot take determinant of non-square matrix");
            } else {
                int det = getDeterminant(matrix);
                System.out.println("Determinant:");
                System.out.println(det);
            }
        }
    }

    private static void handleSum(Scanner scanner, HashMap<String,int[][]> table) {
        System.out.println("Enter first matrix name:");
        String name1 = scanner.next();
        if (name1 == null || name1.isEmpty()) {
            System.out.println("Invalid name");
        } else {
            int[][] matrix1 = table.get(name1);
            if (matrix1 == null) {
                System.out.println("Unknown matrix " + name1);
            } else {
                System.out.println("Enter second matrix name:");
                String name2 = scanner.next();
                if (name2 == null || name2.isEmpty()) {
                    System.out.println("Invalid name");
                } else {
                    int[][] matrix2 = table.get(name2);
                    if (matrix2 == null) {
                        System.out.println("Unknown matrix " + name2);
                    } else {
                        if (isMatchingDimension(matrix1, matrix2)) {
                            int[][] sum = getMatrixSum(matrix1, matrix2);
                            if (isValid(sum)) {
                                String dim = getDimensionString(sum);
                                System.out.println("Sum is " + dim);
                                String str = getMatrixString(sum);
                                System.out.println("String representation of sum:");
                                System.out.println(str);
                            } else {
                                System.out.println("getMatrixSum returned an invalid matrix");
                            }
                        } else {
                            System.out.println("Matrices do not have the same dimensions");
                        }
                    }
                }
            }
        }
    }

    private static void handleScalarMultiple(Scanner scanner, HashMap<String,int[][]> table) {
        System.out.println("Enter matrix name:");
        String name = scanner.next();
        if (name == null || name.isEmpty()) {
            System.out.println("Invalid name");
        } else {
            int[][] matrix = table.get(name);
            if (matrix == null) {
                System.out.println("Unknown matrix " + name);
            } else {
                System.out.println("Enter scalar to multiply by:");
                int scalar = scanner.nextInt();
                int[][] result = getScalarMultiple(scalar, matrix);
                if (isValid(result)) {
                    String dim = getDimensionString(result);
                    System.out.println("Scalar multiple is " + dim);
                    String str = getMatrixString(result);
                    System.out.println("String representation of scalar multiple:");
                    System.out.println(str);
                } else {
                    System.out.println("getScalarMultiple returned an invalid matrix");
                }
            }
        }
    }

    private static void handleTransposition(Scanner scanner, HashMap<String,int[][]> table) {
        System.out.println("Enter matrix name:");
        String name = scanner.next();
        if (name == null || name.isEmpty()) {
            System.out.println("Invalid name");
        } else {
            int[][] matrix = table.get(name);
            if (matrix == null) {
                System.out.println("Unknown matrix " + name);
            } else {
                int[][] transposed = getTransposition(matrix);
                if (isValid(transposed)) {
                    String dim = getDimensionString(transposed);
                    System.out.println("Transposition is " + dim);
                    String str = getMatrixString(transposed);
                    System.out.println("String representation of transposition:");
                    System.out.println(str);
                } else {
                    System.out.println("getTransposition returned an invalid matrix");
                }
            }
        }
    }

    private static void handleList(Scanner scanner, HashMap<String,int[][]> table) {
        System.out.println("Defined matrices:");
        for (String name : table.keySet()) {
            System.out.println(name);
        }
    }

    private static void handleHelp(Scanner scanner, HashMap<String,int[][]> table) {
        System.out.println("Command - Description");
        System.out.println(CMD_READ + " - " + HLP_READ);
        System.out.println(CMD_STRING + " - " + HLP_STRING);
        System.out.println(CMD_NEGATIVE + " - " + HLP_NEGATIVE);
        System.out.println(CMD_DETERMINANT + " - " + HLP_DETERMINANT);
        System.out.println(CMD_ADD + " - " + HLP_ADD);
        System.out.println(CMD_MULTIPLY + " - " + HLP_MULTIPLY);
        System.out.println(CMD_TRANSPOSE + " - " + HLP_TRANSPOSE);
        System.out.println(CMD_LIST + " - " + HLP_LIST);
        System.out.println(CMD_QUIT + " - " + HLP_QUIT);
    }

    private static boolean isSquare(int[][] matrix) {
        return matrix.length > 0 && matrix[0].length == matrix.length;
    }

    private static boolean isValid(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        for (int row = 1; row < matrix.length; row++) {
            if (matrix[0].length != matrix[row].length) {
                return false;
            }
        }

        return true;
    }

    private static boolean isMatchingDimension(int[][] matrix1, int[][] matrix2) {
        return matrix1.length == matrix2.length && matrix1[0].length == matrix2[0].length;
    }

    private static String getDimensionString(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        return "" + rows + "x" + columns;
    }

}
