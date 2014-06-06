package treepuzzle;

import java.util.ArrayList;

/**
COPYRIGHT (C) 2012 Andrew Tilisky. All Rights Reserved.
 * the constructor here's called from the gui and the tree's created 
 * and searched from within the iterator using the bst objector's methods
@author Andrew Tilisky
 */
public class PuzzleSolver
{
//    private int n; // size of the puzzle
    private char[][] puzzle;
    private ArrayList<String> dictionary;
    private String found;
    private BinaryTree BST = new BinaryTree();

    /**
     * instantiates the fields, creates the tree and solves it.  found
     * items are stored in the member string, pritned to the console here and 
     * later the gui gets that string and puts it into its jtextarea
     */
    PuzzleSolver(char[][] aPuzzle, ArrayList<String> aDictionary)
    {
        puzzle = aPuzzle;

        dictionary = aDictionary;
        BST.setDict(dictionary);
        BST.createTree(dictionary, false);
        BST.inorder();

//        System.out.println("ivocations "+BST.getHeightcount());
    }

    // there are many methods, including the method solve(), that you need to implement to 
    // complete this programming project
    //	this method will be called by PuzzleSolverGUI object when the GO button is clicked.
    /**
    @return a String that contains the list of words in the puzzle that can be 
    found in the dictionary as well as their positions in the puzzle
     */
    public String solve()
    {
        found = "Words found:\n\n";

        for (int i = 0; i < puzzle.length; i++)
        {
            for (int j = 0; j < puzzle[0].length; j++)
            {
                int iPlusOffset = i;
                int jPlusOffset = j;
                String possibleWord = "";

                //north
                while (iPlusOffset >= 0)
                {
                    String letter = String.valueOf(puzzle[iPlusOffset][jPlusOffset]);
                    possibleWord = possibleWord.concat(letter);

                    String foundhere = searchFor(possibleWord);

                    if (!foundhere.equals(""))
                    {
                        getFound().concat("at (" + i + ", " + j + ") moving (-1, 0):" + foundhere + '\n');
                    }
                    iPlusOffset--;
                }

                iPlusOffset = i;
                jPlusOffset = j;
                possibleWord = "";

                //south
                while (iPlusOffset < puzzle.length)
                {
                    possibleWord = possibleWord.concat(String.valueOf(puzzle[iPlusOffset][jPlusOffset]));

                    String foundhere = searchFor(possibleWord);

                    if (!foundhere.equals(""))
                    {
                        found = getFound().concat("at (" + j + ", " + i + ") moving (0, +1):" + foundhere + '\n');
                    }
                    iPlusOffset++;
                }

                iPlusOffset = i;
                jPlusOffset = j;
                possibleWord = "";

                //west
                while (jPlusOffset >= 0)
                {
                    possibleWord = possibleWord.concat(String.valueOf(puzzle[iPlusOffset][jPlusOffset]));

                    String foundhere = searchFor(possibleWord);

                    if (!foundhere.equals(""))
                    {
                        found = getFound().concat("at (" + j + ", " + i + ") moving (-1, 0):" + foundhere + '\n');
                    }
                    jPlusOffset--;
                }

                iPlusOffset = i;
                jPlusOffset = j;
                possibleWord = "";

                //east
                while (jPlusOffset < puzzle[0].length)
                {
                    possibleWord = possibleWord.concat(String.valueOf(puzzle[iPlusOffset][jPlusOffset]));

                    String foundhere = searchFor(possibleWord);

                    if (!foundhere.equals(""))
                    {
                        found = getFound().concat("at (" + j + ", " + i + ") moving (+1, 0):" + foundhere + '\n');
                    }
                    jPlusOffset++;
                }

                iPlusOffset = i;
                jPlusOffset = j;
                possibleWord = "";

                //northwest
                while (iPlusOffset >= 0 && jPlusOffset >= 0)
                {
                    possibleWord = possibleWord.concat(String.valueOf(puzzle[iPlusOffset][jPlusOffset]));

                    String foundhere = searchFor(possibleWord);

                    if (!foundhere.equals(""))
                    {
                        found = getFound().concat("at (" + j + ", " + i + ") moving (-1, -1):" + foundhere + '\n');
                    }
                    iPlusOffset--;
                    jPlusOffset--;
                }

                iPlusOffset = i;
                jPlusOffset = j;
                possibleWord = "";

                //northeast
                while (iPlusOffset >= 0 && jPlusOffset < puzzle[0].length)
                {
                    possibleWord = possibleWord.concat(String.valueOf(puzzle[iPlusOffset][jPlusOffset]));

                    String foundhere = searchFor(possibleWord);

                    if (!foundhere.equals(""))
                    {
                        found = getFound().concat("at (" + j + ", " + i + ") moving (+1, -1):" + foundhere + '\n');
                    }
                    iPlusOffset--;
                    jPlusOffset++;
                }

                iPlusOffset = i;
                jPlusOffset = j;
                possibleWord = "";

                //southwest
                while (iPlusOffset < puzzle.length && jPlusOffset >= 0)
                {
                    possibleWord = possibleWord.concat(String.valueOf(puzzle[iPlusOffset][jPlusOffset]));

                    String foundhere = searchFor(possibleWord);

                    if (!foundhere.equals(""))
                    {
                        found = getFound().concat("at (" + j + ", " + i + ") moving (-1, +1):" + foundhere + '\n');
                    }
                    iPlusOffset++;
                    jPlusOffset--;
                }

                iPlusOffset = i;
                jPlusOffset = j;
                possibleWord = "";

                //southeast
                while (iPlusOffset < puzzle.length && jPlusOffset < puzzle[0].length)
                {
                    possibleWord = possibleWord.concat(String.valueOf(puzzle[iPlusOffset][jPlusOffset]));

                    String foundhere = searchFor(possibleWord);

                    if (!foundhere.equals(""))
                    {
                        found = getFound().concat("at (" + i + ", " + j + ") moving (+1, +1):" + foundhere + '\n');
                    }
                    iPlusOffset++;
                    jPlusOffset++;
                }
//                System.err.println(i+", "+j);
            }
//                System.err.println(i);
        }

        return getFound();
    }

    private String searchFor(String word)
    {
        if (BST.search(word))
        {
            return word;
        } else
        {
            return "";
        }
    }

    /**
     * @return the found
     */
    public String getFound()
    {
        return found;
    }
}
