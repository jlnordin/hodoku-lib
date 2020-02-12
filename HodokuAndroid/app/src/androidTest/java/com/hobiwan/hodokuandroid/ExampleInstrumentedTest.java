package com.hobiwan.hodokuandroid;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.hobiwan.hodoku.compatibility.*;
import com.hobiwan.hodoku.sudoku.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.hobiwan.hodokuandroid", appContext.getPackageName());

        DifficultyLevel difficultyLevel = new DifficultyLevel(DifficultyType.EXTREME, 10, "test", new Color(android.graphics.Color.RED), new Color(android.graphics.Color.WHITE));
        assertEquals("test", difficultyLevel.getName());
    }
}
