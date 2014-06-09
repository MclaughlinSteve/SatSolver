
import java.io.*;
/**
 *
 * @author Steve
 */

public class InputConverter {

    static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    private final int SIZE;
    private int board[][];
    private int numClauses;
    private String header;
    private String clauses;



    public InputConverter() //default constructor for 9x9 puzzles
    {
        SIZE = 9;
        board = new int[SIZE][SIZE];
        numClauses = 0;
        clauses = "";
    }



    public InputConverter(int n) //constructor for puzzles of size n
    {
        SIZE = n;
        board = new int[SIZE][SIZE];
        numClauses = 0;
        clauses = "";
    }

    

    public void applyValues(String s) //This method parses the input string and fills the sudoku board
    { 
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Integer.parseInt(s.substring(count, ++count)); 
            }
        }
            }



    public void knownClauses() //sets clauses based on known values in the puzzle
    {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != 0) {
                    clauses += (i + 1) + "" + (j + 1) + "" + board[i][j] + " " + " 0\n";
                    numClauses++;
                }
            }
        }
    }



    public void makeHeader() //makes the first part of the input to the sat solver
    {
        header = "p cnf " + SIZE + "" + SIZE + "" + SIZE + " " + numClauses + "\n";
    }



    public String getInput() // this returns the actual string that will be used for the input
    {
        return header + clauses;
    }


//*******************************************
// The following series of methods creates an exhaustive list of clauses for all puzzles
//*******************************************

    /*
     * Method for creating clauses stating that each cell contains at least one value
     * @param istart the beginning point for the row counter
     * @param iend the end point for the row counter
     * @param jstart the beginning point for the column counter
     * @param jend the end point for the column counter
     */
    public void atLeastOneVariablePerCellClauses(int istart, int iend, int jstart, int jend)
    {
        String temp = "";
        for (int i = istart; i <= iend; i++) {
            for (int j = jstart; j <= jend; j++) {
                for (int k = 1; k <= SIZE; k++) {
                    temp += i + "" + j + "" + k + " ";
                }
                temp += " 0\n";
                numClauses++;
            }
        }
        clauses += temp;
    }



    /*
     * Method for creating clauses stating that each cell contains at most one value
     * @param istart the beginning point for the row counter
     * @param iend the end point for the row counter
     * @param jstart the beginning point for the column counter
     * @param jend the end point for the column counter
     */
    public void atMostOneVariablePerCellClauses(int istart, int iend, int jstart, int jend) 
    {     
        String temp = "";
        for (int i = istart; i <= iend; i++) {
            for (int j = jstart; j <= jend; j++) {
                for (int k = 1; k <= SIZE; k++) {
                    for (int l = k + 1; l <= SIZE; l++) {
                        temp += "-" + i + "" + j + "" + k + " " + "-" + i + "" + j + "" + l + " ";
                        temp += " 0\n";
                        numClauses++;
                    }
                }
            }
        }
        clauses += temp;     
    }



    public void eachValueExistsInEachRow() //Method for creating clauses stating that each row contains at least one value
    {
        String temp = "";
        for (int i = 1; i <= SIZE; i++) {
            for (int j = 1; j <= SIZE; j++) {
                for (int k = 1; k <= SIZE; k++) {
                    temp += i + "" + k + "" + j + " ";
                }
                temp += " 0\n";
                numClauses++;
            }
        }
        clauses += temp;
    }
    
    public void atMostOneVariablePerRowClauses() //Method for creating clauses stating that each row contains at most one value
    {
        String temp = "";
        for (int i = 1; i <= SIZE; i++) {
            for (int j = 1; j <= SIZE; j++) {
                for (int k = 1; k <= SIZE; k++) {
                    for (int l = j+1; l <= SIZE; l++) {
                        temp += "-" + i + "" + j + "" + k + " " + "-" + i + "" + l + "" + k + " ";
                        temp += " 0\n";
                        numClauses++;
                    }
                }
            }
        }
        clauses += temp;
    }




    public void eachValueExistsInEachColumn() //Method for creating clauses stating that each column contains at least one value
    {
        String temp = "";
        for (int i = 1; i <= SIZE; i++) {
            for (int j = 1; j <= SIZE; j++) {
                for (int k = 1; k <= SIZE; k++) {
                    temp += k + "" + i + "" + j + " "; //switched i and j to test
                }
                temp += " 0\n";
                numClauses++;
            }
        }
        clauses += temp;
    }
    
        public void atMostOneVariablePerColumnClauses() //Method for creating clauses stating that each column contains at most one value
        {
        String temp = "";
        for (int i = 1; i <= SIZE; i++) {
            for (int j = 1; j <= SIZE; j++) {
                for (int k = 1; k <= SIZE; k++) {
                    for (int l = i+1; l <= SIZE; l++) {
                        temp += "-" + i + "" + j + "" + k + " " + "-" + l + "" + j + "" + k + " ";
                        temp += " 0\n";
                        numClauses++;
                    }
                }
            }
        }
        clauses += temp;
    }




    public void eachValueExistsInEachBox() //Method for creating clauses stating that each box contains at least one value
    {
        int boxSize = (int) Math.sqrt((double) SIZE);      
        for (int i = 1; i <= SIZE; i+=boxSize) {
            for (int j = 1; j <= SIZE; j+=boxSize) {          
                atLeastOneVariablePerCellClauses(i, (i+boxSize-1), j, (j+boxSize-1));
                }
            }
        }



    
    public void atMostOneVariablePerBoxClauses() //Method for creating clauses stating that each box contains at most one value
   {
        int boxSize = (int) Math.sqrt((double) SIZE);       
        for (int i = 1; i <= SIZE; i+=boxSize) {
            for (int j = 1; j <= SIZE; j+=boxSize) {          
                boxClauseHelper(i, (i+boxSize-1), j, (j+boxSize-1));
                }
            }
        }
 
  public void boxClauseHelper(int istart, int iend, int jstart, int jend)
    {
        String temp = "";
        for(int m = 1; m <= SIZE; m++)
            for(int i = istart; i <= iend; i++)
                for(int j = jstart; j <= jend; j++) 
                    for(int k = i; k <= iend; k++)
                        for(int l = jstart; l <= jend; l++)                                                
                        {
                            String firstValue = (i+""+j+""+m);
                            String secondValue = (k+""+l+""+m);
                            int first = Integer.parseInt(firstValue);
                            int second = Integer.parseInt(secondValue);
                            if(!(firstValue.equals(secondValue)) && first < second)
                            {
                                temp += "-" + firstValue + " ";
                                temp += "-" + secondValue + " ";
                                temp += " 0\n";
                                numClauses++;
                            }
                        }
           clauses += temp;             
                
    }
   
//*******************************************
// End of the clause methods
//*******************************************



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int puzzleSize = 9;
//        System.out.println("Enter size of sudoku puzzle: ");
//        puzzleSize = Integer.parseInt(stdin.readLine());
/*
 * Above two lines are reserved for puzzles of different sizes. 
 * Uncomment those for any size puzzle to work
 * The user doesn't input the puzzle size for this project so I left it commented
 */

        InputConverter puzzle = new InputConverter(puzzleSize);
//      System.out.println("Enter sudoku information: ");
        String sudokuInfo = stdin.readLine();
        puzzle.applyValues(sudokuInfo);
        puzzle.knownClauses();
        puzzle.atMostOneVariablePerCellClauses(1, puzzleSize, 1, puzzleSize);
        puzzle.atLeastOneVariablePerCellClauses(1, puzzleSize, 1, puzzleSize);
        puzzle.eachValueExistsInEachRow();
        puzzle.eachValueExistsInEachColumn();
        puzzle.eachValueExistsInEachBox();
        puzzle.atMostOneVariablePerBoxClauses();
        puzzle.atMostOneVariablePerColumnClauses();
        puzzle.atMostOneVariablePerRowClauses();
        puzzle.makeHeader();

        System.out.println(puzzle.getInput());

        

    }

}
