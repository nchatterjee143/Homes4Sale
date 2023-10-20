//final copy 
import java.net.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Homes4SaleServer extends Thread {
    public static boolean connectionMade = false;
    public static String logUsername;
    public static String logPassword;
    public static String logRole;
    public static boolean loggedIn = false;
    public static boolean atWebsite = false;
    public static int lineCount = 0;
    private static int total = 0;
    public static ArrayList<String> accList = new ArrayList<>();
    private static ArrayList<String> market = new ArrayList<>();
    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> location = new ArrayList<>();
    private static ArrayList<String> type = new ArrayList<>();
    private static ArrayList<String> info = new ArrayList<>();
    private static ArrayList<String> price = new ArrayList<>();
    private static ArrayList<String> seller = new ArrayList<>();
    private static ArrayList<String> store = new ArrayList<>();
    private static ArrayList<String> quantity = new ArrayList<>();
    private static ArrayList<String> numSales = new ArrayList<>();
    private static ArrayList<String> revenue = new ArrayList<>();
    private static ArrayList<String> custNames = new ArrayList<>();
    private static int counter = -1;
    private static int count = 0;
    private static String num = "0";

    public static void main(String[] args) throws IOException{
        //creating socket
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            JOptionPane.showMessageDialog(null, "Server created!\nHost: \"localhost\"\nPort: 1234",
                    "Server", JOptionPane.INFORMATION_MESSAGE);

            while (true) {
                Socket socket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Couldn\'t create server...",
                    "Server", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                // handle client requests here
                // ...

                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
// signing up into a new account
    public static void signUp () {
        checkMarket();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File("accounts.txt")));
            boolean accCheck = false;
            String username;
            String password;
            String line;
            String role;
            while (true) {
                //enter a username and password
                username = JOptionPane.showInputDialog(null, "Enter a username.",
                        "Homes4Sale", JOptionPane.QUESTION_MESSAGE);
                password = JOptionPane.showInputDialog(null, "Enter a password.",
                        "Homes4Sale", JOptionPane.QUESTION_MESSAGE);
                while ((line = bfr.readLine()) != null) {
                    if (line.contains(username)) {
                        //error if the account already exists
                        JOptionPane.showMessageDialog(null, "Account already exists!",
                                "Homes4Sale",
                                JOptionPane.ERROR_MESSAGE);
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
            //save information into a file called account.txt
            FileWriter fw = new FileWriter(new File("accounts.txt"), true);
            while (true) {
                role = JOptionPane.showInputDialog(null, "Enter a role.",
                        "Homes4Sale", JOptionPane.QUESTION_MESSAGE);
                role = role.toLowerCase();
                //identify as buyer or seller
                if ((role.equals("buyer")) || (role.equals("seller"))) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid role!", "Homes4Sale",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            //add account info by username, password, role
            String info = "\n" + username + "," + password + "," + role;
            fw.write(info);
            fw.flush();
            fw.close();
            JOptionPane.showMessageDialog(null, "Account created! Please log in again.",
                    "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// if logging in 
    public static void logIn() {
        checkMarket();
        try {
            // gets every account in the file registry
            BufferedReader bfr = new BufferedReader(new FileReader(new File("accounts.txt")));
            lineCount = 0;
            while (bfr.readLine() != null) {
                lineCount++;
            }
            bfr.close();
            BufferedReader bfr2 = new BufferedReader(new FileReader(new File("accounts.txt")));
            for (int i = 0; i < lineCount; i++) {
                accList.add(bfr2.readLine());
            }
            bfr2.close();
//enter different data to log in to a specific account
            while (true) {
                logUsername = JOptionPane.showInputDialog(null, "Enter your username.",
                        "Homes4Sale", JOptionPane.QUESTION_MESSAGE);
                if (logUsername == null) {
                    JOptionPane.showMessageDialog(null, "Goodbye!",
                            "Homes4Sale", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                logPassword = JOptionPane.showInputDialog(null, "Enter your password.",
                        "Homes4Sale", JOptionPane.QUESTION_MESSAGE);
                if (logPassword == null) {
                    JOptionPane.showMessageDialog(null,
                            "Goodbye! Thank you for using Homes4Sale!",
                            "Homes4Sale", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                // if the accouhnt doesn't exists
                if (logUsername != null || logPassword != null) {
                    if (accList.size() == 0) {
                        JOptionPane.showMessageDialog(null, "Sorry! No accounts registerd. Aborting...",
                                "Homes4Sale", JOptionPane.ERROR_MESSAGE);
                        break;
                    } else {
                        for (int i = 0; i < accList.size(); i++) {
                            String[] userInfo = accList.get(i).split(",");
                            if (userInfo[0].equals(logUsername) && userInfo[1].equals(logPassword)) {
                                loggedIn = true;
                                logRole = userInfo[2];
                                JOptionPane.showMessageDialog(null, "You are now logged in as \"" + logUsername + "\"!",
                                        "Home4Sale", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        }
                        if (!(loggedIn)) {
                            JOptionPane.showMessageDialog(null, "Account does not exist!", "Homes4Sale",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // if user wants to change password

    public static void changePassword() {
        checkMarket();
        try {
            // to do
            atWebsite = true;
            while (true) {
                //enter current password and enter new password
                String pass = JOptionPane.showInputDialog(null,
                        "Please enter your current password: ", "Homes4Sale",
                        JOptionPane.QUESTION_MESSAGE);
                if (pass.equals(logPassword)) {
                    String new_pass = JOptionPane.showInputDialog(null,
                            "Enter your new password: ", "Homes4Sale",
                            JOptionPane.QUESTION_MESSAGE);
                    if (!(new_pass.equals(pass))) {
                        for (int i = 0; i < lineCount; i++) {
                            String[] info = accList.get(i).split(",");
                            if (info[0].equals(logUsername)) {
                                info[1] = new_pass;
                                accList.set(i, info[0] + "," + info[1] + "," + info[2]);
                                break;
                            }
                        }
                        FileWriter fw2 = new FileWriter(new File("accounts.txt"));
                        fw2.write("");
                        fw2.close();
                        FileWriter fw3 = new FileWriter(new File("accounts.txt"), true);
                        for (int i = 0; i < accList.size(); i++) {
                            fw3.write(accList.get(i) + "\n");
                        }
                        fw3.write(""); // write an empty string to the file to flush the output buffer
                        fw3.close(); // close the FileWriter object
                        break;
                    } else {
                        JOptionPane.showInputDialog(null,
                                "Uh oh! New password cannot be the same as old password!", "Homes4Sale",
                                JOptionPane.INFORMATION_MESSAGE);
                        //error if password is the same as the original one
                    }
                } else {
                    JOptionPane.showInputDialog(null,
                            "Uh oh! Incorrect password!", "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //if user wants to delete account
    public static void deleteAcc() throws IOException {
        checkMarket();
        ArrayList<String> accounts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("accounts.txtt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.contains(logUsername)) {
                    accounts.add(line);
                }
            }
        }

        // Write the updated contents of accounts.txt back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("accounts.txt"))) {
            for (String account : accounts) {
                bw.write(account);
                bw.newLine();
            }
        }

        // Read in the contents of marketplace.txt
        ArrayList<String> marketplace = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("market.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.contains(logUsername)) {
                    marketplace.add(line);
                }
            }
        }

        // Write the updated contents of marketplace.txt back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("market.txt"))) {
            for (String item : marketplace) {
                bw.write(item);
                bw.newLine();
            }
        }
        JOptionPane.showMessageDialog(null, "Your account has been deleted. Goodbye!",
                "Server", JOptionPane.INFORMATION_MESSAGE);
    }
    //for viewing marketplace
    public static void viewMarketplace() {
        checkMarket();
        int t = 0;
        String s = String.format("Product Number 1");
        for (int y = 0; y < market.size(); y++) {
            t++;
            if (y > 0) {
                s = s  + "\n" + String.format("\nProduct Number %d", y + 1);
            }
            s = s  + "\n" + String.format("Name: " + name.get(y));
            s = s  + "\n" + String.format("Location: " + location.get(y));
            s = s  + "\n" + String.format("Type of Housing: " + type.get(y));
            s = s  + "\n" +  String.format("Price: " + price.get(y));
            s = s  + "\n" + String.format("Seller Name: " + seller.get(y));
            s = s  + "\n" + String.format("Store Name: " + store.get(y));
        }
        JOptionPane.showMessageDialog(null, s, "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
    }
    // if seller uploads a file with a new property 
    public static void upload() {
        checkMarket();
        String file = JOptionPane.showInputDialog(null,
                "Please enter the name of your file", "Homes4Sale",
                JOptionPane.QUESTION_MESSAGE);
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
    }
    //if seller wants to add a new property to the marketplace manually
    public static void manual(ArrayList<String> infos){
        checkMarket();
        try {
            String propLoc = infos.get(0);
            location.add(propLoc);
            String propType = infos.get(1);
            type.add(propType);
            String propName = JOptionPane.showInputDialog(null,
                    "What is the name of the property?", "Homes4Sale",
                    JOptionPane.QUESTION_MESSAGE);
            name.add(propName);
            String description = JOptionPane.showInputDialog(null,
                    "What is a description of the house?", "Homes4Sale",
                    JOptionPane.QUESTION_MESSAGE);
            info.add(description);
            String stores = JOptionPane.showInputDialog(null,
                    "What store is this being sold in?", "Homes4Sale",
                    JOptionPane.QUESTION_MESSAGE);
            store.add(stores);
            String prices = JOptionPane.showInputDialog(null,
                    "What is the price of the house?", "Homes4Sale",
                    JOptionPane.QUESTION_MESSAGE);
            price.add(prices);
            numSales.add("0");
            seller.add(logUsername);
            revenue.add("0");
            custNames.add("null");
            String quant = JOptionPane.showInputDialog(null,
                    "How many of these houses are you selling?", "Homes4Sale",
                    JOptionPane.QUESTION_MESSAGE);
            quantity.add(quant);
            File file = new File("market.txt");
            String add = "";
            if (market.size() != 0) {
                add = propName + "," + propLoc + "," + propType + "," + description + "," + prices +
                        "," + logUsername + "," + stores + "," + quant + ",0,0,none";
            } else {
                add = "\n" + propName + "," + propLoc + "," + propType + "," + description + "," + prices +
                        "," + logUsername + "," + stores + "," + quant + ",0,0,none";
            }
            market.add(add);
            total++;
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(market.get(market.indexOf(add)) + "\n");
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //for modifying a property
    public static void changes(Integer choices){
        checkMarket();
        boolean done = false;
        boolean ran = false;
        int index = -1;
        while (!done) {
            String propToModify = JOptionPane.showInputDialog(null,
                    "What is the name of the property you would like to modify?", "Homes4Sale",
                    JOptionPane.QUESTION_MESSAGE);
            if (propToModify != null) {
                ran = true;
            }
            counter = market.size();
            for (int i = 0; i < counter; i++) {
                if (name.get(i).equals(propToModify)) {
                    index = i;
                    done = true;
                }
            }
            if (ran == false) {
                break;
            }
            if (done == false) {
                JOptionPane.showMessageDialog(null, "This property doesn't exist",
                        "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        // make changes in the marketplace.txt file
        boolean rep = true;
        String change = "";
        while (rep) {
            rep = false;
            change = JOptionPane.showInputDialog(null,
                    "Enter the change", "Homes4Sale",
                    JOptionPane.QUESTION_MESSAGE);
            if (change.equals("") || change == null) {
                rep = true;
                JOptionPane.showMessageDialog(null, "Invalid Response",
                        "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        String add = "";
        if (choices == 0) {
            name.set(index, change);
            add = change + "," + location.get(index) + "," + type.get(index)
                    + "," + info.get(index) + "," + price.get(index) +
                    "," + logUsername + "," + store.get(index) + "," + quantity.get(index)
                    + "," + numSales.get(index) + "," + revenue.get(index) +
                    "," + custNames.get(index);
            counter++;
            if (index != 0) {
                add = "\n" + add;
            }
        } else if (choices == 1) {
            location.set(index, change);
            add = name.get(index) + "," + change + "," + type.get(index)
                    + "," + info.get(index) + "," + price.get(index) +
                    "," + logUsername + "," + store.get(index) + "," + quantity.get(index) +
                    "," + numSales.get(index) + "," + revenue.get(index) +
                    "," + custNames.get(index);
            counter++;
            if (index != 0) {
                add = "\n" + add;
            }
        } else if (choices == 2) {
            type.set(index, change);
            add = name.get(index) + "," + location.get(index) + "," + change
                    + "," + info.get(index) + "," + price.get(index) +
                    "," + logUsername + "," + store.get(index) + "," + quantity.get(index) +
                    "," + numSales.get(index) + "," + revenue.get(index) +
                    "," + custNames.get(index);
            counter++;
            if (index != 0) {
                add = "\n" + add;
            }
        } else if (choices == 3) {
            info.set(index, change);
            add = name.get(index) + "," + location.get(index) + "," + type.get(index)
                    + "," + change + "," + price.get(index) +
                    "," + logUsername + "," + store.get(index) + "," + quantity.get(index) + ","
                    + numSales.get(index) + "," + revenue.get(index) +
                    "," + custNames.get(index);
            counter++;
            if (index != 0) {
                add = "\n" + add;
            }
        } else if (choices == 4) {
            price.set(index, change);
            add = name.get(index) + "," + location.get(index) + "," + type.get(index)
                    + "," + info.get(index) + "," + change +
                    "," + logUsername + "," + store.get(index) + "," + quantity.get(index) + ","
                    + numSales.get(index) + "," + revenue.get(index) +
                    "," + custNames.get(index);
            counter++;
            if (index != 0) {
                add = "\n" + add;
            }
        } else if (choices == 5) {
            store.set(index, change);
            add = name.get(index) + "," + location.get(index) + "," + type.get(index)
                    + "," + info.get(index) + "," + price.get(index) +
                    "," + logUsername + "," + change + "," + quantity.get(index) + "," +
                    numSales.get(index) + "," + revenue.get(index) +
                    "," + custNames.get(index);
            counter++;
            if (index != 0) {
                add = "\n" + add;
            }
        } else {
            quantity.set(index, change);
            add = name.get(index) + "," + location.get(index) + "," + type.get(index)
                    + "," + info.get(index) + "," + price.get(index) +
                    "," + logUsername + "," + store.get(index) + "," + change + "," +
                    numSales.get(index) + "," + revenue.get(index) +
                    "," + custNames.get(index);
            counter++;
            if (index != 0) {
                add = "\n" + add;
            }
        }
        market.set(index, add);
        try {
            FileOutputStream w = new FileOutputStream("market.txt", false);
            for (int i = 0; i < market.size(); i++) {
                String v = market.get(i);
                if (i > 0) {
                    w.write("\n".getBytes());
                }
                w.write(v.getBytes());
            }
            w.close();

            File file = new File("market.txt");

            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                java.util.List<String> lines = new java.util.ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                reader.close();

                // Remove any null or empty strings
                lines.removeIf(s -> s == null || s.trim().isEmpty());

                // Write the remaining lines back to the file
                FileWriter writer = new FileWriter(file);
                for (String s : lines) {
                    writer.write(s + System.lineSeparator());
                }
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            w.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // to delete a property on the market 
    public static void delete() {
        checkMarket();
        ArrayList<String> sellerIndex = new ArrayList<>();
        for (int i = 0; i < market.size(); i++) {
            if (logUsername.equals(seller.get(i))) {
                sellerIndex.add(String.valueOf(i));
            }
        }
        int t = 0;
        String f = String.format("You currently have %d items on the market:\n",sellerIndex.size());
        if (sellerIndex.size() != 0)  {
            JOptionPane.showMessageDialog(null, f,  "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);

            for (int j = 0; j < sellerIndex.size(); j++) {
                int d = Integer.parseInt(sellerIndex.get(j));
                f = String.format("%d: %s\n", j + 1, name.get(d));
                JOptionPane.showMessageDialog(null,
                        (f), "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
                t++;
            }
            boolean check = true;
            String del = "0";
            while (check) {
                //enter the property that they would like to delete
                del = JOptionPane.showInputDialog(null,
                        "Which would you like to delete?", "Homes4Sale",
                        JOptionPane.QUESTION_MESSAGE);
                if (Integer.parseInt(del) < 0 || Integer.parseInt(del) > t) {
                    //if property doesn't exist
                    JOptionPane.showInputDialog(null,
                            ("Invalid Option!"), "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    check = false;
                }
            }
            //deleting from file
            int inDel = Integer.parseInt(sellerIndex.get(Integer.parseInt(del) - 1));
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
            JOptionPane.showMessageDialog(null,
                    ("Deletion Complete!"), "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);

            // Write updated listings to marketplace.txt file
            try {
                FileWriter writer = new FileWriter("market.txt");
                for (int i = 0; i < market.size(); i++) {
                    writer.write(name.get(i) + "," + type.get(i) + "," + info.get(i) + "," + price.get(i) + "," + seller.get(i) + "," + store.get(i) + "," + quantity.get(i) + "," + numSales.get(i) + "," + revenue.get(i) + "," + custNames.get(i) + "\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            total = total - 1;
        } else {
            JOptionPane.showMessageDialog(null, "Come back when you are selling things",
                    "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //if seller wants to view statistics 
    public static void information() {
        checkMarket();
        ArrayList<String> sellerIndex = new ArrayList<>();
        // code how to get the information requested by the user
        for (int i = 0; i < total; i++) {
            if (logUsername.equals(seller.get(i))) {
                sellerIndex.add(String.valueOf(i));
                count++;
            }
        }
    }
    //for checking the number of sales
    public static void numSales() {
        checkMarket();
        //number of sales
        int sums = 0;
        for (int i = 0; i < count; i++) {
            int num = Integer.parseInt(numSales.get(i));
            sums = sums + num;
        }
        String f = String.format("The total number of sales is %d\n", sums);
        JOptionPane.showMessageDialog(null, f, "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
    }
    //checking the revenue
    public static void revenue() {
        checkMarket();
        int sum = 0;
        for (int i = 0; i < count; i++) {
            int num = Integer.parseInt(revenue.get(i));
            sum = sum + num;
        }
        String f = String.format("The total revenue is %d\n", sum);
        JOptionPane.showMessageDialog(null, f, "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
    }
    //checking customer names
    public static void custNames() {
        checkMarket();
        String f = "";
        for (int i = 0; i < market.size(); i++) {
            String names = (custNames.get(i));
            if (i != market.size() - 1) {
                f = f + names + " ,";
            } else {
                f = f + names;
            }
        }
        String k = String.format("The names of the customers are %s\n", f);
        JOptionPane.showMessageDialog(null, k, "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public static boolean readMarket() {
        checkMarket();
        try {
            int x = 0;
            int issue = 0;
            //create marketplace file
            int track = 0;
            BufferedReader bb = new BufferedReader(new FileReader(new File("market.txt")));
            while (bb.readLine() != null) {
                issue++;
            }
            if (issue < 1) {
                // if no one has entered any properties to the marketplace then print a statement saying there are no items
                JOptionPane.showMessageDialog(null,
                        ("There are currently no items on the market. Please come back soon!"),
                        "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
                bb.close();
                return false;
            }
            bb.close();
            bb = new BufferedReader(new FileReader(new File("market.txt")));
            
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
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //if you are a buyer and want to view the market
    public static void viewMarket() {
        checkMarket();
        int t = 0;
        String s = String.format("Product Number 1");
        //list of different properties and their descriptions
        for (int y = 0; y < market.size(); y++) {
            t++;
            if (y > 0) {
                s = s  + "\n" + String.format("\nProduct Number %d", y + 1);
            }
            s = s  + "\n" + String.format("Name: " + name.get(y));
            s = s  + "\n" + String.format("Location: " + location.get(y));
            s = s  + "\n" + String.format("Type of Housing: " + type.get(y));
            s = s  + "\n" +  String.format("Price: " + price.get(y));
            s = s  + "\n" + String.format("Seller Name: " + seller.get(y));
            s = s  + "\n" + String.format("Store Name: " + store.get(y));
        }
        JOptionPane.showMessageDialog(null, s, "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
        boolean er = true;
        while (er) {
            //to learn more about the propduct
            num = JOptionPane.showInputDialog(null,
                    "Enter a number to learn more about the product", "Homes4Sale",
                    JOptionPane.QUESTION_MESSAGE);
            if (num == null) {
                //invalid number
                JOptionPane.showMessageDialog(null, "That is not a valid number:",
                        "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
            } else if (Integer.parseInt(num) < 1 || Integer.parseInt(num) > t) {
                //invalid number
                JOptionPane.showMessageDialog(null, "That is not a valid number:",
                        "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
            } else {
                er = false;
            }
        }
        //more information about product
        JOptionPane.showMessageDialog(null,
                "Here is the information you requested on this product:",
                "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
        int va = Integer.parseInt(num) - 1;
        String a = String.format("Description: %s\n", info.get(va));
        JOptionPane.showMessageDialog(null, a,  "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
        a = String.format("Quantity: %s\n", quantity.get(va));
        JOptionPane.showMessageDialog(null, a,  "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
    }
    //if after viewing a property they want to buy the property
    public static void buy() throws IOException {
        checkMarket();
        JOptionPane.showMessageDialog(null, "Thank you for your purchase!", "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
        int c = Integer.parseInt(num);
        String thx = quantity.get(c - 1);
        int change = Integer.parseInt(thx.trim()) - 1;
        System.out.println(change);
        int th = Integer.parseInt(num) - 1;
        System.out.println("here");
        // Add the purchased product to the receipt
        //this will be in a new file
        FileWriter receiptWriter = new FileWriter( "receipt.txt", true);
        String wr = "Product Name: " + name.get(th) + ", Price: " + price.get(th) + ", Store: " + store.get(th) + "\n";
        receiptWriter.write(wr);
        receiptWriter.close();
        if (th == 0) {
            try {
                FileWriter writer = new FileWriter("market.txt", false);
                for (int i = 0; i < market.size(); i++) {
                    if (i == th) {
                        if (change > 0) {
                            String add = name.get(th) + "," + location.get(th) + "," + type.get(th) + "," + info.get(th) + "," + price.get(th) + "," + logUsername + "," + store.get(th) + "," + change + "," + numSales.get(th) + "," + revenue.get(th) + "," + custNames.get(th);
                            // Don't write the line for this product to the file
                        }
                    } else {
                        writer.write(market.get(i));
                        writer.write("\n"); // Add a newline after each line
                    }
                }
                writer.close();
                // Remove the purchased product from the market lists
                name.remove(th);
                location.remove(th);
                type.remove(th);
                info.remove(th);
                price.remove(th);
                store.remove(th);
                quantity.remove(th);
                numSales.remove(th);
                revenue.remove(th);
                custNames.remove(th);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //if you want to search for a specific product
    public static void search() {
        checkMarket();
        String key = JOptionPane.showInputDialog(null,
                "Please enter the key search words below to find matches: ",
                "Homes4Sale", JOptionPane.QUESTION_MESSAGE).trim();
        ArrayList<String> ind = new ArrayList<>();
        int c = 0;
        for (int i = 0; i < market.size(); i++) {
            String[] components = market.get(i).split(",");
            String name = components[0].trim();
            String location = components[1].trim();
            String info = components[2].trim();
            if (name.contains(key) || location.contains(key) || info.contains(key)) {
                ind.add(String.valueOf(i));
                c++;
            }
        }
        //print matches if any, or print that there were no matches
        if (c == 0) {
            JOptionPane.showMessageDialog(null, "Sorry there were no matches!"
                    , "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String m = "Here is a list of all the product names that match your criteria!";
            for (int i = 0; i < ind.size(); i++) {
                m = m + "\n" + (name.get(Integer.parseInt(ind.get(i))));
            }
            JOptionPane.showMessageDialog(null, m
                    , "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //if buyer is searching by cost
    public static void costSearch() {
        checkMarket();
        String[] costArr = price.toArray(new String[price.size()]);
        Arrays.sort(costArr, Comparator.comparingInt(Integer::parseInt));
        price = new ArrayList<>(Arrays.asList(costArr));
        String m = "Here are the costs of the houses sorted increasingly: ";
        for (int i = 0; i < price.size(); i++) {
            m = m + "\n" + price.get(i);
        }
        JOptionPane.showMessageDialog(null, m, "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
    }
    //if buyer is searching y name

    public static void nameSearch() throws IOException {
        checkMarket();
        String[] namesArr = name.toArray(new String[name.size()]);
        Arrays.sort(namesArr);
        name = new ArrayList<>(Arrays.asList(namesArr));
        String message = "Here are the names of houses sorted alphabetically:\n";
        for (int i = 0; i < name.size(); i++) {
            message += name.get(i) + "\n";
            System.out.println(name.get(i));
        }
        JOptionPane.showMessageDialog(null, message, "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
    }
    //if buyer is searching by location
    public static void locationSearch() throws IOException {
        checkMarket();
        Collections.sort(market, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.split(",")[1].compareTo(o2.split(",")[1]);
            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the names of locations sorted alphabetically: \n\n");
        for (String s : market) {
            sb.append(s.split(",")[1]).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Homes4Sale", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void checkMarket() {
        if (name.isEmpty()) {
            File file = new File("market.txt");
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    market.add(line);
                    String[] parts = line.split(",");
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
                // Display information in a JTable or JList within the GUI
                // ...
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
            }
        }
    }
}
