package treepuzzle;

/**
COPYRIGHT (C) 2012 Andrew Tilisky. All Rights Reserved.
 * This object is instantiated by the driver class and is the user's
 * starting point.  Their input will trigger the reading and hashing
 * in the solver object.
@author Andrew Tilisky
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Andrew Tilisky
 */
public class PuzzleSolverGUI extends JFrame
{
    private SolverListener actionList;
    private ButtonGroup hashTypesGroup;
    private File dict;
    private JScrollPane listScroller;
    private File puzz;
    private JPanel listPane;
    private JPanel puzzPanel;
    private JPanel outcomePane;
    private char[][] puzzMatrix;
    private ArrayList<String> wordList;
    private PuzzleSolver solver;

    /*
     * if the screen's resized after a dictionary's been read in its scrollpane's
     * scaled 
     */
    @Override
    public void validate()
    {
//        System.out.println(getWidth() + " " + getHeight());
//        System.out.println(puzzPanel.getWidth() + " " + puzzPanel.getHeight());
//        System.out.println(outcomePane.getWidth() + " " + outcomePane.getHeight());
        if (listScroller != null && listPane != null)
        {
            listScroller.setPreferredSize(new Dimension(160, getHeight() - 150));
            listPane.add(listScroller);
        }

        super.validate();
    }

    /*this sets up the main app screen that will persist until the application 
     * is terminated.  
     */
    PuzzleSolverGUI()
    {
        JPanel controlPane = new JPanel(); // pane for controls

        JMenuBar menuBar = new JMenuBar(); // bar
        JMenu fileMenu = new JMenu("File"); //file options (new, save, quit)
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem newDict = new JMenuItem("New Dictionary");
        newDict.setMnemonic(KeyEvent.VK_D);
        JMenuItem newPuzz = new JMenuItem("New Puzzle");
        newPuzz.setMnemonic(KeyEvent.VK_P);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_E);

        // add files options to menu
        fileMenu.add(newDict);
        fileMenu.add(newPuzz);
        fileMenu.add(exitItem);

        JButton goButton = new JButton("Search");

        goButton.setMnemonic(KeyEvent.VK_S);

        controlPane.add(goButton);

        actionList = new SolverListener(this);

        //component listener registration
        newDict.addActionListener(actionList);
        newPuzz.addActionListener(actionList);
        exitItem.addActionListener(actionList);
        goButton.addActionListener(actionList);

        controlPane.setLayout(new FlowLayout()); // controls added sequentially
        setJMenuBar(menuBar); //menuBar for the app
        menuBar.add(fileMenu); // add file option button for bar

        add(controlPane, BorderLayout.NORTH);
        listPane = new JPanel();
        puzzPanel = new JPanel();
        outcomePane = new JPanel();
    }

    /*
     * scans the first line of the puzzle to determine the size and instantiates
     * the char matrix.  scans the remainder of the lines.  everytime this method's
     * recalled the central panel's cleared and redrawn with a nested loop
     */
    public void drawPuzzle()
    {
        Scanner scan;

        try
        {
            scan = new Scanner(puzz);
            String firstLine = scan.nextLine();
            int dimen = firstLine.replaceAll(" ", "").length();

            puzzMatrix = new char[dimen][dimen];

            puzzMatrix[0] = firstLine.replaceAll(" ", "").toUpperCase().toCharArray();

            short lindex = 1;

            while (scan.hasNextLine())
            {
                char[] line;

                try
                {
                    line = scan.nextLine().replaceAll(" ", "").toUpperCase().toCharArray();

                    puzzMatrix[lindex] = line;

                } catch (ArrayIndexOutOfBoundsException t)
                {

                    Logger.getLogger(SolverListener.class.getName()).log(Level.SEVERE, null, t);
                }
                lindex++;
            }

            puzzPanel.removeAll();
            puzzPanel.setLayout(new GridLayout(dimen, dimen));

            for (char[] row : puzzMatrix)
            {
                for (char letter : row)
                {
                    JLabel puzzChar = new JLabel(Character.toString(letter));
                    puzzChar.setHorizontalAlignment(SwingConstants.CENTER);

                    puzzPanel.add(puzzChar);
//                    System.out.print(letter);
                }
//                System.out.println("");
            }

            add(puzzPanel, BorderLayout.CENTER);
            revalidate();
            repaint();

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(SolverListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex)
        {
            Logger.getLogger(SolverListener.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println("");
    }

    /*
     * dictionary is read in by line.  in one operation the arraylist in passed
     * to the new JList object.  that object's then passed to the scroller object's constructor
     * and it has its specifics set.  the listpane is cleared an each call and the dictionary's
     * redrawn.  it's also resized in the validate() override toward the top of the class declaration
     */
    public void drawDict()
    {
        Scanner scan;

        try
        {
            scan = new Scanner(dict);
            wordList = new ArrayList<String>();

            while (scan.hasNextLine())
            {
                wordList.add(scan.nextLine());
            }

            JList dictDisplay = new JList(wordList.toArray());
            dictDisplay.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            dictDisplay.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            dictDisplay.setVisibleRowCount(-1);
            listScroller = new JScrollPane(dictDisplay);
            listScroller.setPreferredSize(new Dimension(160, getHeight() - 150));
            listScroller.setAlignmentX(LEFT_ALIGNMENT);
            listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
            JLabel label = new JLabel("Dictionary");
            label.setLabelFor(dictDisplay);
            listPane.add(label);
            listPane.add(Box.createRigidArea(new Dimension(0, 5)));
            listPane.add(listScroller);
            listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            listPane.removeAll();
            add(listPane, BorderLayout.EAST);

            revalidate();
            repaint();

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(SolverListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex)
        {
            Logger.getLogger(SolverListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * this is called if the puzzle search operation succeeds.  a text area is used not 
     * a JList but regardless it's added to its new scrollpane and then to the southern border.
     * the string's derived from within the while loops in the Solver's solve() method.
     */
    public void drawOutcome(String found, String op)
    {
        outcomePane.removeAll();
        try
        {
            JTextArea ta = null;
            if (op != null && op.equals("building"))
            {
                ta = new JTextArea("Once you've tried to load both the puzzle and dictionary wait for confirmation to begin the search.", 5, 50);
            } else if (op != null && op.equals("built"))
            {
                ta = new JTextArea("You may now search the puzzle", 5, 50);
            } else
            {
                ta = new JTextArea(found, 5, 50);
            }
            ta.setLineWrap(true);
            JScrollPane sbrText = new JScrollPane(ta);
            sbrText.setPreferredSize(new Dimension(160, 160));
            sbrText.setAlignmentX(LEFT_ALIGNMENT);

            outcomePane.setLayout(new BoxLayout(outcomePane, BoxLayout.PAGE_AXIS));
//            outcomePane.add(Box.createRigidArea(new Dimension(0, 5)));
            outcomePane.add(sbrText);
            outcomePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            add(outcomePane, BorderLayout.SOUTH);

            validate();
            repaint();
//            pack();

        } catch (NullPointerException ex)
        {
            Logger.getLogger(SolverListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param solver the solver to set
     */
    public void setSolver()
    {
        if (puzzMatrix != null && wordList != null)
        {
            Date time = new Date();

            long startTime = time.getTime();

            try
            {
                solver = new PuzzleSolver(puzzMatrix, wordList);
                drawOutcome(null, "built");
            } catch (NullPointerException t)
            {
                Logger.getLogger(SolverListener.class.getName()).log(Level.SEVERE, null, t);
            }

            time = new Date();
            long elapsedtime = time.getTime() - startTime;

            System.out.println("Elapsed building time: " + elapsedtime + " ms");
            System.out.println("\t\t\t" + (time.getTime() - startTime) / 1000 + " s");
            System.out.println("\t\t\t" + (time.getTime() - startTime) / 60000 + " m");
            System.out.println("You may now click search");
        }
    }

    /**
     * @param solver the solver to set
     */
    public void solve()
    {
        System.out.println(solver.solve());
        drawOutcome(solver.getFound(), null);
    }

    /**
     * @return the hashTypesGroup
     */
    public ButtonGroup getHashTypesGroup()
    {
        return hashTypesGroup;
    }

    /**
     * @param dict the dict to set
     */
    public void setDict(File dict)
    {
        this.dict = dict;
    }

    /**
     * @param puzz the puzz to set
     */
    public void setPuzz(File puzz)
    {
        this.puzz = puzz;
    }
}
