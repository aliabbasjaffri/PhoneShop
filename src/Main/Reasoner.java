package Main;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import PhoneShop.*;

class Reasoner {

	// The main Class Object holding the Domain knowledge

	// Generate the classes automatically with: Opening a command console and
	// type:
	// Path to YOUR-PROJECTROOT-IN-WORKSPACE\xjc.bat yourschemaname.xsd -d src
	// -p yourclasspackagename

	private PhoneShop phoneShop; //This is a candidate for a name change

	private SimpleGUI Myface;

	// The lists holding the class instances of all domain entities
	private List thePhoneShopList = new ArrayList(); //This is a candidate for a name change
	private List thePhoneList = new ArrayList();    //This is a candidate for a name change
	private List thePhoneSaleList = new ArrayList(); //This is a candidate for a name change
	private List thePhoneLeaseList = new ArrayList(); //This is a candidate for a name change
	private List theCustomerList = new ArrayList();  //This is a candidate for a name change
	private List theSalesmenList = new ArrayList(); //This is a candidate for a name change
	private List theRecentThing = new ArrayList();

	// Gazetteers to store synonyms for the domain entities names
	private Vector<String> phoneShopSyn = new Vector<String>();  //This is a candidate for a name change
	private Vector<String> phoneSyn = new Vector<String>();     //This is a candidate for a name change
	private Vector<String> phoneSaleSyn = new Vector<String>();   //This is a candidate for a name change
	private Vector<String> phoneLeaseSyn = new Vector<String>();  //This is a candidate for a name change
	private Vector<String> customerSyn = new Vector<String>();  //This is a candidate for a name change
	private Vector<String> salesmenSyn = new Vector<String>();  //This is a candidate for a name change
	private Vector<String> recentobjectsyn = new Vector<String>();

	private String questionType = "";         // questionType selects method to use in a query
	private List classtype = new ArrayList(); // classtype selects which class list to query
	public String attributetype = "";        // attributetype selects the attribute to check for in the query

	private Object Currentitemofinterest; // Last Object dealt with
	private Integer Currentindex;         // Last Index used

	private String tooltipstring = "";
	private String URL = "";              // URL for Wordnet site
	private String URL2 = "";             // URL for Wikipedia entry

	Reasoner(SimpleGUI myface) {

		Myface = myface; // reference to GUI to update Tooltip-Text
		// basic constructor for the constructors sake :)
	}

	void initknowledge() { // load all the library knowledge from XML

		JAXB_XMLParser xmlHandler = new JAXB_XMLParser(); // we need an instance of our parser

		//This is a candidate for a name change
		File xmlFileToLoad = new File("PhoneShop.xml"); // we need a (CURRENT)  file (xml) to load

		// Init synonmys and typo forms in gazetteers
		phoneShopSyn.add("shop");   	//This is a candidate for a name change
		phoneShopSyn.add("place");		//This is a candidate for a name change
		phoneShopSyn.add("store");	//This is a candidate for a name change
		phoneShopSyn.add("market"); 	//This is a candidate for a name change
		phoneShopSyn.add("phoneshop");		//This is a candidate for a name change

		phoneSyn.add("phone");    //All of the following is a candidate for a name change
		phoneSyn.add("phon");
		phoneSyn.add("mobile");
		phoneSyn.add("mobile phone");
		phoneSyn.add("cell");
		phoneSyn.add("cellphone");
		phoneSyn.add("mob");
		phoneSyn.add("mobil");

		phoneSaleSyn.add("sold"); //All of the following is a candidate for a name change
		phoneSaleSyn.add("sell");
		phoneSaleSyn.add("phones sold");
		phoneSaleSyn.add("phones sell");
		phoneSaleSyn.add("phone sold");

		phoneLeaseSyn.add("phone for lease"); //All of the following is a candidate for a name change
		phoneLeaseSyn.add("lease");
		phoneLeaseSyn.add("for leasing");
		phoneLeaseSyn.add("leasing");
		phoneLeaseSyn.add("leasing plan");
		phoneLeaseSyn.add("installments");
		phoneLeaseSyn.add("installment option");
		phoneLeaseSyn.add("lend");
		phoneLeaseSyn.add("lending");
		phoneLeaseSyn.add("lending options");

		customerSyn.add("customer");   //All of the following is a candidate for a name change
		customerSyn.add("customer name");   //All of the following is a candidate for a name change
		customerSyn.add("customers");   //All of the following is a candidate for a name change

		salesmenSyn.add("salesman");  //All of the following is a candidate for a name change
		salesmenSyn.add("sales person");
		salesmenSyn.add("shop keeper");
		salesmenSyn.add("attendant");

		recentobjectsyn.add(" this");   //All of the following is a candidate for a name change
		recentobjectsyn.add(" that");
		recentobjectsyn.add(" him");
		recentobjectsyn.add(" her");	// spaces to prevent collision with "wHERe"	
		recentobjectsyn.add(" it");

		try {
			FileInputStream readThatFile = new FileInputStream(xmlFileToLoad); // initiate input stream

			phoneShop = xmlHandler.loadXML(readThatFile);

			// Fill the Lists with the objects data just generated from the xml
			thePhoneList = phoneShop.getPhones();  		//This is a candidate for a name change
			thePhoneSaleList = phoneShop.getPhonesSold(); 	//This is a candidate for a name change
			thePhoneLeaseList = phoneShop.getPhonesLeased(); 	//This is a candidate for a name change
			theCustomerList = phoneShop.getCustomers(); 	//This is a candidate for a name change
			theSalesmenList = phoneShop.getSalesmen(); 	//This is a candidate for a name change
			thePhoneShopList.add(phoneShop);             // force it to be a List, //This is a candidate for a name change

			System.out.println("List reading");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("error in init");
		}
	}

	Vector<String> generateAnswer(String input) { // Generate an answer (String Vector)

		Vector<String> out = new Vector<String>();
		out.clear();                 // just to make sure this is a new and clean vector

		questionType = "none";

		Integer Answered = 0;        // check if answer was generated

		Integer subjectcounter = 0;  // Counter to keep track of # of identified subjects (classes)
		
		// Answer Generation Idea: content = Questiontype-method(classtype class) (+optional attribute)

		// ___________________________ IMPORTANT _____________________________

		input = input.toLowerCase(); // all in lower case because thats easier to analyse
		
		// ___________________________________________________________________

		String answer = "";          // the answer we return

		// ----- Check for the kind of question (number, location, etc)------------------------------

		if (input.contains("how many")){
			questionType = "amount"; input = input.replace("how many", "<b>how many</b>");}
		if (input.contains("number of")){
			questionType = "amount"; input = input.replace("number of", "<b>number of</b>");}
		if (input.contains("amount of")){
			questionType = "amount"; input = input.replace("amount of", "<b>amount of</b>");}
		if (input.contains("count")){
			questionType = "amount"; input = input.replace("count", "<b>count</b>");}

		if (input.contains("what kind of")){
			questionType = "list"; input = input.replace("what kind of", "<b>what kind of</b>");}
		if (input.contains("list all")){
			questionType = "list"; input = input.replace("list all", "<b>list all</b>");}
		if (input.contains("diplay all")){
			questionType = "list"; input = input.replace("diplay all", "<b>diplay all</b>");}

		if (input.contains("is there a")){
			questionType = "checkfor"; input = input.replace("is there a", "<b>is there a</b>");}
		if (input.contains("i am searching")){
			questionType = "checkfor"; input = input.replace("i am searching", "<b>i am searching</b>");}
		if (input.contains("i am looking for")){
			questionType = "checkfor"; input = input.replace("i am looking for", "<b>i am looking for</b>");}
		if (input.contains("do you have")&&!input.contains("how many")){
			questionType = "checkfor";input = input.replace("do you have", "<b>do you have</b>");}
		if (input.contains("i look for")){
			questionType = "checkfor"; input = input.replace("i look for", "<b>i look for</b>");}
		if (input.contains("is there")){
			questionType = "checkfor"; input = input.replace("is there", "<b>is there</b>");}

		if (input.contains("where") 
				|| input.contains("can't find")
				|| input.contains("can i find") 
				|| input.contains("way to"))

		{
			questionType = "location";
			System.out.println("Find Location");
		}
		if (input.contains("can i lend") 
				|| input.contains("can i borrow")
				|| input.contains("can i get the book")
				|| input.contains("am i able to")
				|| input.contains("could i lend") 
				|| input.contains("i want to lend")
				|| input.contains("i want to borrow"))

		{
			questionType = "intent";
			System.out.println("Find BookAvailability");
		}
		
		if (input.contains("thank you") 
				|| input.contains("bye")
				|| input.contains("thanks")
				|| input.contains("cool thank")) 			

		{
			questionType = "farewell";
			System.out.println("farewell");
		}


		// ------- Checking the Subject of the Question --------------------------------------

		for (String aPhoneSyn : phoneSyn) {   //This is a candidate for a name change
			if (input.contains(aPhoneSyn)) {    //This is a candidate for a name change
				classtype = thePhoneList;             //This is a candidate for a name change
				input = input.replace(aPhoneSyn, "<b>" + aPhoneSyn + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Phone recognised.");
			}
		}
		for (String aPhoneSaleSyn : phoneSaleSyn) {  //This is a candidate for a name change
			if (input.contains(aPhoneSaleSyn)) {   //This is a candidate for a name change
				classtype = thePhoneSaleList;            //This is a candidate for a name change
				input = input.replace(aPhoneSaleSyn, "<b>" + aPhoneSaleSyn + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Phone Sale recognised.");
			}
		}
		for (String aPhoneLeaseSyn : phoneLeaseSyn) {  //This is a candidate for a name change
			if (input.contains(aPhoneLeaseSyn)) {   //This is a candidate for a name change
				classtype = thePhoneLeaseList;            //This is a candidate for a name change
				input = input.replace(aPhoneLeaseSyn, "<b>" + aPhoneLeaseSyn + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Phone Lease recognised.");
			}
		}
		for (String aCustomerSyn : customerSyn) {  //This is a candidate for a name change
			if (input.contains(aCustomerSyn)) {   //This is a candidate for a name change
				classtype = theCustomerList;            //This is a candidate for a name change
				input = input.replace(aCustomerSyn, "<b>" + aCustomerSyn + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Customer recognised.");
			}
		}
		for (String aSalesmanSyn : salesmenSyn) {  //This is a candidate for a name change
			if (input.contains(aSalesmanSyn)) {   //This is a candidate for a name change
				classtype = theSalesmenList;            //This is a candidate for a name change
				input = input.replace(aSalesmanSyn, "<b>" + aSalesmanSyn + "</b>");
				subjectcounter = 1;
				System.out.println("Class type Salesman recognised.");
			}
		}
		
		if(subjectcounter == 0)
		{
			for (String aRecentobjectsyn : recentobjectsyn) {
				if (input.contains(aRecentobjectsyn)) {
					classtype = theRecentThing;
					input = input.replace(aRecentobjectsyn, "<b>" + aRecentobjectsyn + "</b>");
					subjectcounter = 1;
					System.out.println("Class type recognised as" + aRecentobjectsyn);
				}
			}
		}
		// More than one subject in question + Library ...
		// "Does the Library has .. Subject 2 ?"

		System.out.println("subjectcounter = "+subjectcounter);

		for (String aPhoneShopSyn : phoneShopSyn) {  //This is a candidate for a name change
			if (input.contains(aPhoneShopSyn)) {   //This is a candidate for a name change
				// Problem: "How many Books does the Library have ?" -> classtype = Library
				// Solution:
				if (subjectcounter == 0) { // Library is the first subject in the question
					input = input.replace(aPhoneShopSyn, "<b>" + aPhoneShopSyn + "</b>");
					classtype = thePhoneShopList;        //This is a candidate for a name change
					System.out.println("class type Phone Shop recognised");
				}
			}
		}

		// Compose Method call and generate answerVector

		if (questionType.equals("amount")) { // Number of Subject
			Integer numberof = Count(classtype);
			answer=("The number of "
					+ classtype.get(0).getClass().getSimpleName() + "s is "
					+ numberof + ".");
			Answered = 1; // An answer was given
		}

		if (questionType.equals("list")) { // List all Subjects of a kind
			answer=("You asked for the listing of all "
					+ classtype.get(0).getClass().getSimpleName() + "s. <br>"
					+ "We have the following "
					+ classtype.get(0).getClass().getSimpleName() + "s:"
					+ ListAll(classtype));
			Answered = 1; // An answer was given
		}

		if (questionType.equals("checkfor")) { // test for a certain Subject instance
			Vector<String> check = CheckFor(classtype, input);
			answer=(check.get(0));
			Answered = 1; // An answer was given
			if (check.size() > 1) {
				Currentitemofinterest = classtype.get(Integer.valueOf(check
						.get(1)));
				System.out.println("Classtype List = "
						+ classtype.getClass().getSimpleName());
				System.out.println("Index in Liste = "
						+ Integer.valueOf(check.get(1)));
				Currentindex = Integer.valueOf(check.get(1));
				theRecentThing.clear(); // Clear it before adding (changing) the
				// now recent thing
				theRecentThing.add(classtype.get(Currentindex));
			}
		}

		// Location Question in Pronomial form "Where can i find it"

		if (questionType.equals("location")) {   // We always expect a pronomial question to refer to the last
											// object questioned for
			answer=("You can find the "
					+ classtype.get(0).getClass().getSimpleName() + " " + "at "
					+ Location(classtype, input));
			Answered = 1; // An answer was given
		}

		if ((questionType.equals("intent") && classtype == thePhoneList)
				|| (questionType.equals("intent") && classtype == theRecentThing))
		{
			// Can I lend the book or not (Can I lent "it" or not)
			answer=("You "+ phoneAvailable(classtype, input));
			Answered = 1; // An answer was given
		}

		if (questionType.equals("farewell")) {       // Reply to a farewell
			answer=("You are welcome.");
			Answered = 1; // An answer was given
		}
		
		if (Answered == 0) { // No answer was given
			answer=("Sorry I didn't understand that.");
		}

		out.add(input);
		out.add(answer);
		
		return out;
	}

	// Methods to generate answers for the different kinds of Questions
	// Answer a question of the "Is a book or "it (meaning a book) available ?" kind

	private String phoneAvailable(List theList, String input) {

		boolean available = false;
		String answer ="";
		Phone phone;
		String phoneInformation="";

		if (theList == thePhoneList) {                      //This is a candidate for a name change
			//Identify which phone is asked for
			for (Object aPhone : theList){
				phone = (Phone) aPhone;         //This is a candidate for a name change
				if (input.contains(phone.getName().toLowerCase())            //This is a candidate for a name change
						|| input.contains(phone.getMake().toLowerCase())      //This is a candidate for a name change
						|| input.contains(phone.getModel().toLowerCase())) {  //This is a candidate for a name change
					theRecentThing.clear(); 									//Clear it before adding (changing) the
					classtype = thePhoneList;                                    //This is a candidate for a name change
					theRecentThing.add(phone);
					phoneInformation = phone.getName();
					if (input.contains(phone.getName().toLowerCase())){input = input.replace(phone.getName().toLowerCase(), "<b>"+phone.getName().toLowerCase()+"</b>");}
					if (input.contains(phone.getMake().toLowerCase())) {input = input.replace(phone.getMake().toLowerCase(), "<b>"+phone.getMake().toLowerCase()+"</b>");}
					if (input.contains(phone.getModel().toLowerCase())){input = input.replace(phone.getModel().toLowerCase(), "<b>"+phone.getModel().toLowerCase()+"</b>");}
					available = true;
					break; 									// force break
				}
			}
		}
		// maybe other way round or double
		if (theList == theRecentThing && theRecentThing.get(0) != null) {
			if (theRecentThing.get(0).getClass().getSimpleName()
					.toLowerCase().equals("phone")) {                  //This is a candidate for a name change
				phone = (Phone) theRecentThing.get(0);               //This is a candidate for a name change
				phoneInformation = phone.getName();
				available = true;
			}
		}

		if(available){
			answer = "Phone is Available.";
		}
		else{ 
			answer = "Phone is not available.";
		}

		URL = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http://en.wikipedia.org/wiki/"
				+ phoneInformation;
		System.out.println("URL = "+URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return answer;
	}

	// Answer a question of the "How many ...." kind 
	
	private Integer Count(List thelist) { // List "thelist": List of Class Instances (e.g. thePhoneList)

		//URL = "http://en.wiktionary.org/wiki/"		

		URL = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http://en.wikipedia.org/wiki/"
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		System.out.println("URL = "+URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return thelist.size();
	}

	// Answer a question of the "What kind of..." kind
	
	private String ListAll(List thelist) {

		String listemall = "<ul>";

		if (thelist == thePhoneList) {                                  //This is a candidate for a name change
			for (Object aPhone : thelist) {
				Phone phone = (Phone) aPhone;                  //This is a candidate for a name change
				listemall += "<li>" + (phone.getName() + "</li>");    //This is a candidate for a name change
			}
		}

		if (thelist == thePhoneSaleList) {                               //This is a candidate for a name change
			for (Object aPhoneSale : thelist) {
				PhoneSale phoneSale = (PhoneSale) aPhoneSale;             //This is a candidate for a name change
				listemall += "<li>" + (phoneSale.getReceiptID() + "</li>");                //This is a candidate for a name change
			}
		}

		if (thelist == thePhoneLeaseList) {                               //This is a candidate for a name change
			for (Object aPhoneLease : thelist) {
				PhoneLease phoneLease = (PhoneLease) aPhoneLease;             //This is a candidate for a name change
				listemall += "<li>" + (phoneLease.getReceiptID() + "</li>");                //This is a candidate for a name change
			}
		}

		if (thelist == theCustomerList) {                                //This is a candidate for a name change
			for (Object aCustomer : thelist) {
				Customer customer = (Customer) aCustomer;               //This is a candidate for a name change
				listemall += "<li>" + (customer.getName()  + "</li>");  //This is a candidate for a name change
			}
		}

		if (thelist == theSalesmenList) {                               //This is a candidate for a name change
			for (Object aSalesman : thelist) {
				Salesman salesman = (Salesman) aSalesman;             //This is a candidate for a name change
				listemall += "<li>" + (salesman.getName() + "</li>");      //This is a candidate for a name change
			}
		}

		listemall += "</ul>";

		URL = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http://en.wikipedia.org/wiki/"
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		System.out.println("URL = "+URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);
		
		return listemall;
	}

	// Answer a question of the "Do you have..." kind
	private Vector<String> CheckFor(List theList, String input) {

		Vector<String> yesOrNo = new Vector<String>();
		if (classtype.isEmpty()){
			yesOrNo.add("Class not recognised. Please specify if you are searching for a Phone, Customer, Salesman, Phone Lease or Phone Sale?");
		} else {
			yesOrNo.add("No we don't have such a "
				+ classtype.get(0).getClass().getSimpleName());
		}

		Integer counter = 0;

		if (theList == thePhoneList) {                         //This is a candidate for a name change
			for (Object aPhone : theList) {
				Phone phone = (Phone) aPhone;                           //This is a candidate for a name change
				if (input.contains(phone.getMake().toLowerCase())            //This is a candidate for a name change
						|| input.contains(phone.getModel().toLowerCase())      //This is a candidate for a name change
						|| input.contains(phone.getType().toLowerCase())) {  //This is a candidate for a name change
					counter = theList.indexOf(phone);
					yesOrNo.set(0, "Yes we have such a Phone");                  //This is a candidate for a name change
					yesOrNo.add(counter.toString());
					break;
				}
			}
		}

		if (theList == thePhoneSaleList) {                                     //This is a candidate for a name change
			for (Object aPhoneSale : theList ) {
				PhoneSale phoneSale = (PhoneSale) aPhoneSale;                  //This is a candidate for a name change
				if (input.contains(phoneSale.getReceiptID().toLowerCase())          //This is a candidate for a name change
						|| input.contains(phoneSale.getCustomerID().toLowerCase())){ //This is a candidate for a name change
					counter = theList.indexOf(phoneSale);
					yesOrNo.set(0, "Yes we have such a Sale");            //This is a candidate for a name change
					yesOrNo.add(counter.toString());
					break;
				}
			}
		}

		if (theList == thePhoneLeaseList) {                                     //This is a candidate for a name change
			for (Object aPhoneLease : theList ) {
				PhoneLease phoneLease = (PhoneLease) aPhoneLease;                  //This is a candidate for a name change
				if (input.contains(phoneLease.getReceiptID().toLowerCase())          //This is a candidate for a name change
						|| input.contains(phoneLease.getCustomerID().toLowerCase())){ //This is a candidate for a name change
					counter = theList.indexOf(phoneLease);
					yesOrNo.set(0, "Yes we have such a Lease");            //This is a candidate for a name change
					yesOrNo.add(counter.toString());
					break;
				}
			}
		}

		if (theList == theCustomerList) {                                      //This is a candidate for a name change
			for (Object aCustomer : theList){
				Customer customer = (Customer) aCustomer;                      //This is a candidate for a name change
				if (input.contains(customer.getName().toLowerCase())         //This is a candidate for a name change
						|| input.contains(customer.getCustomerID().toLowerCase()) //This is a candidate for a name change
						|| input.contains(customer.getMobileNumber().toLowerCase())) {  //This is a candidate for a name change
					counter = theList.indexOf(customer);
					yesOrNo.set(0, "Yes we have such a Customer");               //This is a candidate for a name change
					yesOrNo.add(counter.toString());
					break;
				}
			}
		}

		if (theList == theSalesmenList) {                                    //This is a candidate for a name change
			for (Object aSalesMan : theList){
				Salesman salesman = (Salesman) aSalesMan;                  //This is a candidate for a name change
				if (input.contains(salesman.getName().toLowerCase())          //This is a candidate for a name change
						|| input.contains(salesman.getSalesManID().toLowerCase())) { //This is a candidate for a name change
					counter = theList.indexOf(salesman);
					yesOrNo.set(0, "Yes we have such a Salesman");           //This is a candidate for a name change
					yesOrNo.add(counter.toString());
					break;
				}
			}
		}

		if (classtype.isEmpty()) {
			System.out.println("Not class type given.");
		} else {
			URL = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
			URL2 = "http://en.wikipedia.org/wiki/"
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
			System.out.println("URL = "+URL);
			tooltipstring = readwebsite(URL);
			String html = "<html>" + tooltipstring + "</html>";
			Myface.setmytooltip(html);
			Myface.setmyinfobox(URL2);
		}
		return yesOrNo;
	}

	//  Method to retrieve the location information from the object (Where is...) kind
	private String Location(List classTypeList, String input) {

		String location = "";
		// if a pronomial was used "it", "them" etc: Reference to the recent thing

		if (classTypeList == theRecentThing && theRecentThing.get(0) != null) {

			if (theRecentThing.get(0).getClass().getSimpleName()
					.toLowerCase().equals("phoneshop")) {                  //This is a candidate for a name change
				PhoneShop phoneShop = (PhoneShop) theRecentThing.get(0);          //This is a candidate for a name change
				location = (phoneShop.getShopName() + " " + phoneShop.getAddress() + " " + phoneShop.getContactNumber());             //This is a candidate for a name change
			}

			if (theRecentThing.get(0).getClass().getSimpleName()
					.toLowerCase().equals("customer")) {               //This is a candidate for a name change
				Customer customer = (Customer) theRecentThing.get(0);      //This is a candidate for a name change
				location = (customer.getName() + " " + customer.getMobileNumber() + " " + customer  //This is a candidate for a name change
						.getEmailAddress());                                    //This is a candidate for a name change
			}

			if (theRecentThing.get(0).getClass().getSimpleName()  
					.toLowerCase().equals("salesman")) {                 //This is a candidate for a name change
				Salesman salesman = (Salesman) theRecentThing.get(0);       //This is a candidate for a name change
				location = (salesman.getName() + " " + salesman.getDepartment());                //This is a candidate for a name change

			}
		}

		// if a direct noun was used (book, member, etc)
		else {
			if (classTypeList == theCustomerList) {                                         //This is a candidate for a name change
				for(Object aCustomer : classTypeList){
					Customer customer = (Customer) aCustomer;         				  //This is a candidate for a name change
					if (input.contains(customer.getName().toLowerCase())              //This is a candidate for a name change
							|| input.contains(customer.getMobileNumber().toLowerCase())      //This is a candidate for a name change
							|| input.contains(customer.getEmailAddress().toLowerCase())) {   //This is a candidate for a name change
						location = (customer.getCity() + " ");
						theRecentThing.clear(); 										// Clear it before adding (changing) the
						classtype = theCustomerList;            	 						//This is a candidate for a name change
						theRecentThing.add(customer);
						break; 				             	        // force break
					}
				}
			}
			if (classTypeList == theSalesmenList) {                                       	 //This is a candidate for a name change
				for (Object aSalesman : classTypeList){
					Salesman salesman = (Salesman) aSalesman;                    //This is a candidate for a name change
					if (input.contains(salesman.getName().toLowerCase())            //This is a candidate for a name change
							|| input.contains(salesman.getDepartment().toLowerCase())) {   //This is a candidate for a name change
						location = (salesman.getDepartment() + " ");
						theRecentThing.clear();                                      // Clear it before adding (changing) the	
						classtype = theSalesmenList;                                  //This is a candidate for a name change
						theRecentThing.add(salesman);
						break;                                      // force break
					}
				}
			}
			if (classTypeList == thePhoneShopList) {                                                  //This is a candidate for a name change

				location = (phoneShop.getShopName() + " " + phoneShop.getAddress() + phoneShop  //This is a candidate for a name change
						.getContactNumber());                                                   //This is a candidate for a name change
			}
		}

		URL = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http://en.wikipedia.org/wiki/"
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		System.out.println("URL = "+URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return location;
	}

	String testit() {   // test the loaded knowledge by querying for books written by dostoyjewski
		String answer = "";
		System.out.println("Phone List = " + thePhoneList.size());  //This is a candidate for a name change
		for (Object aThePhoneList : thePhoneList) {   // check each book in the List, //This is a candidate for a name change
			Phone phone = (Phone) aThePhoneList;    // cast list element to Book Class //This is a candidate for a name change
			System.out.println("Testing Phone" + phone.getMake());

			if (phone.getName().equalsIgnoreCase("6s")) {     // check for the author //This is a candidate for a name change

				answer = "A phone by " + phone.getMake() + "\n"  //This is a candidate for a name change
						+ " is a classic example for a " + phone.getType()      //This is a candidate for a name change
						+ ".";
			}
		}
		return answer;
	}

	private String readwebsite(String url) {

		String webtext = "";
		try {
			BufferedReader readit = new BufferedReader(new InputStreamReader(
					new URL(url).openStream()));

			String lineread = readit.readLine();

			System.out.println("Reader okay");

			while (lineread != null) {
				webtext = webtext + lineread;
				lineread = readit.readLine();				
			}

			// Hard coded cut out from "wordnet website source text": 
			//Check if website still has this structure   vvvv ...definitions...  vvvv 		
			
			webtext = webtext.substring(webtext.indexOf("<ul>"),webtext.indexOf("</ul>"));                                 //               ^^^^^^^^^^^^^^^^^              

			webtext = "<table width=\"700\"><tr><td>" + webtext
					+ "</ul></td></tr></table>";

		} catch (Exception e) {
			webtext = "Not yet";
			System.out.println("Error connecting to wordnet");
		}
		return webtext;
	}
}
