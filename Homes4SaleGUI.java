//final copy
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;

public class Homes4SaleGUI {
    private static int parsed = 0;
    private static String port;
    private static Socket socket;
    private static int choice = 0;
    private static String[] buttons;
    private static int total;

    public static void main(String[] args) throws IOException {
        // gets the port number to connect to
        do {
            port = JOptionPane.showInputDialog(null, "Enter Port Number", //1234
                    "Homes4Sale", JOptionPane.QUESTION_MESSAGE);

            if ((port == null) || (port.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Invalid Port number!", "Homes4Sale",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    parsed = Integer.parseInt(port);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid Port Number!!",
                            "Homes4Sale", JOptionPane.ERROR_MESSAGE);
                }
            }
        } while ((port == null) || (port.isEmpty()) || (parsed == 0));

        // creates the socket to connect to
        try {
            socket = new Socket("localhost", parsed);
            Homes4SaleServer.connectionMade = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not establish connection! Aborting...", "Homes4Sale",
                    JOptionPane.ERROR_MESSAGE);
        }

        // starts the log in process
        if (Homes4SaleServer.connectionMade) {
            JOptionPane.showMessageDialog(null, "Connection established! Welcome to Homes4Sale!",
                    "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
            logInChoice();
        }

        // starts the account change process
        if (Homes4SaleServer.loggedIn) {
            accountInfoChange();
        }

        // actually puts you at the website
        if (Homes4SaleServer.atWebsite) {
            if (Homes4SaleServer.logRole.equals("seller")) {
                sellerActions();
            } else {
                if (Homes4SaleServer.readMarket() && Homes4SaleServer.logRole.equals("buyer")) {
                    buyerActions();
                }
            }
        }

        // closes the socket and the program as a whole
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // if you are logging in
    public static void logInChoice() {
        buttons = new String[2];
        buttons[0] = "Log In";
        buttons[1] = "Sign Up";
        while (true) {
            choice = JOptionPane.showOptionDialog(null, "What would you like to do?",
                    "Homes4Sale", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    buttons, buttons[0]);
            if (choice == JOptionPane.YES_OPTION) {
                Homes4SaleServer.logIn();
                break;
            } else if (choice == JOptionPane.NO_OPTION) {
                Homes4SaleServer.signUp();
            } else {
                JOptionPane.showMessageDialog(null, "Goodbye! Thank you for using Homes4Sale!",
                        "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }
   // if you are changing something from an existing account
    public static void accountInfoChange() throws IOException {
        buttons = new String[3];
        buttons[0] = "Take to Website";
        buttons[1] = "Change Password and Take to Website";
        buttons[2] = "Delete Account";
        choice = JOptionPane.showOptionDialog(null, "Please select an account option", "Homes4Sale",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
        if (choice == 0) {
            JOptionPane.showMessageDialog(null, "Okay! Thank you for using Homes4Sale!",
                    "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
            Homes4SaleServer.atWebsite = true;
        } else if (choice == 1) {
            // change password
            Homes4SaleServer.changePassword();
        } else if (choice == 2) {
            //delete account
            Homes4SaleServer.deleteAcc();
        } else {
            JOptionPane.showMessageDialog(null, "Goodbye! Thank you for using Homes4Sale!",
                    "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // if you log in as a seller
    public static void sellerActions() throws IOException {
        Homes4SaleServer.checkMarket();
        boolean continues = true;
        while (continues) {
            // choices for seller
            buttons = new String[3];
            buttons[0] = "1. View the Marketplace";
            buttons[1] = "2. Modify Shop";
            buttons[2] = "3. View Store Statistics";
            Integer choices = JOptionPane.showOptionDialog(null,
                    "Please choose what you would like to do: ", "Homes4Sale",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
            if (choices == 0) {
                //view marketplace
                Homes4SaleServer.viewMarketplace();
            } else if (choices == 1) {
                boolean repeat = true;
                while (repeat) {
                    //modify shop
                    buttons = new String[4];
                    buttons[0] = "1. Create a new item";
                    buttons[1] = "2. Modify an existing item";
                    buttons[2] = "3. Delete an item";
                    buttons[3] = "4. Quit program";
                    choices = JOptionPane.showOptionDialog(null,
                            "What would you like to change today?", "Homes4Sale",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
                    if (choices == 0) {
                        boolean modify = true;
                        while (modify) {
                            // choices on how to create new items
                            buttons = new String[2];
                            buttons[0] = "1: Upload a file";
                            buttons[1] = "2: Add by hand";
                            choices = JOptionPane.showOptionDialog(null,
                                    "Would you like to add the property by hand or upload a file?", "Homes4Sale",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
                            if (choices != 0 || choices != 1) {
                                modify = false;
                            }
                        }
                        if (choices == 0) {
                            //if uploading a file
                            Homes4SaleServer.upload();
                        } else {
                            // questions on what type of property it is
                            ArrayList<String> info = new ArrayList<>();
                            buttons = new String[4];
                            buttons[0] = "1. Beach property";
                            buttons[1] = "2. Mountain property";
                            buttons[2] = "3. City property";
                            buttons[3] = "4. Town property";
                            while (true) {
                                choices = JOptionPane.showOptionDialog(null,
                                        "Where is the property located?", "Homes4Sale",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
                                //location
                                if (choices == 0) {
                                    info.add("Beach Property");
                                } else if (choices == 1) {
                                    info.add("Mountain Property");
                                } else if (choices == 2) {
                                    info.add("City Property");
                                } else if (choices == 3) {
                                    info.add("Town Property");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid Location!",
                                            "Homes4Sale", JOptionPane.ERROR_MESSAGE);
                                }

                                if (choices == 0 || choices == 1 || choices == 2 || choices == 3) {
                                    break;
                                }
                            }
                            //house or apt
                            buttons = new String[2];
                            buttons[0] = "1. House";
                            buttons[1] = "2. Apartment";
                            while (true) {
                                choices = JOptionPane.showOptionDialog(null,
                                        "What type of property is it?", "Homes4Sale",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
                                if (choices == 0) {
                                    info.add("House");
                                } else if (choices == 1) {
                                    info.add("Apartment");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid Property Type!",
                                            "Homes4Sale", JOptionPane.ERROR_MESSAGE);
                                }

                                if (choices == 0 || choices == 1) {
                                    break;
                                }
                            }
                            Homes4SaleServer.manual(info);
                            JOptionPane.showMessageDialog(null, "The Listing was Added!",
                                    "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if (choices == 1) {
                        //for this we are asking what changes they want to make first
                        boolean go = true;
                        boolean repeats = true;
                        while (repeats) {
                            while (go) {
                                //for changes
                                buttons = new String[7];
                                buttons[0] = "1. Name of House";
                                buttons[1] = "2. Location";
                                buttons[2] = "3. Type of House";
                                buttons[3] = "4. House description";
                                buttons[4] = "5. Price";
                                buttons[5] = "6. Store";
                                buttons[6] = "7. Quantity";
                                choices = JOptionPane.showOptionDialog(null,
                                        "What would you like to change today?", "Homes4Sale",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
                                if (choices == 0 || choices == 1 ||
                                        choices == 2 || choices == 3 ||
                                        choices == 4 || choices == 5 ||
                                        choices == 6) {
                                    go = false;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid Input!",
                                            "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
                                }
                                Homes4SaleServer.changes(choices);
                                buttons = new String[2];
                                buttons[0] = "Yes";
                                buttons[1] = "No";
                                int choicess = JOptionPane.showOptionDialog(null,
                                        "Would you like to make more changes?", "Homes4Sale",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
                                if (choicess == 1) {
                                    repeats = false;
                                    go = false;
                                } else {
                                    repeats = true;
                                    go = true;
                                    Homes4SaleServer.checkMarket();
                                }
                            }
                        }
                        // to delete things
                    } else if (choices == 2) {
                        boolean modify = true;
                        while (modify) {
                            //call function to delete
                            Homes4SaleServer.delete();
                            buttons = new String[2];
                            buttons[0] = "Yes";
                            buttons[1] = "No";
                            choices = JOptionPane.showOptionDialog(null,
                                    "Would you like to delete anything else?",
                                    "Homes4Sale", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                    buttons, buttons[0]);
                            if (choices == JOptionPane.NO_OPTION) {
                                repeat = false;
                                modify = false;
                            }
                        }
                        //exit
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Goodbye! Thank you for using Homes4Sale!",
                                "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            } else if (choices == 2) { //view product information
                boolean modify = true;
                while (modify) {
                    //view statistics
                    buttons = new String[4];
                    buttons[0] = "The number of sales";
                    buttons[1] = "The revenue";
                    buttons[2] = "The names of customers";
                    buttons[3] = "Exit";
                    choices = JOptionPane.showOptionDialog(null,
                            "What would you like to know about your products?", "Homes4Sale",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
                    Homes4SaleServer.information();
                    if (choices == 0) {
                        Homes4SaleServer.numSales();
                    } else if (choices == 1) {
                        Homes4SaleServer.revenue();
                    } else if (choices == 2) {
                        Homes4SaleServer.custNames();
                    } else {
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Goodbye! Thank you for using Homes4Sale!",
                        "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }

    }
    //if you are buyer
    public static void buyerActions () throws IOException {
        Homes4SaleServer.checkMarket();
        boolean continues = true;
        while (continues) {
            Homes4SaleServer.readMarket();
            buttons = new String[4];
            //buyer options
            buttons[0] = "1. View the Market and Shop";
            buttons[1] = "2. Search for specific products";
            buttons[2] = "3. Sort the market";
            buttons[3] = "4. Quit program";
            Integer choices = JOptionPane.showOptionDialog(null,
                    "What would you like to change today?", "Homes4Sale",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
            if (choices == 0) {
                //get different properties
                Homes4SaleServer.viewMarket();
                int choice = (JOptionPane.showConfirmDialog(null,
                        "Now that you have all the information on this product do you want to buy it?",
                        "Homes4Sale", JOptionPane.YES_NO_OPTION));

                if (choice == JOptionPane.YES_OPTION) {
                    // if they are going to buy property
                    Homes4SaleServer.buy();
                    if (!(Homes4SaleServer.readMarket())) {
                        continues = false;
                    }
                } else {
                    choice = (JOptionPane.showConfirmDialog(null,
                            "Would you like to stay on the website?",
                            "Homes4Sale", JOptionPane.YES_NO_OPTION));
                    // to stay on page
                    if (choice == JOptionPane.NO_OPTION) {
                        // leave page
                        JOptionPane.showMessageDialog(null, "Goodbye!",
                                "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
                        continues = false;
                    }
                }
                // search market
            } else if (choices == 1) {
                Homes4SaleServer.search();
            } else if (choices == 2) {
                // search by cost, name, location
                buttons = new String[3];
                buttons[0] = "1: Cost";
                buttons[1] = "2: Name";
                buttons[2] = "3: Location";
                Integer choice = JOptionPane.showOptionDialog(null,
                        "What would you like to sort the market by?", "Homes4Sale",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
                if (choice == 0) {
                    //call cost sort
                    Homes4SaleServer.costSearch();
                } else if (choice == 1) {
                    //call name sort
                    Homes4SaleServer.nameSearch();
                } else {
                    //call location sort
                    Homes4SaleServer.locationSearch();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Goodbye!",
                        "Thank you for using Homes4Sales", JOptionPane.INFORMATION_MESSAGE);
                continues = false;
            }
        }
    }
}
