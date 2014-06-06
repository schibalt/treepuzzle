package treepuzzle;

/**
COPYRIGHT (C) 2012 Andrew Tilisky. All Rights Reserved.
 * This object is instantiated by the driver class and is the user's
 * starting point.  Their input will trigger the reading and hashing
 * in the solver object.
@author Andrew Tilisky
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/*
 * This listener is registered with everything in the GUI and uses a system
 * of conditions to trigger the correct functions.
 */
public class SolverListener implements ActionListener
{
    private PuzzleSolverGUI GUI;

    SolverListener(PuzzleSolverGUI gUI)
    {
        this.GUI = gUI;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
//        System.out.println(e.getActionCommand());

        if (e.getActionCommand().equals("New Dictionary"))
        {
            final JFileChooser dictChooser = new JFileChooser("dictionary");
            dictChooser.showOpenDialog(dictChooser);
            if (dictChooser.getSelectedFile() != null)
            {
                GUI.setDict(dictChooser.getSelectedFile());
                GUI.drawDict();
                GUI.drawOutcome(null, "building");
                GUI.setSolver();
            }
        } else if (e.getActionCommand().equals("New Puzzle"))
        {
            final JFileChooser puzzChooser = new JFileChooser("puzzle");
            puzzChooser.showOpenDialog(puzzChooser);

            if (puzzChooser.getSelectedFile() != null)
            {
                GUI.setPuzz(puzzChooser.getSelectedFile());
                GUI.drawPuzzle();
                GUI.drawOutcome(null, "building");
                GUI.setSolver();
            }

        } else if (e.getActionCommand().equals("Exit"))
        {
            System.exit(1);

        } else if (e.getActionCommand().equals("Search"))
        { // filter
            Date time = new Date();

            long startTime = time.getTime();

            try
            {
                GUI.solve();
            } catch (NullPointerException t)
            {
                Logger.getLogger(SolverListener.class.getName()).log(Level.SEVERE, null, t);
            }

            time = new Date();
            long elapsedtime = time.getTime() - startTime;

            System.out.println("Elapsed search time: " + elapsedtime + " ms");
            System.out.println("\t\t\t" + (time.getTime() - startTime) / 1000 + " s");
            System.out.println("\t\t\t" + (time.getTime() - startTime) / 60000 + " m");
        }
    }
} // SolverListener