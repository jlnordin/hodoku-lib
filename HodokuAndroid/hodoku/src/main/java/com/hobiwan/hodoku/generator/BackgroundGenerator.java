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

package com.hobiwan.hodoku.generator;

import java.awt.EventQueue;
import java.util.List;
import com.hobiwan.hodoku.solver.SudokuSolver;
import com.hobiwan.hodoku.solver.SudokuSolverFactory;
import com.hobiwan.hodoku.sudoku.ClipboardMode;
import com.hobiwan.hodoku.sudoku.DifficultyLevel;
import com.hobiwan.hodoku.sudoku.GameMode;
import com.hobiwan.hodoku.sudoku.Options;
import com.hobiwan.hodoku.sudoku.SolutionStep;
import com.hobiwan.hodoku.sudoku.Sudoku2;

/**
 * A BackgroundGenerator generates sudokus with a given {@link DifficultyLevel} 
 * and for a given {@link GameMode}. An instance of this class should be contained
 * within a {@link BackgroundGeneratorThread}. It will deliver the generated puzzle
 * or <code>null</code>, if no puzzle could be found.
 * 
 * @author hobiwan
 */
public class BackgroundGenerator {
    /** Maximal number of tries, when called from a {@link BackgroundGeneratorThread}. */
    private static final int MAX_TRIES = 20000;
    /** Current number of tries */
    private int anz = 0;
    /**
     * Generates a new instance.
     */
    public BackgroundGenerator() {
        // nothing to do!
    }
    
    /**
     * Generates a new sudoku: If <code>dlg</code> is <code>null</code>, the progress is
     * reported and the dialog is closed, when puzzle has been found. The
     * current thread is checked for interruptions.<br>
     * If <code>dlg</code> is not <code>null</code>, the creation process goes on
     * until a puzzle has been found or until {@link #MAX_TRIES} tries have been
     * run.
     * 
     * @param level
     * @param mode
     * @return 
     */
    public Sudoku2 generate(DifficultyLevel level, GameMode mode) {
        long actMillis = System.currentTimeMillis();
        Sudoku2 sudoku = null;
        SudokuGenerator creator = null; 
        SudokuSolver solver = null;
        anz = 0;

        // get any instance
        solver = SudokuSolverFactory.getInstance();
        creator = SudokuGeneratorFactory.getInstance();

        while (!Thread.currentThread().isInterrupted()) {
            sudoku = creator.generateSudoku(true);
            if (sudoku == null) {
                // impossible to create sudoku due to an invalid pattern
                return null;
            }
            Sudoku2 solvedSudoku = sudoku.clone();
            boolean ok = solver.solve(level, solvedSudoku, true, null, false, 
                    Options.getInstance().solverSteps, mode);
            boolean containsTrainingStep = true;
            if (mode != GameMode.PLAYING) {
                containsTrainingStep = false;
                List<SolutionStep> steps = solver.getSteps();
                for (SolutionStep step : steps) {
                    if (step.getType().getStepConfig().isEnabledTraining()) {
                        containsTrainingStep = true;
                        break;
                    }
                }
            }
            if (ok && containsTrainingStep && 
                    (solvedSudoku.getLevel().getOrdinal() == level.getOrdinal()
                    || mode == GameMode.LEARNING)) {
                sudoku.setLevel(solvedSudoku.getLevel());
                sudoku.setScore(solvedSudoku.getScore());
                break;
            }
            anz += 1;

            if (anz > MAX_TRIES) {
                // give up...
                sudoku = null;
                break;
            }
        }

        // give everything back
        SudokuGeneratorFactory.giveBack(creator);
        SudokuSolverFactory.giveBack(solver);
        return sudoku;
    }
}
