package com.hobiwan.hodoku.viewmodels;

import com.hobiwan.hodoku.solver.SudokuSolver;

public class SolverProgressDialogViewModel {

    public SolverProgressDialogViewModel(Object parent, boolean modal, SudokuSolver solver) {
    }

    public Thread getThread() {
        return Thread.currentThread();
    }

    public void setVisible(boolean visible) {
    }

    public boolean isVisible() {
        return true;
    }

    public boolean isSolved() {
        return true;
    }

    public void initializeProgressState(int maxProgress) {
    }

    public void setProgressState(int unsolvedCells, int unsolvedCandidates) {
    }
}