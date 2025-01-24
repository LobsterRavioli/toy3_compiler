package test.java.compiler;

import main.java.compiler.Lexer;
import main.java.compiler.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

public class ParserTest {

    private static final String SRC_TEST_RESOURCES = "src/test/resources/";

    private void parseFile(String fileName) {
        String filePath = SRC_TEST_RESOURCES + fileName;
        try (Reader fileReader = new BufferedReader(new FileReader(filePath))) {
            Parser parser = new Parser(new Lexer(fileReader));
            Object result = parser.parse(); // Esegui il parsing
            assertNotNull(result, "Parsing result should not be null for file: " + filePath);
        } catch (Exception e) {
            fail("Error while parsing file '" + filePath + "': " + e.getMessage()) ;
        }
    }

    @Test
    public void
    basicProgram() {
        parseFile("basic_program.txt");
    }
    @Test
    public void testFile1() {
        parseFile("test_1.txt");
    }

    @Test
    public void testFile2() {
        parseFile("test_2.txt");
    }

    @Test
    public void testFile3() {
        parseFile("test_3.txt");
    }

    @Test
    public void testFile4() {
        parseFile("test_4.txt");
    }

    @Test
    public void testFile5() {
        parseFile("test_5.txt");
    }

    @Test
    public void testFile6() {
        parseFile("test_6.txt");
    }

    @Test
    public void testFile7() {
        parseFile("test_7.txt");
    }

    @Test
    public void testFile8() {
        parseFile("test_8.txt");
    }

    @Test
    public void testFile9() {
        parseFile("test_9.txt");
    }

    @Test
    public void testFile10() {
        parseFile("test_10.txt");
    }


    @Test
    public void testFile12() {
        parseFile("test_12.txt");
    }
    @Test
    public void valid1() {
        parseFile("valid1.txt");
    }
    @Test
    public void valid2() {
        parseFile("valid2.txt");
    }
    @Test
    public void valid3() {
        parseFile("valid3.txt");
    }
    @Test
    public void valid4() {
        parseFile("valid4.txt");
    }
    @Test
    public void test_error_3() {
        parseFile("test_3_error.txt");
    }
    @Test
    public void test_error_4() {
        parseFile("test_4_error.txt");
    }
    @Test
    public void test_error_5() {
        parseFile("test_5_error.txt");
    }
    @Test
    public void test_error_8() {
        parseFile("test_8_error.txt");
    }
    @Test
    public void test_error_11() {
        parseFile("test_11_error.txt");
    }
    @Test
    public void test_error_13() {
        parseFile("test_13_error.txt");
    }

}
