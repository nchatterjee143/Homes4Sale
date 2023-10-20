import java.util.*;
import java.io.*;

public class Homes4Sale {
    // String comments we are going to use for creating or logging in to the accounts
    private static final String WELCOME = "Hello! Welcome to Homes4Sale!";
    private static final String signIn = "1. Log In\n2. Sign Up";
    private static final String noAccs = "Uh oh! There are no accounts registered! Sorry for the inconvenience...";
    private static final String invalidNum = "Please enter a valid number!";
    private static final String invalidRole = "Please enter a valid role!";
    private static final String enterUser = "Please enter your email address: ";
    private static final String enterPass = "Please enter a password: ";
    private static final String enterRole = "Please enter a role: ";
    private static final String accExists = "Uh oh! This account already exists!";
    private static final String accNotExist = "Uh oh! Either this account doesn\'t exist or you gave the wrong credentials!";
    private static final String accCreated = "Account created successfully! Please log in again.";
    private static final String accOptions = "Before you proceed, would you like to do anything with your account?";
    private static final String accOptionNums = "1. Change Password and Go To Website\n2. Delete Account\n3. Take me to the website";
    private static final String moveOn = "Okay, thanks for choosing Homes4Sale!";
    private static final String enterCurrentPass = "Please enter your current password: ";
    private static final String areYouSure = "Are you sure you want to delete your account?";
    private static final String yesNo = "1. Yes\n2. No";
    private static final String reconsidered = "Okay, thanks for reconsidering!";
    private static final String deleted = "Sorry to see you go. Hope you come back soon...";
    private static final String incorrectPass = "Uh oh! Incorrect password!";
    private static final String enterNewPass = "Enter your new password: ";
    private static final String passCheck = "Uh oh! New password cannot be the same as old password!";
    private static final String passChangeComplete = "Password changed successfully!";


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int signInOption;
        ArrayList<String> lists = new ArrayList<>();
        // declare the strings that will have the account info 
        String email;
        String pass;
        String role = "null";
        boolean noAccsBool = false;
        boolean accCheck = false;
        boolean accDeleted = false;
        String[] userInfo = new String[3];
        boolean loggedIn = false;
        int accOptionChoice;
        int yesOrNo;

        try {
            //create file for the accounts 
            BufferedReader bfr = new BufferedReader(new FileReader(new File("accounts.txt")));
            FileWriter fw = new FileWriter(new File("accounts.txt"), true);
            System.out.println(WELCOME);
            while (true) {
                while (true) {
                    System.out.println(signIn);
                    signInOption = scan.nextInt();
                    scan.nextLine();
                    if (signInOption == 1 || signInOption == 2) {
                        break;
                    } else {
                        System.out.println(invalidNum);
                        signInOption = 0;
                    }
                }
                // code what will happen if the are logging in or creating a new account

                if (signInOption == 1) { // log in option
                    bfr = new BufferedReader(new FileReader(new File("accounts.txt")));
                    int count = 0;
                    while (bfr.readLine() != null) {
                        count++;
                    }
                    bfr.close();
                    BufferedReader bfr2 = new BufferedReader(new FileReader(new File("accounts.txt")));
                    for (int i = 0; i < count; i++) {
                        lists.add(bfr2.readLine());
                    }
                    bfr2.close();
                    while (true) {
                        System.out.print(enterUser);
                        email = scan.next();
                        scan.nextLine();
                        System.out.print(enterPass);
                        pass = scan.nextLine();
                        if (lists.size() == 0) {
                            System.out.println(noAccs);
                            noAccsBool = true;
                            break;
                        } else {
                            for (int i = 0; i < lists.size(); i++) {
                                userInfo = lists.get(i).split(",");
                                if (userInfo[0].equals(email) && userInfo[1].equals(pass)) {
                                    System.out.println("You are now logged in as \"" + userInfo[0] + "\"!");
                                    loggedIn = true;
                                    role = userInfo[2];
                                    break;
                                }
                            }
                            if (!loggedIn) {
                                System.out.println(accNotExist);
                            } else {
                                break;
                            }
                        }
                    }
                    break;

                } else if (signInOption == 2) { // sign up option
                    bfr = new BufferedReader(new FileReader(new File("accounts.txt")));
                    while (true) {
                        System.out.print(enterUser);
                        email = scan.next();
                        scan.nextLine();
                        System.out.print(enterPass);
                        pass = scan.nextLine();
                        String line;
                        while ((line = bfr.readLine()) != null) {
                            if (line.contains(email)) {
                                System.out.println(accExists);
                                accCheck = true;
                                break;
                            }
                        }
                        if (accCheck == false) {
                            break;
                        } else {
                            accCheck = false;
                        }
                    }
                    fw = new FileWriter(new File("accounts.txt"), true);
                    while (true) {
                        System.out.print(enterRole);
                        role = scan.nextLine().toLowerCase();
                        if ((role.equals("buyer")) || (role.equals("seller"))) {
                            break;
                        } else {
                            System.out.println(invalidRole);
                        }
                    }
                    String info = email + "," + pass + "," + role;
                    fw.write(info + "\n");
                    fw.flush();
                    fw.close();
                    System.out.println(accCreated);
                    signInOption = 0;
                }
            }
            // code if they want to make alterations to their account, delete it or move on to the website

            if (!(noAccsBool)) {
                System.out.println(accOptions); // should be accessed after logged in
                while (true) {
                    System.out.println(accOptionNums);
                    accOptionChoice = scan.nextInt();
                    scan.nextLine();
                    if (accOptionChoice == 1) { // change password
                        while (true) {
                            System.out.print(enterCurrentPass);
                            String input = scan.nextLine();
                            if (input.equals(pass)) {
                                System.out.print(enterNewPass);
                                input = scan.nextLine();
                                if (!(input.equals(pass))) {
                                    for (int i = 0; i < lists.size(); i++) {
                                        String[] info = lists.get(i).split(",");
                                        if (info[0].equals(email)) {
                                            info[1] = input;
                                            lists.set(i, info[0] + "," + info[1] + "," + info[2]);
                                            break;
                                        }
                                    }
                                    FileWriter fw2 = new FileWriter(new File("accounts.txt"));
                                    fw2.write("");
                                    fw2.close();
                                    FileWriter fw3 = new FileWriter(new File("accounts.txt"), true);
                                    for (int i = 0; i < lists.size(); i++) {
                                        fw3.write(lists.get(i) + "\n");
                                    }
                                    fw3.close();
                                    break;
                                } else {
                                    System.out.println(passCheck);
                                }
                            } else {
                                System.out.println(incorrectPass);
                            }
                        }
                        System.out.println(passChangeComplete);
                        break;
                    } else if (accOptionChoice == 2) { // delete account
                        System.out.println(areYouSure);
                        while (true) {
                            System.out.println(yesNo);
                            yesOrNo = scan.nextInt();
                            scan.nextLine();
                            if (yesOrNo == 1) { // yes
                                int index = 0;
                                for (int i = 0; i < lists.size(); i++) {
                                    if (lists.get(i).contains(email) && lists.get(i).contains(pass)) {
                                        index = i;
                                        break;
                                    }
                                }
                                lists.remove(index);
                                ArrayList<String> newSeller = new ArrayList<>();
                                BufferedReader bfrMarket = new BufferedReader(new FileReader(new File("marketplace.txt")));
                                String lineToParse;
                                while ((lineToParse = bfrMarket.readLine()) != null) {
                                    if (!(lineToParse.contains(email))) {
                                        newSeller.add(lineToParse);
                                    }
                                }
                                bfrMarket.close();
                                File marketTemp = new File("marketplace.txt");
                                FileWriter fwMarketTemp = new FileWriter(marketTemp, true);
                                for (int i = 0; i < newSeller.size(); i++) {
                                    fwMarketTemp.write(newSeller.get(i) + "\n");
                                }
                                fwMarketTemp.close();
                                File market = new File("marketplace.txt");
                                market.delete();
                                marketTemp.renameTo(market);
                                File temp = new File("accs.txt");
                                FileWriter fwTemp = new FileWriter(temp, true);
                                for (int i = 0; i < lists.size(); i++) {
                                    fwTemp.write(lists.get(i) + "\n");
                                }
                                fwTemp.close();
                                File accs = new File("accounts.txt");
                                accs.delete();
                                temp.renameTo(accs);
                                System.out.println(deleted);
                                accDeleted = true;
                                break;
                            } else if (yesOrNo == 2) { // no
                                System.out.println(reconsidered);
                                break;
                            } else { // invalid
                                System.out.println(invalidNum);
                            }
                        }
                        if (yesOrNo == 1) {
                            break;
                        }
                    } else if (accOptionChoice == 3) { // take to website
                        System.out.println(moveOn);
                        break;
                    }
                }
                // if the account is deleted, remove info from acount.txt file

                if (!(accDeleted)) {
                    lists = new ArrayList<>();
                    int counter = 0;
                    BufferedReader bf = new BufferedReader(new FileReader(new File("accounts.txt")));
                    while (bf.readLine() != null) {
                        counter++;
                    }
                    bf.close();
                    BufferedReader bf2 = new BufferedReader(new FileReader(new File("accounts.txt")));
                    for (int i = 0; i < counter; i++) {
                        lists.add(bf2.readLine());
                    }
                    bf.close();
                    // depending the account that has signed in, that person may be a buyer or a seller

                    if (role.equals("seller")) { // seller option
                        boolean continues = true;
                        while (continues) {
                            int total = 0;
                            // create the array lists for the info on the properties
                            ArrayList<String> market = new ArrayList<>();
                            ArrayList<String> name = new ArrayList<>();
                            ArrayList<String> location = new ArrayList<>();
                            ArrayList<String> type = new ArrayList<>();
                            ArrayList<String> info = new ArrayList<>();
                            ArrayList<String> price = new ArrayList<>();
                            ArrayList<String> seller = new ArrayList<>();
                            ArrayList<String> store = new ArrayList<>();
                            ArrayList<String> quantity = new ArrayList<>();
                            ArrayList<String> numSales = new ArrayList<>();
                            ArrayList<String> revenue = new ArrayList<>();
                            ArrayList<String> custNames = new ArrayList<>();
                            int x = 0;
                            int track = 0;
                            // create the marketplace file
                            BufferedReader bb = new BufferedReader(new FileReader(new File("marketplace.txt")));
                            while (bb.readLine() != null) {
                                x++;
                                total++;
                            }
                            //create the marketplace file and add the information on the property
                            bb.close();
                            bb = new BufferedReader(new FileReader(new File("marketplace.txt")));
                            for (int i = 0; i < x; i++) {
                                track++;
                                String thiss = bb.readLine();
                                market.add(thiss);
                                String[] parts = thiss.split(",");
                                name.add(parts[0]);
                                location.add(parts[1]);
                                type.add(parts[2]);
                                info.add(parts[3]);
                                price.add(parts[4]);
                                seller.add(parts[5]);
                                store.add(parts[6]);
                                quantity.add(parts[7]);
                                numSales.add(parts[8]);
                                revenue.add(parts[9]);
                                custNames.add(parts[10]);
                            }
                            // code the options for the sellers
                            // (view marketplace, modify shop or view store statistics)
                            System.out.println("Please choose what you would like to do: ");
                            System.out.println("1. View the Marketplace");
                            System.out.println("2. Modify Shop");
                            System.out.println("3. View Store Statistics");
                            String choice = scan.nextLine();
                            if (choice.equals("1")) {
                                // print all the properties in the marketplace
                                for (int y = 0; y < x; y++) {
                                    System.out.println(market.get(y));
                                }
                            } else if (choice.equals("2")) {
                                boolean modify = true;
                                // add different options por modification
                                while (modify) {
                                    System.out.println("What would you like to do today?");
                                    System.out.println("1. Create a new item");
                                    System.out.println("2. Modify an existing item");
                                    System.out.println("3. Delete an item");
                                    System.out.println("4. Quit program");
                                    String schoice = scan.nextLine();
                                    if (schoice.equals("1")) { // create a new item
                                        total++;
                                        boolean correct = true;
                                        String choicer = "";
                                        while (correct) {
                                            // implement the two different optons on how to create a new item
                                            System.out.println("Would you like to add the property by hand or upload a file?");
                                            System.out.println("1: Upload a file");
                                            System.out.println("2: Add by hand");
                                            choicer = scan.nextLine();
                                            if (choicer.equals("1") || choicer.equals("2")) {
                                                correct = false;
                                            } else {
                                                System.out.println("That is not a correct output please try again");
                                            }
                                        } if (choicer.equals("1")) {
                                            // creating an item with a file that contains the necessary information on the property
                                            System.out.println("Please enter the name of your file");
                                            String file = scan.nextLine();
                                            try {
                                                BufferedReader br = new BufferedReader(new FileReader(file));
                                                String line = br.readLine();
                                                String[] parts = line.split(",");
                                                br.close();
                                                name.add(parts[0]);
                                                location.add(parts[1]);
                                                type.add(parts[2]);
                                                info.add(parts[3]);
                                                price.add(parts[4]);
                                                seller.add(parts[5]);
                                                store.add(parts[6]);
                                                quantity.add(parts[7]);
                                                numSales.add("0");
                                                revenue.add("0");
                                                custNames.add("none");
                                                String ad = (parts[0] + "," + parts [1] + "," + parts[2] + "," + parts[3] + ","
                                                        + parts[4] + "," + parts[5] + "," + parts[6] + "," + parts[7] + ",0,0,none");
                                                market.add(ad);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            // manually adding the new property, in this case ask the necessary information to add the property to the marketplace
                                            System.out.println("What is the name of the property?");
                                            String propName = scan.nextLine();
                                            name.add(propName);
                                            boolean prop = true;
                                            String propLoc = "";
                                            while (prop) {
                                                System.out.println("Where is the property located?");
                                                System.out.println("1. Beach property");
                                                System.out.println("2. Mountain property");
                                                System.out.println("3. City property");
                                                System.out.println("4. Town property");
                                                propLoc = scan.nextLine();
                                                if (propLoc.equals("1")) {
                                                    propLoc = "Beach Property";
                                                    location.add(propLoc);
                                                    prop = false;
                                                } else if (propLoc.equals("2")) {
                                                    propLoc = "Mountain Property";
                                                    location.add(propLoc);
                                                    prop = false;
                                                } else if (propLoc.equals("3")) {
                                                    propLoc = "City Property";
                                                    location.add(propLoc);
                                                    prop = false;
                                                } else if (propLoc.equals("4")) {
                                                    propLoc = "Town Property";
                                                    location.add(propLoc);
                                                    prop = false;
                                                } else {
                                                    System.out.println("Not a valid input");
                                                }
                                            }
                                            prop = true;
                                            String propType = "";
                                            while (prop) {
                                                System.out.println("What type of property is it?");
                                                System.out.println("1. House");
                                                System.out.println("2. Apartment");
                                                propType = scan.nextLine();
                                                if (propType.equals("1")) {
                                                    propType = "House";
                                                    type.add(propType);
                                                    prop = false;
                                                } else if (propType.equals("2")) {
                                                    propType = "Apartment";
                                                    type.add(propType);
                                                    prop = false;
                                                } else {
                                                    System.out.println("Invalid input");
                                                }
                                            }
                                            System.out.println("What is a description of the house?");
                                            String description = scan.nextLine();
                                            info.add(description);
                                            System.out.println("What store is this being sold in?");
                                            String stores = scan.nextLine();
                                            store.add(stores);
                                            System.out.println("What is the price of the house?");
                                            String prices = scan.nextLine();
                                            price.add(prices);
                                            System.out.println("How many of these houses are you selling?");
                                            String quant = scan.nextLine();
                                            quantity.add(quant);
                                            File file = new File("marketplace.txt");
                                            String add = propName + "," + propLoc + "," + propType + "," + description + "," + prices +
                                                    "," + email + "," + stores + "," + quant + ",0,0,none";
                                            market.add(add);
                                            track++;
                                            FileWriter fileWriter = new FileWriter(file, true);
                                            BufferedWriter writer = new BufferedWriter(fileWriter);
                                            writer.write(market.get(market.indexOf(add)) + "\n");
                                            writer.close();
                                            fileWriter.close();
                                        }
                                    } else if (schoice.equals("2")) { // modify shop
                                        // here you can modify an existing property
                                        boolean ch = true;
                                        boolean done = false;
                                        String propToModify;
                                        int index = -1;
                                        while (ch) {
                                            while (!done) {
                                                System.out.println("What is the name of the property you would like to modify?");
                                                propToModify = scan.nextLine();
                                                for (int i = 0; i < total; i++) {
                                                    if (name.get(i).equals(propToModify)) {
                                                        index = i;
                                                        done = true;
                                                    }
                                                }
                                                if (done == false) {
                                                    System.out.println("This property doesn't exist");
                                                }
                                            }
                                            boolean go = true;
                                            go = true;
                                            String changeOpt = "k";
                                            while (go) {
                                                // add the different options for change
                                                System.out.println("What would you like to change today?");
                                                System.out.println("1. Name of House");
                                                System.out.println("2. Location");
                                                System.out.println("3. Type of House");
                                                System.out.println("4. House description");
                                                System.out.println("5. Price");
                                                System.out.println("6. Store");
                                                System.out.println("7. Quantity");
                                                changeOpt = scan.nextLine();
                                                if ((changeOpt.equals("1")) || (changeOpt.equals("2")) ||
                                                        (changeOpt.equals("3")) || (changeOpt.equals("4")) ||
                                                        (changeOpt.equals("5")) || (changeOpt.equals("6")) ||
                                                        (changeOpt.equals("7"))) {
                                                    go = false;
                                                } else {
                                                    System.out.println("Invalid Input!");
                                                }
                                            }
                                            // make changes in the marketplace.txt file
                                            System.out.println("Enter the change");
                                            String change = scan.nextLine();
                                            String add = "";
                                            if (changeOpt.equals("1")) {
                                                add = change + "," + location.get(index) + "," + type.get(index)
                                                        + "," + info.get(index) + "," + price.get(index) +
                                                        "," + email + "," + store.get(index) + "," + quantity.get(index)
                                                        + "," + numSales.get(index) + "," + revenue.get(index) +
                                                        "," + custNames.get(index);
                                            } else if (changeOpt.equals("2")) {
                                                add = name.get(index) + "," + change + "," + type.get(index)
                                                        + "," + info.get(index) + "," + price.get(index) +
                                                        "," + email + "," + store.get(index) + "," + quantity.get(index) +
                                                        "," + numSales.get(index) + "," + revenue.get(index) +
                                                        "," + custNames.get(index);
                                                ;
                                            } else if (changeOpt.equals("3")) {
                                                add = name.get(index) + "," + location.get(index) + "," + change
                                                        + "," + info.get(index) + "," + price.get(index) +
                                                        "," + email + "," + store.get(index) + "," + quantity.get(index) +
                                                        "," + numSales.get(index) + "," + revenue.get(index) +
                                                        "," + custNames.get(index);
                                                ;
                                            } else if (changeOpt.equals("4")) {
                                                add = name.get(index) + "," + location.get(index) + "," + type.get(index)
                                                        + "," + change + "," + price.get(index) +
                                                        "," + email + "," + store.get(index) + "," + quantity.get(index) + ","
                                                        + numSales.get(index) + "," + revenue.get(index) +
                                                        "," + custNames.get(index);
                                                ;
                                            } else if (changeOpt.equals("5")) {
                                                add = name.get(index) + "," + location.get(index) + "," + type.get(index)
                                                        + "," + info.get(index) + "," + change +
                                                        "," + email + "," + store.get(index) + "," + quantity.get(index) + ","
                                                        + numSales.get(index) + "," + revenue.get(index) +
                                                        "," + custNames.get(index);
                                                ;
                                            } else if (changeOpt.equals("6")) {
                                                add = name.get(index) + "," + location.get(index) + "," + type.get(index)
                                                        + "," + info.get(index) + "," + price.get(index) +
                                                        "," + email + "," + change + "," + quantity.get(index) + "," +
                                                        numSales.get(index) + "," + revenue.get(index) +
                                                        "," + custNames.get(index);
                                                ;
                                            } else {
                                                add = name.get(index) + "," + location.get(index) + "," + type.get(index)
                                                        + "," + info.get(index) + "," + price.get(index) +
                                                        "," + email + "," + store.get(index) + "," + change + "," +
                                                        numSales.get(index) + "," + revenue.get(index) +
                                                        "," + custNames.get(index);
                                                ;
                                            }
                                            market.set(index, add);
                                            FileOutputStream w = new FileOutputStream("marketplace.txt", false);
                                            for (int i = 0; i < market.size(); i++) {
                                                String v = market.get(i);
                                                w.write(v.getBytes());
                                            }
                                            w.close();
                                            //add option to make more changes 
                                            System.out.println("Would you like to make more changes?");
                                            System.out.println("1. Yes");
                                            System.out.println("2. No");
                                            String yn = scan.nextLine();
                                            if (yn.equals("2")) {
                                                ch = false;
                                            }
                                        }
                                    } else if (schoice.equals("3")){ // delete item
                                        // if the seller wants to delete an existing item from marketplace
                                        ArrayList<String> sellerIndex = new ArrayList<>();
                                        modify = true;
                                        while (modify) {
                                            for (int i = 0; i < total; i++) {
                                                if (email.equals(seller.get(i))) {
                                                    sellerIndex.add(String.valueOf(i));
                                                }
                                            }
                                            int t = 0;
                                            System.out.printf("You currently have %d items on the market:\n", sellerIndex.size());
                                            for (int j = 0; j < sellerIndex.size(); j++) {
                                                int d = Integer.parseInt(sellerIndex.get(j));
                                                System.out.printf("%d: %s\n", j + 1, name.get(d));
                                                t++;
                                            }
                                            boolean check = true;
                                            String del = "0";
                                            while (check) {
                                                System.out.println("Which would you like to delete?");
                                                del = scan.nextLine();
                                                if (Integer.parseInt(del) < 0 || Integer.parseInt(del) > t) {
                                                    System.out.println("Invalid Option!");
                                                } else {
                                                    check = false;
                                                }
                                            }
                                            int inDel = Integer.parseInt(sellerIndex.get(Integer.parseInt(del)));
                                            market.remove(inDel);
                                            name.remove(inDel);
                                            type.remove(inDel);
                                            info.remove(inDel);
                                            price.remove(inDel);
                                            seller.remove(inDel);
                                            store.remove(inDel);
                                            quantity.remove(inDel);
                                            numSales.remove(inDel);
                                            revenue.remove(inDel);
                                            custNames.remove(inDel);
                                            System.out.println("Deletion Complete!");
                                            System.out.println("Would you like to make more changes?");
                                            System.out.println("1. Yes");
                                            System.out.println("2. No");
                                            String yn = scan.nextLine();
                                            if (yn.equals("2")) {
                                                modify = false;
                                            }
                                        }
                                        total = total - 1;
                                    } else {
                                        // if user wants to quit the propgram
                                        System.out.println("Thank you for using Homes4Sales!");
                                        return;
                                    }
                                }
                            } else { // view product information
                                // if they want to view the store's statistics
                                boolean more = true;
                                while (more) {
                                    ArrayList<String> sellerIndex = new ArrayList<>();
                                    boolean rep = true;
                                    boolean loop = true;
                                    while (rep) {
                                        String choices = "";
                                        while (loop) {
                                            // add different choices they want to view in the marketplace
                                            System.out.println("What would you like to know about your products?");
                                            System.out.println("1: The number of sales");
                                            System.out.println("2: The revenue");
                                            System.out.println("3: The names of customers");
                                            System.out.println("4: Quit the program");
                                            choices = scan.nextLine();
                                            if (choices.equals("1") || choices.equals("2") || choices.equals("3") || choices.equals("4")) {
                                                loop = false;
                                            } else {
                                                System.out.println("Invalid choice!");
                                            }
                                        }
                                        int count = 0;
                                        // code how to get the information requested by the user
                                        for (int i = 0; i < total; i++) {
                                            if (email.equals(seller.get(i))) {
                                                sellerIndex.add(String.valueOf(i));
                                                count++;
                                            }
                                        }
                                        if (choices.equals("1")) {
                                            //number of sales
                                            int sum = 0;
                                            for (int i = 0; i < count; i++) {
                                                int num = Integer.parseInt(numSales.get(i));
                                                sum = sum + num;
                                            }
                                            rep = false;
                                            System.out.printf("The total number of sales is %d\n", sum);
                                        } else if (choices.equals("2")) {
                                            //revenue
                                            int sum = 0;
                                            rep = false;
                                            for (int i = 0; i < count; i++) {
                                                int num = Integer.parseInt(revenue.get(i));
                                                sum = sum + num;
                                            }
                                            System.out.printf("The total revenue is %d\n", sum);
                                        } else if (choices.equals("3")) {
                                            // names of customer
                                            String f = "";
                                            rep = false;
                                            for (int i = 0; i < count; i++) {
                                                String names = (custNames.get(i));
                                                if (i != count - 1) {
                                                    f = f + names + ",";
                                                } else {
                                                    f = f + names;
                                                }
                                            }
                                            System.out.printf("The names of the customers are %s\n", f);
                                        } else {
                                            // if user wants to quit program
                                            System.out.println("Thank you for using Homes4Sales!");
                                            return;
                                        } 
                                        //if user wants to know anything else
                                        System.out.println("Would you like to know anything else?");
                                        System.out.println("1: Yes");
                                        System.out.println("2: No");
                                        String yn = scan.nextLine();
                                        if (yn.equals("1")) {
                                            more = true;
                                        } else if (yn.equals("2")) {
                                            more = false;
                                        } else {
                                            System.out.println("Invalid Answer");
                                        }
                                    }
                                }
                            }
                        }
                        // finish of seller 
                        // start of buyer
                    } else { // buyer options
                        boolean continues = true;
                        while (continues) {
                            // create arrayLists needed for the buyer
                            ArrayList<String> market = new ArrayList<>();
                            ArrayList<String> name = new ArrayList<>();
                            ArrayList<String> location = new ArrayList<>();
                            ArrayList<String> type = new ArrayList<>();
                            ArrayList<String> info = new ArrayList<>();
                            ArrayList<String> price = new ArrayList<>();
                            ArrayList<String> seller = new ArrayList<>();
                            ArrayList<String> store = new ArrayList<>();
                            ArrayList<String> quantity = new ArrayList<>();
                            ArrayList<String> numSales = new ArrayList<>();
                            ArrayList<String> revenue = new ArrayList<>();
                            ArrayList<String> custNames = new ArrayList<>();
                            int x = 0;
                            int total = 0;
                            int issue = 0;
                            //create marketplace file 
                            int track = 0;
                            BufferedReader bb = new BufferedReader(new FileReader(new File("marketplace.txt")));
                            while (bb.readLine() != null) {
                                x++;
                                total++;
                                issue++;
                            }
                            if (issue < 1) {
                                // if no one has entered any properties to the marketplace then print a statement saying there are no items
                                System.out.println("There are currently no items on the market. Please come back soon!");
                                bb.close();
                                return;
                            }
                            bb.close();
                            bb = new BufferedReader(new FileReader(new File("marketplace.txt")));
                            for (int i = 0; i < x; i++) {
                                track++;
                                String thiss = bb.readLine();
                                market.add(thiss);
                                String[] parts = thiss.split(",");
                                name.add(parts[0]);
                                location.add(parts[1]);
                                type.add(parts[2]);
                                info.add(parts[3]);
                                price.add(parts[4]);
                                seller.add(parts[5]);
                                store.add(parts[6]);
                                quantity.add(parts[7]);
                                numSales.add(parts[8]);
                                revenue.add(parts[9]);
                                custNames.add(parts[10]);
                            }
                            bb.close();
                            // print diffrent options for the buyers
                            System.out.println("What would you like to do today?");
                            System.out.println("1. View the Market and Shop");
                            System.out.println("2. Search for specific products");
                            System.out.println("3. Sort the market");
                            System.out.println("4. Quit program");
                            String choice = scan.nextLine();
                            if (choice.equals("1")) { // view market
                                // print the different properties that are stored in marketplace.txt file
                                boolean rep = true;
                                while (rep) {
                                    int t = 0;
                                    for (int y = 0; y < x; y++) {
                                        t++;
                                        System.out.printf("Product Number %d\n", y + 1);
                                        System.out.println("Name: " + name.get(y));
                                        System.out.println("Location: " + location.get(y));
                                        System.out.println("Type of Housing: " + type.get(y));
                                        //System.out.println("Description: " + info.get(y));
                                        System.out.println("Price: " + price.get(y));
                                        System.out.println("Seller Name: " + seller.get(y));
                                        System.out.println("Store Name: " + store.get(y));
                                        //System.out.println("Quantity: " + quantity.get(y));
                                    }
                                    boolean er = true;
                                    String num = "0";
                                    while (er) {
                                        System.out.println("Enter a number to learn more about the product");
                                        num = scan.nextLine();
                                        if (Integer.parseInt(num) < 1 || Integer.parseInt(num) > t) {
                                            System.out.println("That is not a valid number");
                                        } else {
                                            er = false;
                                        }
                                    }
                                    //print description of the property if the buuyer requests it 
                                    System.out.println("Here is the information you requested on this product:");
                                    int va = Integer.parseInt(num) - 1;
                                    //System.out.println(quan.get(0));
                                    System.out.printf("Description: %s\n", info.get(va));
                                    System.out.printf("Quantity: %s\n", quantity.get(va));
                                    boolean lad = true;
                                    String dd = "";
                                    while (lad) {
                                        // add option to buy product
                                        System.out.println("Now that you have all the information on this product do you want" +
                                                " to buy it?");
                                        System.out.println("1: Yes");
                                        System.out.println("2: No");
                                        dd = scan.nextLine();
                                        if (dd.equals("1") || dd.equals("No")) {
                                            lad = false;
                                        } else {
                                            System.out.println("Invalid option please try again");
                                        }
                                    }
                                    if (dd.equals("1")) {
                                        System.out.println("Thank you for your purchase");
                                        int c = Integer.parseInt(num);
                                        //System.out.println("|" + c + "|");
                                        String thx = quantity.get(c - 1);
                                        //System.out.println(thx);
                                        int change = Integer.parseInt(thx.trim());
                                        change = change - 1;
                                        int th = Integer.parseInt(num) - 1;
                                        //System.out.println(th);
                                        if (th == 0) {
                                            FileWriter writer = new FileWriter("marketplace.txt", false);
                                            for (int i = 0; i < market.size(); i++) {
                                                //System.out.println("here");
                                                if (i == (Integer.parseInt(num) - 1)) {
                                                    //System.out.println("here");
                                                    String add = name.get(Integer.parseInt(num) - 1) + "," +
                                                            location.get((Integer.parseInt(num) - 1)) + "," +
                                                            type.get((Integer.parseInt(num) - 1))
                                                            + "," + info.get((Integer.parseInt(num) - 1)) + "," +
                                                            price.get((Integer.parseInt(num) - 1)) +
                                                            "," + email + "," + store.get((Integer.parseInt(num) - 1))
                                                            + "," + change + "," +
                                                            numSales.get((Integer.parseInt(num) - 1)) + "," +
                                                            revenue.get((Integer.parseInt(num) - 1)) +
                                                            "," + custNames.get((Integer.parseInt(num) - 1));
                                                    writer.write(add);
                                                } else {
                                                    System.out.println("here");
                                                    writer.write(market.get((Integer.parseInt(num) - 1)));
                                                    writer.close();
                                                }
                                            }

                                        }
                                        quantity.set(th, String.valueOf(change));
                                        FileWriter writer = new FileWriter("receipt.txt", true);
                                        String wr = ("Product Name: " + name.get(th) +" Price " + price.get(th) + "\n");
                                        writer.write(wr);
                                        writer.close();
                                        return;
                                    }
                                }
                            } else if (choice.equals("2")) { // search for specific product
                                // code for a key word to lead to certain properties
                                System.out.println("Please enter the key search words below to find matches: ");
                                String key = scan.nextLine();
                                ArrayList<String> ind = new ArrayList<>();
                                int c = 0;
                                for (int i = 0; i < total; i++) {
                                    if (name.get(i).equals(key)) {
                                        ind.add(String.valueOf(i));
                                        c++;
                                    } else if (store.get(i).equals(key)) {
                                        ind.add(String.valueOf(i));
                                        c++;
                                    } else if (info.get(i).equals(key)) {
                                        ind.add(String.valueOf(i));
                                        c++;
                                    }
                                }
                                //print matches if any, or print that there were no matches
                                if (c == 0) {
                                    System.out.println("Sorry there were no matches!");
                                } else {
                                    System.out.println("Here is a list of all the product names that match your criteria!");
                                    for (int i = 0; i < ind.size(); i++) {
                                        System.out.println(name.get(Integer.parseInt(ind.get(i))));
                                    }
                                }
                            } else if (choice.equals("3")) { // sort market
                                // if the user wants the market to appear in a certain order
                                boolean again = true;
                                String cho = "";
                                while (again) {
                                    System.out.println("What would you like to sort the market by?");
                                    System.out.println("1: Cost");
                                    System.out.println("2: Name");
                                    System.out.println("3: Location");
                                    cho = scan.nextLine();
                                    if (cho.equals("1") || cho.equals("2") || cho.equals("3")) {
                                        again = false;
                                    } else {
                                        System.out.println("That is an invalid answer please try again");
                                    }
                                }
                                if (cho.equals("1")) { // if user chooses cost
                                    String[] costArr = price.toArray(new String[price.size()]);
                                    Arrays.sort(costArr, Comparator.comparingInt(Integer::parseInt));
                                    price = new ArrayList<>(Arrays.asList(costArr));
                                    System.out.println("Here are the costs of the houses sorted increasingly: ");
                                    for (int i = 0; i < price.size(); i++) {
                                        System.out.println(price.get(i));
                                    }
                                } else if (cho.equals("2")) { // if user chooses name
                                    String[] namesArr = name.toArray(new String[name.size()]);
                                    Arrays.sort(namesArr);
                                    name = new ArrayList<>(Arrays.asList(namesArr));
                                    System.out.println("Here are the names of houses sorted alphabetically: ");
                                    for (int i = 0; i < name.size(); i++) {
                                        System.out.println(name.get(i));
                                    }
                                } else { // if user chooses location
                                    String[] locationArr = location.toArray(new String[location.size()]);
                                    Arrays.sort(locationArr);
                                    location = new ArrayList<>(Arrays.asList(locationArr));
                                    System.out.println("Here are the names of locations sorted alphabetically: ");
                                    for (int i = 0; i < location.size(); i++) {
                                        System.out.println(location.get(i));
                                    }
                                }
                            } else { //if user wants to quit program
                                System.out.println("Thank you for using Homes4Sales");
                                return;
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scan.close();
    }
}
