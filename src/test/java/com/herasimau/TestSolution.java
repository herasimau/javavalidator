package com.herasimau;

import level1.task1.Solution;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static org.junit.Assert.assertEquals;

/**
 * Created by herasimau on 04/03/17.
 */
public class TestSolution {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    Class solution;
    @Before
    public void setUpStreams() {
        try {

            String projectLocation = "/home/herasimau/Documents/javarush/src/main/java";
            File root = new File(projectLocation);
            URLClassLoader testClassLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });

            solution = Class.forName("level1.task1.Solution", true, testClassLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void out() {
        try {
            solution.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assertEquals("world", outContent.toString().trim());
    }


}