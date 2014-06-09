/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
/**
 *
 * @author Steven
 */
public class SatConverter {
    static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    
    
    final int SIZE;
    int board[][];
    
    public SatConverter()
    {
        SIZE = 9;
        board = new int[SIZE][SIZE];
    }

    public SatConverter(int n)
    {
        SIZE = n;
        board = new int[SIZE][SIZE];
    }
    
    public void satToArray(String satString)
    {
	satString = satString.substring(332, satString.length());
        //System.out.println(satString);
	int testValue;
	//if(satString.contains(" ")) 
	//{
	testValue = satString.indexOf(" ");
	//}
	//else
	//{testValue = -1;}
	
	//System.out.println(testValue);
	while(testValue > 0)
	{
		int temp = Integer.parseInt(satString.substring(0, satString.indexOf(" ")));
		if(temp > 0)
		{
			int row = Integer.parseInt(satString.substring(0,1));
			int col = Integer.parseInt(satString.substring(1,2));
			int val = Integer.parseInt(satString.substring(2,3));
			
			if(row!=0 && col !=0 && val !=0)
			{
				board[row-1][col-1] = val;
			}
		}
		
		if( satString.indexOf(" ")+1 < satString.length())
		satString = satString.substring(satString.indexOf(" ")+1, satString.length());
		else
		testValue = -1;
	}
        int temp = Integer.parseInt(satString.trim());
	if(temp > 0)
	{
		int row = Integer.parseInt(satString.substring(0,1));
		int col = Integer.parseInt(satString.substring(1,2));
		int val = Integer.parseInt(satString.substring(2,3));

		board[row-1][col-1] = val;
	}
    }

    public void outputValues(){

	String outputString = "";
	for(int i=0; i <SIZE; i++){
		for(int j=0; j < SIZE; j++)
			outputString += board[i][j] + " ";
	outputString += "\n";
	}
	System.out.println(outputString);
	}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        SatConverter puzzle = new SatConverter();
        
       //System.out.println("Enter sudoku information");
       String sudokuInfo = stdin.readLine();
       puzzle.satToArray(sudokuInfo);
       puzzle.outputValues();
    }
    
}
