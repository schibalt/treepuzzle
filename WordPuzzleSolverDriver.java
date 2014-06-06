package treepuzzle;

import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;

//DO NOT MODIFY THIS PROGRAM!!!
/**
This is the driver program for the puzzle solver project
 */
public class WordPuzzleSolverDriver
{
    public static void main(String[] args) throws IOException
    {
        PuzzleSolverGUI p2 = new PuzzleSolverGUI();
        p2.setSize(new Dimension(598, 678));
        p2.setVisible(true);
        p2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
