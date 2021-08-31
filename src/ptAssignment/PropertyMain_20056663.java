package ptAssignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;


public class PropertyMain_20056663 {
	static BufferedReader br = null;
	static String line;
	static Scanner kb = new Scanner(System.in);
	static ArrayList<Client_20056663> clients = new ArrayList<>();      // arraylists ive used to store data from files
	static ArrayList<Property_20056663> properties = new ArrayList<>();
	static ArrayList<Expense_20056663> expenses = new ArrayList<>();
	static ArrayList<Rents_20056663> rents = new ArrayList<>();
	
	static ArrayList<Expense_20056663> newExpenses = new ArrayList<>();   // new inputed value, gets stored in this for later use, in the save option
	static ArrayList<Rents_20056663> newRents = new ArrayList<>();

	 
	
	
	static int weeksOfrents = -1; // we set variable input to -1 because arrays start from 0 select function start from 0, 
	//in this case it wont since we minused 1 from user input, hence will provide the corresponding appropriate value that the user desires
	
	static boolean willExit = false; // set the loop via a boolean until the user decides to exit the program, it is set as true, which will then exit the loop 

	public static void main(String[] args) {
		storingClientsClass();
		storingPropertiesClass();		// the arraylists i created to store all the files inside each desired class 
		storingExpensesClass();
		storingRentClass();
		
		int choice = -1; // user validation

		while (!willExit) {
			choice = userInputValidation();
			switch (choice) {// The switch case method, so that user enters a certain option to run from the menu

			case 1:
				RecordingRentCollection(); // records rents 
				break;
			
			case 2:
				recordExpense(); // records expenses 
				break;
			
			case 3:
				generateReport(); // generates report
				break;
					
			case 4:
				saveFiles(); // saves the files
				break;
			
			case 5:
				
				if (!newExpenses.isEmpty() || !newRents.isEmpty()) { // if the new inputts from the user arent saved, program notifys to then ask the user if he/she would like to save or exit
					System.out.println("You Havent Saved Anything\n");
					System.out.println("Would you like to return to the menu or exit?  (return/exit)");
					String user = kb.nextLine();
					
					while (!user.equals("return") && !user.equals("exit")) {
						System.out.println("Please enter 'exit' or 'return'");	//VALIDATION
						user = kb.nextLine();
					}

					if (user.equals("exit")) {
						System.out.println("You Have Exited The Program, Have a nice Day! ");
						willExit = true; // program will execute if user decides to exit 
					}

				} else {
					System.out.println("You Have Exited The Program, Have a nice Day! "); // if user has already saved and decides to exit the program, the program will end
					willExit = true;
				}

			}
		}

	}
	
	public static boolean printReport(int clientIndex, String postcode, boolean reportEmpty)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		//matchingVariables
		double sumOfRents = 0;
		double sumOfExpenses = 0;
		// net fees expenses display variables
		double net = 0;
		double feesPlusExpenses = 0;
		double fees = 0;
		//total display variable
		double totalRent  = 0;
		double totalExpense = 0;
		double totalFees = 0;
		double totalNet = 0;
		
		ArrayList<String> rows = new ArrayList<String>();
		
		boolean ownsProperty = false;
		
		totalRent = 0;
		totalExpense = 0;
		totalFees = 0;
		totalNet =  0;
		sumOfRents = 0;
		sumOfExpenses = 0;
		fees = 0;
		net = 0;
		Client_20056663 c = clients.get(clientIndex);
		
		for (int p = 0; p < properties.size(); p++) {
        	Property_20056663 property = properties.get(p); 
        	if (properties.get(p).getClientID() == c.getClientID() &&	// match machine for postcode inputs, client Ids 
        			(postcode == null || properties.get(p).getPostCode().equals(postcode))) {
        		ownsProperty = true;
				for (int r = 0; r < rents.size(); r++) {
					if (property.getPropertyID() == rents.get(r).getPropertyID()) {
						sumOfRents += rents.get(r).getRentAmount();	// match machine, matches the propertiespropertyID and rentsPropertyID
					}

				}


				for (int j = 0; j < expenses.size(); j++) {
					if (property.getPropertyID() == expenses.get(j).getPropertyID()) {	// match machine to find sum of expenses 
						sumOfExpenses = expenses.get(j).getExpenseAmount();
					}
				}
				
				fees = property.getManagementFee()*sumOfRents;
				feesPlusExpenses = sumOfExpenses+fees;	// varibles needed to carry out values for each other to display the correct answers (fees, net, expenses)
				net = sumOfRents-feesPlusExpenses;
				
				//Total variable counters
				totalRent += sumOfRents;
				totalExpense += sumOfExpenses;
				totalFees += fees;
				totalNet += net;
				rows.add(String.format("%-48s"+"| %-14.2f"+"| %-14.2f"+"| %-14.2f"+"| %-14.2f"+"| %-13.2f\n",property.getStreetAddress()+" "+property.getSuburb()+" "+ property.getState()+" "+property.getPostCode(),sumOfRents,sumOfExpenses,property.getManagementFee(),fees,net));
				
				
			}
        	
		}
    	
    	if(!rows.isEmpty())
    	{
        	System.out.println("\n\nPORTFOLIO REPORT\n"+"Client: (" +c.getLastName()+", " + c.getFirstName() + "), "+c.getStreetAddress()+" "+c.getSuburb()+" "+c.getState()+" "+c.getPostCode());
    		System.out.println("Report Generated: "+dtf.format(now));
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.println("Property\t\t\t\t\t"+"| Rent\t\t"+"| Expenses\t"+"| Fee Rate\t"+"| Fees\t\t"+"| Net");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------\n");
            
            
            for(String s : rows)
            {
            	System.out.println(s);		// property displays 
            }
            
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.printf("%-48s"+"| %-14.2f"+"| %-30.2f"+"| %-14.2f"+"| %-14.2f\n","TOTAL ",totalRent,totalExpense,totalFees,totalNet);
    	}
    	else if(reportEmpty)	// boolean that detects the clients that do not own anything, and display them over here 
    	{	
    		System.out.println("\n\nPORTFOLIO REPORT\n"+"Client: (" +c.getLastName()+", " + c.getFirstName() + "), "+c.getStreetAddress()+" "+c.getSuburb()+" "+c.getState()+" "+c.getPostCode());
			System.out.println("Report Generated: "+dtf.format(now));
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.println("Property\t\t\t\t\t"+"| Rent\t\t"+"| Expenses\t"+"| Fee Rate\t"+"| Fees\t\t"+"| Net");
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.println("There are no records for this client");
    	}
        
        return ownsProperty;
	}

	// method overloading for efficient code as well as neater looking for each print option in menu selection 3
	
	public static boolean printReport(int clientIndex, boolean reportEmpty)
	{
		return printReport(clientIndex, null, reportEmpty);
	}
	
	public static boolean printReport(int clientIndex, String postcode)
	{
		return printReport(clientIndex, postcode, false);
	}

	public static void sortArrayList() {
		// sort clients 
		Collections.sort(clients, new Comparator<Client_20056663>() {
			@Override
			public int compare(Client_20056663 arg0, Client_20056663 arg1) {
				// sorts by last name first
				int a = arg0.getLastName().compareTo(arg1.getLastName()); 
				return a == 0 ? // if last names are the same
						arg0.getFirstName().compareTo(arg1.getFirstName()) : a; // sort by first
			}
			
		});
		
	}
	
	public static void saveFiles() {
		if (newExpenses.isEmpty() && newRents.isEmpty()) { 
			System.out.println("You have not made any changes");	// if user hasnt selected menu option 1 or 2, this msg will popup indicating that there isnt anything to save
		}

		if (!newRents.isEmpty()) { 	// since user has selected this option ( we have stored the new user data ) , we then procesed to save
			String RentsExist = "rents.txt";
			File RentsFile = new File(RentsExist);	
			PrintWriter rentsWriter;

			try {

				rentsWriter = new PrintWriter(new FileOutputStream(RentsFile, true));
				for(Rents_20056663 r : newRents) 
				{
				rentsWriter.print(r.getPropertyID() + "," + r.getRentAmount() + ","+ r.getDate() + "\n");	// writes the inputed data to the file
				}
				rentsWriter.close();
				
			} catch (FileNotFoundException e) {
				System.out.println("You have no Saved Changes from Rents File");
			}
			System.out.println("You have Succesfully Saved Your Rents Files");
			newRents.clear(); // clear at the end of the process, so for future saves, we dont duplicate the previous data
		}

		if(!newExpenses.isEmpty())  	// since user has selected this option ( we have stored the new user data ) , we then procesed to save
		{
			
			String ExpensesExist = "expenses.txt";
			File ExpensesFile = new File(ExpensesExist);
			PrintWriter expenseWriter;

			try {
				
				expenseWriter = new PrintWriter(new FileOutputStream(ExpensesFile, true));
				for(Expense_20056663 e : newExpenses)
				{
					expenseWriter.println(e.getPropertyID() + "," + e.getExpenseDescription()+ "," + e.getExpenseAmount() + "," + e.getDate()); // writes the inputed data to the file
				}
				expenseWriter.close();

			}
		
			catch (FileNotFoundException e1) {
				System.out.println("You have no Saved Changes from Expense File");
			}

			System.out.println("You have Succesfully Saved Your Expenses Files");
			newExpenses.clear(); // clear at the end of the process, so for future saves, we dont duplicate the previous data
			

		}
	}
	
	
	
	public static void generateReport() {
		System.out.println("Enter the number that corresponds to your option  :\n");
		System.out.println("1. Generate a report for a specific client.");
		System.out.println("2. Generate a report for all clients. ");							//menu display
		System.out.println("3. Generate a report for all properties in a specific postcode.");
		int portfolioInput = numberInput(0, null, "retryMessage");// number input , validates the input
		
		while(!(portfolioInput <= 3 && portfolioInput >= 1)) {
		System.out.println("Invalid Index number, enter from (1-3) ");	// VALIDATION
		portfolioInput = numberInput(0, null, "retryMessage");// number input , validates the input
		}
		
		if (portfolioInput == 1) {
			System.out.println("Search for client name: ");
			String input = kb.nextLine();
			input = kb.nextLine();
			
			ArrayList<Integer> clientIndexes = new ArrayList<>();
			
			for(int i = 0; i < clients.size(); i++) 
			{
				Client_20056663 c = clients.get(i);
				
				if (clients.get(i).getClientName().toLowerCase().contains(input.toLowerCase())) { // prints results, and stores in a arraylist
					clientIndexes.add(i); // adds to the list if results are matches 
					printReport(i, true);;
				}
			}
			if(clientIndexes.isEmpty()) { // if the arraylist returns no values, then search hasnt found anything
				System.out.println("Search returned no results.");
				return;
			}

		}
		else if (portfolioInput == 2) {
			sortArrayList(); // sorting the array list in ascending order
			for (int i = 0; i < clients.size(); i++) {
				printReport(i, true);
			}

		}
		
		
		else if (portfolioInput == 3) {
			
			System.out.println("Please Enter A Postcode You Would Like To Generate A Report For");
			
			String postcodeInput = kb.nextLine();
			postcodeInput = kb.nextLine();
			int postcodeInputLength = String.valueOf(postcodeInput).length();
			
			while(postcodeInputLength != 4) {
				System.out.println("Enter A Valid PostCode");	// this makes sure that the length of postcode is 4 number characters, else the program wont proceed until this is accomplished by user
				postcodeInput = kb.nextLine();
				postcodeInputLength = String.valueOf(postcodeInput).length();
			}
			
			boolean thereAreProperties = false;
			for (int i = 0; i < clients.size(); i++) {
				
				boolean thereArePropertiesForThisPerson = printReport(i, postcodeInput);	// print report for postcodes that correspond to users postcode input
				if(thereArePropertiesForThisPerson)
				{
					thereAreProperties = true;
				}
				
			}
			
			if(!thereAreProperties) System.out.println("No properties matched this postcode.");	// if this boolean doesnt set as true (doesnt contain any properties with postcode input), prints that there anrent any properties found for that postcode
		} 
		
		
	}
	
	
	public static int userInputValidation() { // this method makes sure that the user enters a number only, thats within the range of 1-5
		int choice = -1;
		do {

			System.out.println("\n\n1. Record Rent Collection.\n2. Record Expense.\n3. Generate Portfolio Report.\n4. Save.\n5. Exit Program.");
			System.out.println("\nEnter a number (1-5)");
			try {
				choice = kb.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Enter a NUMBER only! "); // if user inputs a string or anything thats not a number, this exectues
				kb.next();
			}

		} while (choice < 1 || choice > 5);// satisfies the range of user to enter 1-5 only
		return choice;
	}

	public static void RecordingRentCollection() {
		int ClientIndexMatch = 0;
		ArrayList<Integer> streetsToSelect = new ArrayList<Integer>();

		System.out.println("Enter an Address");
		String input = kb.nextLine();
		input = kb.nextLine();

		for (int i = 0; i < properties.size(); i++) {
			Property_20056663 p = properties.get(i); // simple variable that makes the code simple, and to improve readability

			if (p.getStreetAddress().toLowerCase().contains(input.toLowerCase())) {

				streetsToSelect.add(i);
				System.out.println((streetsToSelect.size() + ". ") + p.getStreetAddress()+", "+p.getSuburb()+", "+p.getState()+", "+p.getPostCode());
			}

		}
		if (streetsToSelect.isEmpty()) {
			System.out.println("This addres is not Available"); // IF the desired address that the user wants to find isnt found, then user will return to menu 
			return;

		}

		System.out.println("\nSelect your address from the index number");
		int selectAdd = numberInput(0, null, "retryMessage");// number input , validates the input

		// validation, if user doesnt enter the available index, re try and select an appropriate number
		while(!(selectAdd > 0 & selectAdd <= streetsToSelect.size())) {
			System.out.println("Invalid Index Number, Please select a number that corresponds to your street ");
			selectAdd = numberInput(0, null, "retryMessage");// number input , validates the input

		}
		
		
		int index = streetsToSelect.get(selectAdd - 1); //arrays start from 0, so i made sure that input -1 to line up with the arraylist index

		Property_20056663 p = properties.get(index);// simple variable that makes the code simple, and to improve readability
		ClientIndexMatch = p.getClientID();
		int ownerIndex = 0; // variable to hold the index of the owner of the property

		for (int x = 0; x < clients.size(); x++) {
			Client_20056663 c = clients.get(x);

			if (c.getClientID() == ClientIndexMatch) {
				ownerIndex = x;
				break; // once we find the owner, we dont need the rest of the list
			}
		}

		Client_20056663 o = clients.get(ownerIndex); // carry the index in another class to carry it for summary display and then we can access the owner outside of the for loop
		System.out.println("Street Address: " + properties.get(index).getStreetAddress()+", "+p.getSuburb()+", "+p.getState()+", "+p.getPostCode()
				+ "\nWeekly Rent For Charged This Address: " + p.getWeeklyRent() + "\nOwners Name Of This Property: "
				+ o.getClientName());

		System.out.println("\nHow many weeks of rent was collected for? " + p.getStreetAddress()+", "+p.getSuburb()+", "+p.getState()+", "+p.getPostCode());		
		double rentAmount = RentAmount(p.getWeeklyRent()); // gets weeklyamount from the metohd that caclulate and includes validation
		
			
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		
		Rents_20056663 u = new Rents_20056663(); // new class vairale to store new values in the arraylist
		u.setPropertyID(p.getPropertyID());
		u.setRentAmount(rentAmount);
		u.setDate(dtf.format(now));
		rents.add(u);
		newRents.add(u);	// adds new inputed value to an arraylist, that stores the new inputs , and is also used for saving in the save section
		
		System.out.println("\nOn Screen Summary of The rent Collection Transaction: ");

		System.out.format("\nProperty's Address: %-36s\n" + "The Number Of Weeks Rented: %-36d\n" // on screen summary
				+ "Owners Full Name Of This Property: %-36s\n" + "The Total Amount Of Rent: $%.2f\n" + "Date: %-36s",
				p.getStreetAddress()+", "+p.getSuburb()+", "+p.getState()+", "+p.getPostCode(), weeksOfrents, o.getClientName(), rentAmount, u.getDate());

			}
			
		
	
	public static void recordExpense() {
		int ClientIndexMatch = 0;
		int PropertyIdIndexMatch = 0;
		ArrayList<Integer> steetsFromExpenseToSelect = new ArrayList<Integer>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		
		System.out.println("Enter an Address");
		String input = kb.nextLine();
		input = kb.nextLine();

		for (int i = 0; i < properties.size(); i++) {

			Property_20056663 p = properties.get(i); // simple variable that makes the code simple, and to improve readability
			if (p.getStreetAddress().toLowerCase().contains(input.toLowerCase())) {
				
				steetsFromExpenseToSelect.add(i);
				System.out.println((steetsFromExpenseToSelect.size()+". ")  + p.getStreetAddress()+", "+p.getSuburb()+", "+p.getState()+", "+p.getPostCode());
			}

		}
		
		if (steetsFromExpenseToSelect.isEmpty()) {
			System.out.println("This addres is not Available");
			return;

		}
		
		System.out.println("\nSelect your address from the index number");
		int selectInput = numberInput(0, null, "retryMessage");
		
		
		// validation, if user doesnt enter the available index, re try and select an appropriate number
				while(!(selectInput > 0 & selectInput <= steetsFromExpenseToSelect.size())) {
					System.out.println("Invalid Index Number, Please select a number that corresponds to your street ");	 // VALIDATION
					selectInput = numberInput(0, null, "retryMessage");

				}
		
		
		int index1 = steetsFromExpenseToSelect.get(selectInput - 1);

			Property_20056663 p = properties.get(index1);// simple variable that makes the code simple, and to improve
													// readability
				ClientIndexMatch = p.getClientID();
				PropertyIdIndexMatch = p.getPropertyID();
				int ownerIndex = 0; // variable to hold the index of the owner of the property

				for (int x = 0; x < clients.size(); x++) {
					Client_20056663 c = clients.get(x);
					if (p.getStreetAddress().toLowerCase().contains(input.toLowerCase())) {
						if (c.getClientID() == ClientIndexMatch) {
							ownerIndex = x;
							break; // once we find the owner, we dont need the rest of the list
						}
					}
				}

				Client_20056663 o = clients.get(ownerIndex); // carry the index in another class to carry it for
															// summarydisplay and then we can access the owner
															// outside of the for loop
				System.out
						.println("Street Address: " + p.getStreetAddress()+", "+p.getSuburb()+", "+p.getState()+", "+p.getPostCode() + "\nWeekly Rent For Charged This Address: "
								+ p.getWeeklyRent() + "\nOwners Name Of This Property: " + o.getClientName());

				System.out.println("\nWould You Like To Record An Expesnse From This Property? (y/n)");
				input = kb.nextLine();

				while (!input.equals("n") && !input.equals("y")) {
					System.out.println("Please Enter (y/n) Yes or No");	// VALIDATION
					input = kb.nextLine();
				}

				if (input.equals("y")) {

					System.out.println("Please enter the Expense Description: ");
					String description = kb.nextLine();

					System.out.println("Please enter the Expense Amount: ");

					
					double expenseAmount = numberInput(0, null, "retryMessage"); // number input , validates the input
					

					
					Expense_20056663 ex = new Expense_20056663();// new class vairale to store new values in the arraylist
					ex.setPropertyID(PropertyIdIndexMatch);
					ex.setExpenseDescription(description);
					ex.setExpenseAmount(expenseAmount);
					ex.setDate(dtf.format(now));
					expenses.add(ex);							
					newExpenses.add(ex); // adds new inputed value to an arraylist, that stores the new inputs , and is also used for saving in the save section
							
					System.out.println("\nOn Screen Summary of The Record Expense: ");
					System.out.format("\nProperty's Address: %-36s\n" + "Expense Amount: %-36.2f\n" // on
							+ "Expense Description: %-36s" + "\nOwners Full Name Of This Property: %-36s"
							+ "\nDate: %-36s", p.getStreetAddress()+", "+p.getSuburb()+", "+p.getState()+", "+p.getPostCode(), expenseAmount, description, o.getClientName(),
							ex.getDate());
			
				}

				else 
				{
					System.out.println("No worries, You have been returned to the main menu");
				}

			}

	
	public static Integer numberInput(Integer lowerBound, Integer upperBound, String retryMessage)	//VALIDATION METHOD FOR STRING INPUT OR LESS THEN 0
	{
	  Integer input = null;
	  boolean inital = true;

	  while(input == null || (lowerBound != null && input < lowerBound) || (upperBound != null && input > upperBound) || input <= 0)
	  {
	    if(inital) 
	    inital = false;
	    else
	    {
	      System.out.println("Invalid Input, Please Enter a appropriate number"); 
	    }

	    try
	    {
	      input = kb.nextInt();
	    }
	    catch(InputMismatchException e) { 
	    kb.next();  // eat the "\n" character
	    }
	    
	  }
	  
	  return input;
	}
	
	public static double RentAmount(double weeklyRent) {

		System.out.print("Please enter an amount of rents (more than 0): ");

		weeksOfrents = numberInput(0, null, "retryMessage");	// number input , validates the input
		// rent amount process

		
		double rentAmount = weeksOfrents * weeklyRent; // rent calculation
		return rentAmount;
		}

	
	
	
	public static void storingClientsClass() {
		String clientExist = "clients.txt";
		File Clientsfile = new File(clientExist); // creating the text file Array
		while (!Clientsfile.exists()) { // if the file does not exist, then create the file so that it can exist
			System.out.println("Clients file does not exist, enter a new file name");
			clientExist = kb.nextLine() + ".txt";
			Clientsfile = new File(clientExist); // this updates after userinput so it sets it to the available filename
													// entered by the user

		}

		// storing the clients class in an arraylist
		try {

			br = new BufferedReader(new FileReader(Clientsfile));

			while ((line = br.readLine()) != null) {

				String[] values = line.split(",");// after every comma, represents a certain attribute
				Client_20056663 c = new Client_20056663();
				c.setClientID(Integer.parseInt(values[0]));
				c.setClientName(values[1]);
				c.setStreetAddress(values[2]);
				c.setSuburb(values[3]);
				c.setState(values[4]);
				c.setPostCode(values[5]);
				clients.add(c);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void storingPropertiesClass() {

		String PropertyExist = "properties.txt";
		File Propertiesfile = new File(PropertyExist); // creating the text file Array
		while (!Propertiesfile.exists()) { // if the file does not exist, then create the file so that it can exist
			System.out.println("Properties file does not exist, enter a new file name");
			PropertyExist = kb.nextLine() + ".txt";
			Propertiesfile = new File(PropertyExist); // this updates after userinput so it sets it to the available
														// filename entered by the user

		}

		// storing the properties class in an arraylist
		try {

			br = new BufferedReader(new FileReader(Propertiesfile));
			while ((line = br.readLine()) != null) {

				String[] values = line.split(",");// after every comma, represents a certain attribute

				Property_20056663 p = new Property_20056663();

				p.setPropertyID(Integer.parseInt(values[0]));
				p.setStreetAddress(values[1]);
				p.setSuburb(values[2]);
				p.setState(values[3]);
				p.setPostCode(values[4]);
				p.setWeeklyRent(Integer.parseInt(values[5]));
				p.setManagementFee(Double.parseDouble(values[6]));
				p.setClientID(Integer.parseInt(values[7]));
				properties.add(p);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void storingExpensesClass() {
		String ExpensesExist = "expenses.txt";
		File ExpensesFile = new File(ExpensesExist); // creating the text file Array
		while (!ExpensesFile.exists()) { // if the file does not exist, then create the file so that it can exist
			System.out.println("Expenses file does not exist, enter a new file name");
			ExpensesExist = kb.nextLine() + ".txt";
			ExpensesFile = new File(ExpensesExist); // this updates after userinput so it sets it to the available
													// filename entered by the user

		}
		// storing the expenses class in an arraylist
		try {

			br = new BufferedReader(new FileReader(ExpensesFile));
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");// after every comma, represents a certain attribute
				Expense_20056663 e = new Expense_20056663();
				e.setPropertyID(Integer.parseInt(values[0]));
				e.setExpenseDescription(values[1]);
				e.setExpenseAmount(Double.parseDouble(values[2]));
				e.setDate(values[3]);
				expenses.add(e);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void storingRentClass() {
		String RentsExist = "rents.txt";
		File RentsFile = new File(RentsExist); // creating the text file Array
		while (!RentsFile.exists()) { // if the file does not exist, then create the file so that it can exist
			System.out.println("Rents file does not exist, enter a new file name");
			RentsExist = kb.nextLine() + ".txt";
			RentsFile = new File(RentsExist); // this updates after userinput so it sets it to the available filename
												// entered by the user
		}

		// storing the rents class in an arraylist
		try {

			br = new BufferedReader(new FileReader(RentsFile));
			while ((line = br.readLine()) != null) {

				String[] values = line.split(",");// after every comma, represents a certain attribute
				Rents_20056663 r = new Rents_20056663();
				r.setPropertyID(Integer.parseInt(values[0]));
				r.setRentAmount(Double.parseDouble(values[1]));
				r.setDate(values[2]);
				rents.add(r);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}