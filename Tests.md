+ Note: Every test written is done under the assumption that the server (Homes4SaleServer.java) is running!

# Test 1 #

+ Enter port number "1234".
+ "Connection established" message appears on screen.

Expected result: Application verifies the user is connected to the server. 

Test Status: Passed. 

# Test 2 #

+ Follow the steps from Test 1
+ Press "Log In".
+ Enter username "buyerUser". You may also choose to do "sellerUser".
+ Enter password "buyerPass". If you chose "sellerUser", the password is "sellerPass".
+ Message should appear stating that you are now logged in as "buyerUser" or "sellerUser".

Expected result: Application verifies the user is connected to the server and logged in. 

Test Status: Passed. 

# Test 3 #

+ Follow the steps from Test 2
+ Click "Take to Website"
+ Message should appear that thanks you for choosing Homes4Sale

Expected result: Application verifies the user is connected to the server, logged in, and at the website. 

Test Status: Passed. 

# Test 4 #

+ Follow the steps from Test 3, but choose "sellerUser" and "sellerPass" this time around.
+ Click "Modify Shop".
+ Click "Quit Program".
+ Message should appear that says goodbye.

Expected result: Application verifies the user is at the website as a seller, and can leave the program. 

Test Status: Passed. 
