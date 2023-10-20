import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.Assert;
import org.junit.Before;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Summer 2022</p>
 *
 * @author Purdue CS
 * @version June 13, 2022
 */
public class RunLocalTests {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Summer 2022</p>
     *
     * @author Purdue CS
     * @version June 13, 2022
     */
    public static class TestCase {
        private static final String WELCOME = "Hello! Welcome to Homes4Sale!";
        private static final String signIn = "1. Log In\n2. Sign Up";
        private static final String invalidSignIn = "Please enter a valid number!";
        private static final String invalidRole = "Please enter a valid role!";
        private static final String enterUser = "Please enter your email address: ";
        private static final String enterPass = "Please enter a password: ";
        private static final String enterRole = "Please enter a role: ";
        private static final String accExists = "Uh oh! This account already exists!";
        private static final String accNotExist = "Uh oh! Either this account doesn\'t exist or you gave the wrong credentials!";
        private static final String accCreated = "Account created successfully! Please log in again.";

        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test (timeout = 1000)
        public void testExpectedTwo() {
            String input = "2" + System.lineSeparator() + "testUser" + System.lineSeparator() +
                    "testPass" + System.lineSeparator() + "seller" + System.lineSeparator() +
                    "2" + System.lineSeparator() + "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "SuperCave" + System.lineSeparator() + "2" + System.lineSeparator() +
                    "1" + System.lineSeparator() + "3 bedrooms 2 bathrooms" + System.lineSeparator() +
                    "zara" + System.lineSeparator() + "200,000 dollars" + System.lineSeparator() +
                    "1" + System.lineSeparator();

            String expected = WELCOME + System.lineSeparator() + signIn + System.lineSeparator() +
                    enterUser + System.lineSeparator() + enterPass + System.lineSeparator() +
                    enterRole +System.lineSeparator() + accCreated + System.lineSeparator() +
                    "Please choose what you would like to do: " + System.lineSeparator() +
                    "1. View the Marketplace" + System.lineSeparator() + "2. Modify Shop" +
                    System.lineSeparator() + "3. View Store Statistics" + System.lineSeparator() +
                    "What would you like to do today?" + System.lineSeparator() + "1. Create a new item" +
                    System.lineSeparator() + "2. Modify an existing item" + System.lineSeparator() +
                    "3. Delete an item" + System.lineSeparator() + "Would you like to add the property by hand or upload a file?"
                    + System.lineSeparator() +"1: Upload a file"+ System.lineSeparator() +
                    "2: Add by hand"+ System.lineSeparator() + "What is the name of the property?"
                    + System.lineSeparator() + "Where is the property located?" + System.lineSeparator() +
                    "1. Beach property" + System.lineSeparator() + "2. Mountain property" + System.lineSeparator() +
                    "3. City property" + System.lineSeparator() + "4. Town property" + System.lineSeparator() +
                    "What type of property is it?" + System.lineSeparator() + "1. House" + System.lineSeparator() +
                    "2. Apartment" + System.lineSeparator() + "What is a description of the house?" + System.lineSeparator() +
                    "What store is this being sold for?" + System.lineSeparator() + "What is the price of the house?"
                    + System.lineSeparator() + "How many of these houses are you selling?";


            String output = getOutput();
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");

        }
        @Test (timeout = 1000)
        public void testExpectedThree() {
            String input = "2" + System.lineSeparator() + "testUser" + System.lineSeparator() +
                    "testPass" + System.lineSeparator() + "buyer" + System.lineSeparator() +
                    "3" + System.lineSeparator() + "2" + System.lineSeparator();

            String expected = WELCOME + System.lineSeparator() + signIn + System.lineSeparator() +
                    enterUser + System.lineSeparator() + enterPass + System.lineSeparator() +
                    enterRole +System.lineSeparator() + accCreated + System.lineSeparator() +
                    "Please choose what you would like to do: " + System.lineSeparator() +
                    "1. View the Marketplace" + System.lineSeparator() + "2. Search for specific products" +
                    System.lineSeparator() + "3. Sort the market" + System.lineSeparator() +
                    "What would you like to sort the market by?" + System.lineSeparator() + "1: cost" +
                    System.lineSeparator() + "2: name" + System.lineSeparator() +
                    "3: location" + System.lineSeparator();

            String output = getOutput();
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");

        }
    }
}
