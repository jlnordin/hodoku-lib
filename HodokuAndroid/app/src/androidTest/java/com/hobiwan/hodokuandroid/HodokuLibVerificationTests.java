package com.hobiwan.hodokuandroid;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.hobiwan.hodoku.compatibility.*;
import com.hobiwan.hodoku.generator.*;
import com.hobiwan.hodoku.solver.*;
import com.hobiwan.hodoku.sudoku.*;

@RunWith(AndroidJUnit4.class)
public class HodokuLibVerificationTests {

    @Test
    public void instantiateDifficultyType() {
        DifficultyLevel difficultyLevel = new DifficultyLevel(DifficultyType.EXTREME, 10, "test", Color.RED, Color.WHITE);
        assertEquals("test", difficultyLevel.getName());
    }

    @Test
    public void basicGenerator() {
        SudokuGenerator generator = SudokuGeneratorFactory.getDefaultGeneratorInstance();
        Sudoku2 newSudoku = generator.generateSudoku(true);
        assertTrue( newSudoku.getSolution() == generator.getSolution());
    }

    @Test
    public void basicSolver() {

    }
}
