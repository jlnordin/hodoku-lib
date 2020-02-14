/*
 * Copyright (C) 2008-12  Bernhard Hobiger
 *
 * This file is part of HoDoKu.
 *
 * HoDoKu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HoDoKu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HoDoKu. If not, see <http://www.gnu.org/licenses/>.
 */
package com.hobiwan.hodoku.sudoku;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.hobiwan.hodoku.solver.SudokuSolver;
import com.hobiwan.hodoku.viewmodels.SolutionPanelViewModel;
import com.hobiwan.hodoku.viewmodels.SudokuPanelViewModel;

/**
 * Holds the complete GUI state at a given time. The state consists of the
 * following items:<br>
 * {@link SudokuPanelViewModel}:
 * <ul>
 *  <li>{@link SudokuPanelViewModel#sudoku sudoku}: The actual sudoku</li>
 *  <li>{@link SudokuPanelViewModel#solvedSudoku solvedSudoku}: The solved sudoku for displaying invalid values (optional)</li>
 *  <li>{@link SudokuPanelViewModel#undoStack undoStack}/{@link SudokuPanelViewModel#redoStack redoStack}: The undo/redo stack (optional)</li>
 *  <li>{@link SudokuPanelViewModel#step step}: The step that is currently highlighted (may be null if no step is selected)</li>
 *  <li>{@link SudokuPanelViewModel#chainIndex chainIndex}: The chain from step that is currently shown</li>
 *  <li>{@link SudokuPanelViewModel#coloringMap coloringMap}: All colored cells (optional)</li>
 *  <li>{@link SudokuPanelViewModel#coloringCandidateMap coloringCandidateMap}: All colored candidates (optional)</li>
 * </ul>
 * {@link SudokuSolver}:
 * <ul>
 *  <li>{@link SudokuSolver#steps steps}: The current solution</li>
 *  <li>{@link SudokuSolver#anzSteps anzSteps}: The solution summary</li>
 * </ul>
 * {@link SolutionPanelViewModel}:
 * <ul>
 *  <li>{@link SolutionPanelViewModel#titels titels}: The titles of all available solutions</li>
 *  <li>{@link SolutionPanelViewModel#tabSteps tabSteps}: The available solutions</li>
 * </ul>
 *
 * Since this is only data structure that is never saved all attributes are package protected
 * and without getters or setters.<br>
 * Every class referenced above holds a <code>getState()</code> method that retrieves all items from that
 * class.<br>
 * In addition to the attributes described above every GUI state can have a
 * name and a timestamp.
 * 
 * @author hobiwan
 */
public class GuiState {
    /** Debug flag - set to false before releasing! */
    private static final boolean DEBUG = false;
    // items from SudokuPanelViewModel
    private Sudoku2 sudoku = null;
    private Stack<Sudoku2> undoStack = null;
    private Stack<Sudoku2> redoStack = null;
    private SolutionStep step = null;
    private int chainIndex = -1;
    private SortedMap<Integer, Integer> coloringMap = null;
    private SortedMap<Integer, Integer> coloringCandidateMap = null;

    // items from SudokuSolver
    private List<SolutionStep> steps;
    private int[] anzSteps;

    // items from SolutionPanelViewModel
    private List<String> titels;
    private List<List<SolutionStep>> tabSteps;

    // name and timestamp
    private String name;
    private Date timestamp;
    
    // internal fields, are not written by XMLEncoder
    private SudokuPanelViewModel SudokuPanelViewModel;
    private SudokuSolver sudokuSolver;
    private SolutionPanelViewModel SolutionPanelViewModel;

    /**
     * Default constructor, only for XmlEncoder/XmlDecoder.<br>
     */
    public GuiState() {

    }

    /**
     * Initializes a state object. If the parameters are null, {@link #get(boolean)} and
     * {@link #set()} ignore the respective objects.
     * @param SudokuPanelViewModel
     * @param sudokuSolver
     * @param SolutionPanelViewModel
     */
    public GuiState(SudokuPanelViewModel SudokuPanelViewModel, SudokuSolver sudokuSolver, SolutionPanelViewModel SolutionPanelViewModel) {
        initialize(SudokuPanelViewModel, sudokuSolver, SolutionPanelViewModel);
    }

    /**
     * Sets the internal fields for the state. Is used by {@link MainFrameViewModel#loadFromFile(boolean)}
     * (the internal fields cannot be stored, they have no meaning outside the running program).
     * @param SudokuPanelViewModel
     * @param sudokuSolver
     * @param SolutionPanelViewModel
     */
    public final void initialize(SudokuPanelViewModel SudokuPanelViewModel, SudokuSolver sudokuSolver, SolutionPanelViewModel SolutionPanelViewModel) {
        this.SudokuPanelViewModel = SudokuPanelViewModel;
        this.sudokuSolver = sudokuSolver;
        this.SolutionPanelViewModel = SolutionPanelViewModel;
    }

    /**
     * Gets all necessary GUI state information. NULL objectes are ignored.
     *
     * @param copy
     */
    public void get(boolean copy) {
        if (sudokuSolver != null) {
            sudokuSolver.getState(this, copy);
        }
        if (SolutionPanelViewModel != null) {
            SolutionPanelViewModel.getState(this, copy);
        }
        if (SudokuPanelViewModel != null) {
            SudokuPanelViewModel.getState(this, copy);
        }
    }

    /**
     * Sets all necessary GUI state information. NULL objectes are ignored.<br>
     */
    public void set() {
        if (sudokuSolver != null) {
            sudokuSolver.setState(this);
        }
        if (SolutionPanelViewModel != null) {
            SolutionPanelViewModel.setState(this);
        }
        if (SudokuPanelViewModel != null) {
            SudokuPanelViewModel.setState(this);
        }
    }
    
    /**
     * {@link #anzSteps} can become out of sync, when
     * a sudoku file is loaded and the step order has been
     * changed (it is assumed, that the order in {@link #anzSteps}
     * is the same as in {@link Options#solverSteps}).<br><br>
     * 
     * The method assumes, that {@link #anzSteps} and {@link #steps}
     * have already been set.
     */
    public void resetAnzSteps() {
        // better save than sorry...
        if (steps == null || anzSteps == null) {
            Logger.getLogger(GuiState.class.getName()).log(Level.SEVERE, "Trying to reset anzSteps, but attributes have not been set ({0}/{1})!", new Object[] { steps, anzSteps});
            return;
        }
        if (DEBUG) {
            System.out.println("resetAnzSteps() start (" + steps.size() + ")");
        }
        // now start
        // adjust anzSteps if necessary, else reset it
        StepConfig[] cfg = Options.getInstance().solverSteps;
        int length = cfg.length;
        if (anzSteps.length != length) {
            anzSteps = new int[length];
        } else {
            for (int i = 0; i < anzSteps.length; i++) {
                anzSteps[i] = 0;
            }
        }
        // now iterate over steps and increment the according entry in anzSteps
        for (SolutionStep act : steps) {
            // get the correct StepConfig (has to be done this way,
            // because some SolutionTypes dont have StepConfigs!
            StepConfig config = act.getType().getStepConfig();
            if (config != null) {
                // now get the correct index for the StepConfig
                int index = 0;
                for (index = 0; index < cfg.length; index++) {
                    if (cfg[index] == config) {
                        break;
                    }
                }
                if (index < cfg.length) {
                    anzSteps[index]++;
                } else {
                    if (DEBUG) {
                        System.out.println("resetAnzSteps(): NO config found for " + act.getType().getStepName() + " (1)");
                    }
                }
            } else {
                if (DEBUG) {
                    System.out.println("resetAnzSteps(): NO config found for " + act.getType().getStepName() + " (1)");
                }
            }
        }
    }

    /**
     * @return the sudoku
     */
    public Sudoku2 getSudoku() {
        return sudoku;
    }

    /**
     * @param sudoku the sudoku to set
     */
    public void setSudoku(Sudoku2 sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * @return the undoStack
     */
    public Stack<Sudoku2> getUndoStack() {
        return undoStack;
    }

    /**
     * @param undoStack the undoStack to set
     */
    public void setUndoStack(Stack<Sudoku2> undoStack) {
        this.undoStack = undoStack;
    }

    /**
     * @return the redoStack
     */
    public Stack<Sudoku2> getRedoStack() {
        return redoStack;
    }

    /**
     * @param redoStack the redoStack to set
     */
    public void setRedoStack(Stack<Sudoku2> redoStack) {
        this.redoStack = redoStack;
    }

    /**
     * @return the step
     */
    public SolutionStep getStep() {
        return step;
    }

    /**
     * @param step the step to set
     */
    public void setStep(SolutionStep step) {
        this.step = step;
    }

    /**
     * @return the chainIndex
     */
    public int getChainIndex() {
        return chainIndex;
    }

    /**
     * @param chainIndex the chainIndex to set
     */
    public void setChainIndex(int chainIndex) {
        this.chainIndex = chainIndex;
    }

    /**
     * @return the coloringMap
     */
    public SortedMap<Integer, Integer> getColoringMap() {
        return coloringMap;
    }

    /**
     * @param coloringMap the coloringMap to set
     */
    public void setColoringMap(SortedMap<Integer, Integer> coloringMap) {
        this.coloringMap = coloringMap;
    }

    /**
     * @return the coloringCandidateMap
     */
    public SortedMap<Integer, Integer> getColoringCandidateMap() {
        return coloringCandidateMap;
    }

    /**
     * @param coloringCandidateMap the coloringCandidateMap to set
     */
    public void setColoringCandidateMap(SortedMap<Integer, Integer> coloringCandidateMap) {
        this.coloringCandidateMap = coloringCandidateMap;
    }

    /**
     * @return the steps
     */
    public List<SolutionStep> getSteps() {
        // items from SudokuSolver
        return steps;
    }

    /**
     * @param steps the steps to set
     */
    public void setSteps(List<SolutionStep> steps) {
        this.steps = steps;
    }

    /**
     * @return the anzSteps
     */
    public int[] getAnzSteps() {
        return anzSteps;
    }

    /**
     * @param anzSteps the anzSteps to set
     */
    public void setAnzSteps(int[] anzSteps) {
        this.anzSteps = anzSteps;
    }

    /**
     * @return the titels
     */
    public // items from SolutionPanelViewModel
    List<String> getTitels() {
        return titels;
    }

    /**
     * @param titels the titels to set
     */
    public void setTitels(List<String> titels) {
        this.titels = titels;
    }

    /**
     * @return the tabSteps
     */
    public List<List<SolutionStep>> getTabSteps() {
        return tabSteps;
    }

    /**
     * @param tabSteps the tabSteps to set
     */
    public void setTabSteps(List<List<SolutionStep>> tabSteps) {
        if (steps == null || steps.isEmpty()) {
            if (tabSteps.get(0) != null) {
                steps = new ArrayList<SolutionStep>(tabSteps.get(0));
            } else {
                steps = new ArrayList<SolutionStep>();
            }
        }
        this.tabSteps = tabSteps;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
