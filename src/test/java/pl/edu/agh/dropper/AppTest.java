package pl.edu.agh.dropper;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testHelp() {
        App.main(new String[]{"-h"});
        boolean helpFound = StringUtils.containsIgnoreCase(outContent.toString(), "help");
        assertTrue(helpFound);
    }
}
