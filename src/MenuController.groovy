class MenuControl {
    Scanner scan
    ContactAPI cAPI

    MenuControl() {
        scan = new Scanner(System.in)
        cAPI = new ContactAPI()
    }
}

MenuControl mc = new MenuControl()
input = mc.scan
cAPI = mc.cAPI
bootstrapContacts()
mainMenu() //starts the program by displaying the main menu

    //mainMenuDisplay() is run with mainMenu(), and displays the options to the user. It awaits an input from the user and stores it as the variable "option" and then returns that variable to mainMenu()

    private int mainMenuDisplay() {
        int option = 0
        boolean correctInputTest = false   //this boolean is made true if the correct input is put in, it ensures a number is entered and not a letter
        while (!correctInputTest) {
            println("--------------------------------")
            println("\tADDRESS BOOK")
            println("--------------------------------")
            println("1 ) Add a contact")
            println("2 ) Delete a contact")
            println("3 ) Edit a contact")
            println("4 ) Search for a contact")
            println("5 ) List Contacts")
            println("--------------------------------")
            println("0 ) Exit")
            println("--------------------------------")
            println("===>")
            try {
                option = input.nextInt()  //this option is passed to the loginMenu() method
                input.nextLine()
                correctInputTest = true //the program will only get this far if there has not been an error with the 'option' input
            } catch (Exception e) { //if the user makes an input that is not an integer
                println("Incorrect Input: Expecting a number, press enter to continue..")
                input.nextLine() //these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                input.nextLine()
            }
        }
        return option
    }

    /* This mainMenu() method is run first. It runs the mainMenuDisplay method which presents the user with the options and has the functions that controls the actions when the user selects an option
    when a selection has been made in the mainMenuDisplay method, it is passed here to the switch function. This method will perform the appropriate actions if the cases 1-5 are selected,
    if a number above 5 is entered, it will follow the default path. If 0 is entered, it will skip the while(option !=0) loop and quit the program  */

    private void mainMenu() {
        int option = mainMenuDisplay()
        while (option != 0) {
            switch (option) {
                case 1:  //if 1 is entered by the user
                    addContact()  //this is the function that will add a new contact to the system, it is detailed below.
                    break
                case 2: //if 2 is entered by the user
                    println("--------------------------------")   //this is a header to declare the section of the program the user is headed
                    println("SELECT CONTACT FOR DELETION")
                    println("--------------------------------")
                    deleteContact()  //this is the function that will lead to the deletion of a contact, detailed below
                    break
                case 3:
                    println("--------------------------------")
                    println("SELECT CONTACT FOR EDIT")
                    println("--------------------------------")
                    Contact contactForEdit = searchOption()  //this will return a contact from the method searchOption that searches the address book for contacts based on search terms entered
                    editContact(contactForEdit) //passes the contact returned above to the method that can edit details about a contact
                    break
                case 4:
                    Contact searchResult = searchOption()  //this will return a contact from the method searchOption that searches the address book for contacts based on search terms entered
                    if(searchResult != null) {              //if a contact is found the below is run, this will give all of the information about that contact
                        println(searchResult)
                        println(" ")
                        println("Press enter to continue")
                        input.nextLine()
                        mainMenu()  //once the user has reviewed the information about the contact, after they press a key they will be brought back to the main menu
                    }
                    break
                case 5:
                    listContacts()  //this will direct the user to the method that can list all contacts in alphabetical order
                    break
                default: //if any number apart from 1-5 or 0 is entered
                    println("Invalid option entered: " + option)
                    println("Press enter to continue..")
                    input.nextLine()
                    mainMenu()
                    break
            }
            println("Press enter to continue..")
            input.nextLine() //these are to absorb the carriage return that remains in the scanner buffer after an integer input.
            input.nextLine()

            option = mainMenuDisplay()
        }
        println("Exiting the system, good bye!")  //if 0 is entered, the program will perform these lines
        System.exit(0)
    }

    /* The addContact method is used to add a new contact, it prompts the user for information about the new Person, and creates a new Person.
    Then it will prompt the user for contact information for this person, and then adds the Person and the extra contact information to the creation of a New Contact */
    private void addContact() {
        println("Please enter a first Name:")
        String firstName = input.nextLine()
        println("Please enter a last Name:")
        String lastName = input.nextLine()
        println("Please enter an email address:")
        String email = input.nextLine()
        println("Please enter a Date Of Birth:")
        String dob = input.nextLine()
        println("Please enter a Gender(input M or F, or leave blank if you wish):")
        String gender = input.nextLine()
        println("Please enter an Address:")
        String address = input.nextLine()
        println("Please enter a Phone Number:")
        String number = input.nextLine()
        Person newPerson = new Person(firstName, lastName, gender, dob)   //New Person created
        cAPI.addPerson(newPerson) //adds the new person to the API that stores the AddressBook info
        Contact newContact = new Contact(newPerson, email, address, number) //New Contact created
        cAPI.addContact(newContact)  //Adds the new contact to the API that stores the AddressBook Info
        println("Press enter to continue..")
        input.nextLine()
        mainMenu() //when complete, returns user to the main menu
    }

    //the deleteContact method will delete a contact, it will run the searchOption method to find and select a user to delete
    private void deleteContact() {
        boolean decision = false //this boolean is used to ensure the user makes a correct input in the check to ensure if they want to delete the user
        String lcResponse = ""
        Contact contact = searchOption()  //returns a contact from the method that searches the list of contacts
        println(contact) //prints the contact returned
        println(" ")
        while(!decision) {  // this while loop will cycle until the user inputs a definitive Y or N that they are sure about deleting the contact
            println("Delete this contact (Y/N?)")  //prompts the user to make sure if they want to delete this contact
            String response = input.nextLine()
            lcResponse = response.toLowerCase()  //this will convert Y or N input to lower case, to allow case insensitive control of user input
            if (lcResponse.equals("y") || lcResponse.equals("n")) {
                decision = true  //if the user inputs Y or N, then they have made a decision about deleting the contact and the while is broken allowing the app to continue
            }
        }
        if (lcResponse.equals("y")) {
            int contactIndex = cAPI.getIndexByEmail(contact.getEmail())  //if the user input Y, this will run the method that will get the position index of the contact within the search results.
            cAPI.removeContact(contactIndex) //this will delete the contact
            println("Contact deleted. Press Enter to Return to Main Menu")
            input.nextLine()
            mainMenu()
        }
        if(lcResponse.equals("n")){ //if user input N, these lines will run
            println("Deletion cancelled. Press Enter to Return to Main Menu")
            input.nextLine()
            mainMenu()
        }
    }


    /*editContactMenu is the user interface for editing a contact, it is run with the editContact function, and awaits an input from the user with respect to the presented options
    returns a value for option selected by the user. It is asking the user if what part of the contact they want to edit. Only 1,2 and 0 are valid options. Anything else will present an error and loop back.
     */
    private int editContactMenu() {
        int option = 0
        boolean correctInput = false  //boolean that is made true when a correct input is made
        while(!correctInput) { //until the user makes a correct input, this loop will cycle
            println("EDIT CONTACT")
            println("--------------------------")
            println("1 ) Edit personal details")
            println("2 ) Edit contact details")
            println("--------------------------")
            println("0 ) Return to Main Menu")
            println("--------------------------")
            println(" ===>")
            try {
                option = input.nextInt()
                correctInput = true  //when the user makes an input that doesnt result in an error, this boolean is made true
            }
            catch(Exception e){  //if the user inputs a letter instead of a number
                println("Incorrect input, expecting a number. Press enter to continue")
                input.nextLine()//these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                input.nextLine()
            }
        }
        return option
    }

    /* editContact contains the code that is run when the options seen in editContactMenu() are selected. A contact is passed from the searchOption method */
    private void editContact(Contact contact) {
        boolean decision = false  //boolean for input check
        println(contact) //the selected contact is presented to the user
        while(!decision){
            println("Edit this user? (Y/N)")  //user asked are they sure about editing this user
            String response = input.nextLine()
            String lcResponse = response.toLowerCase()
            if(lcResponse.equals("y")) {
                decision = true
            }
            if(lcResponse.equals("n")) {
                mainMenu()
            }
        }
        int option = editContactMenu()
        while (option != 0) {
            switch (option) {
                case 1: //if option 1 is selected
                    println("EDIT THIS CONTACT")   //user is prompted to enter new information
                    println("-----------------")
                    println(contact)
                    input.nextLine()
                    println("Please enter new first Name:")
                    String firstName = input.nextLine()
                    println("Please enter new last Name:")
                    String lastName = input.nextLine()
                    println("Please enter new Date Of Birth:")
                    String dob = input.nextLine()
                    println("Please enter new Gender(input M or F, or leave blank if you wish):")
                    String gender = input.nextLine()
                    cAPI.editPerson(contact, firstName, lastName, dob, gender) //new information is passed to the editPerson method, where it replaces the old information
                    break
                case 2:
                    println("EDIT THIS CONTACT")
                    println("-----------------")
                    println(contact)
                    input.nextLine()
                    println("Please enter new email:")
                    String email = input.nextLine()
                    println("Please enter new Address:")
                    String address = input.nextLine()
                    println("Please enter new PhoneNo:")
                    String phoneNo = input.nextLine()
                    cAPI.editContactDetails(contact, email, address, phoneNo)
                    break
                default: //if an option that isnt 1,2 or 0 is entered.
                    println("Invalid option entered: " + option)
                    println("Press enter to continue..")
                    input.nextLine()//these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                    input.nextLine()
                    break
            }
            option = editContactMenu() //returns to the edit contact menu when the cases break.
        } //if 0 is selected
        mainMenu()
    }

    /* the searchByEmail and searchByName methods are methods in which the user is prompted for a search term, and if a result is found it is stored and returned
     if more than one result is found, an array of results are presented to the user and they are prompted to make a selection, the selected contact is then stored and returned */

     Contact searchByEmail() {
        boolean correctInput = false
        ArrayList<Contact> contactsReturned
        input.nextLine()
        println("Please enter an search term for an email address (Enter blank to see all):")
        String email = input.nextLine()
        contactsReturned = cAPI.searchByEmail(email)  //search the list of contacts stored, returns an arraylist of contacts.
        if(contactsReturned != null) {
            if (contactsReturned.size() == 1) {
                return contactsReturned.get(0) //if only one result found, returns this contact
            }
            else //if more than one result found
                while(!correctInput) {  //this loop will cycle if the user makes an incorrect input in the result selection process
                    String result = cAPI.formatResult(contactsReturned) //formats the array of results in a manner that is easier to read
                    println(result) //prints the formatted results
                    println("Please enter a number to select a contact")
                    println("===>")
                    try {
                        int response = input.nextInt() //user response is recorded in this variable
                        input.nextLine()
                        int selection = response - 1  //the selection is the response minus one, as this will be the corresponding array list index value
                        if(selection < contactsReturned.size()) { //this ensures the user enters an appropriate number
                            correctInput = true //when appropriate value entered, this being true breaks while loop
                            Contact contactResult = contactsReturned.get(selection)
                            return contactResult
                        }
                        else  //this runs if incorrect value entered, these lines are run and loop cycles back
                            println("Incorrect Number Entered. Press Enter to Continue.")
                        input.nextLine()
                    } catch (Exception e) { //this runs if the user inputs a letter instead of a number in the selection
                        println("Incorrect Input. Expecting a number. Press Enter to Continue")
                        input.nextLine()//these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                        input.nextLine()
                    }
                }
        }
        else //if contactsReturned is null
            println("No Contacts Found. Press Enter to return to Main Menu")
        input.nextLine()
        mainMenu()
        return null
    }

    /* the searchByEmail and searchByName methods are methods in which the user is prompted for a search term, and if a result is found it is stored and returned
 if more than one result is found, an array of results are presented to the user and they are prompted to make a selection, the selected contact is then stored and returned */

     Contact searchByName() {
        boolean correctInput = false
        ArrayList<Contact> contactsReturned
        input.nextLine()
        println("Please enter a search term for a Name (Enter blank to see all):")
        String name = input.nextLine()
        contactsReturned = cAPI.searchByName(name)
        if(contactsReturned != null) {
            if(contactsReturned.size() == 1){
                return contactsReturned.get(0)
            }
            else
                while(!correctInput) {
                    String result = cAPI.formatResult(contactsReturned)
                    println(result)
                    println("Please enter a number to select a contact")
                    println("===>")
                    try {
                        int response = input.nextInt()
                        input.nextLine()
                        int selection = response - 1
                        if (selection < contactsReturned.size()) {
                            correctInput = true
                            Contact contactResult = contactsReturned.get(selection)
                            return contactResult
                        } else
                            println("Incorrect Number Entered. Press Enter to Continue.")
                        input.nextLine()
                    } catch (Exception e) {
                        println("Incorrect Input. Expecting a number. Press Enter to Continue")
                        input.nextLine()//these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                        input.nextLine()
                    }
                }
        }
        else
            println("No Contacts Found. Press Enter to return to Main Menu")
        input.nextLine()
        mainMenu()
        return null
    }

    /*
    The searchMenu method is run with searchOption() method, these function to allow the user to choose how they would like to search the address book, by name or by email
     */
     int searchMenu() {
        int option = 0
        boolean correctInput = false
        while (!correctInput) {
            println("SEARCH CONTACTS")
            println("-----------------------")
            println("1 ) Search by Name")
            println("2 ) Search by E-mail")
            println("-----------------------")
            println("0 ) Return to Main Menu")
            try {
                option = input.nextInt()
                correctInput = true
            } catch (Exception e) {
                println("Incorrect Input. Expecting a number. Press enter to continue")
                input.nextLine()//these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                input.nextLine()
            }
        }
        return option
    }

    /*
    1,2 and 0 are the only valid options, it has a while loop and a try catch function that will cycle if the user puts in the wrong inputs
     */

     Contact searchOption() {
        Contact returnContact = null
        int option = searchMenu()
        while (option != 0) {
            switch (option) {
                case 1:
                    returnContact = searchByName()
                    return returnContact
                case 2:
                    returnContact = searchByEmail()
                    return returnContact
                default:
                    println("Invalid option entered: " + option)
                    println("Press enter to continue..")
                    input.nextLine()//these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                    input.nextLine()
                    break
            }
            option = searchMenu()
        }
        mainMenu()
        return null
    }

    /*
    The listContacts menu is run with the listContacts function, and will allow the user the choice to sort the contacts alphabetically by first or last name
     */

    private int listContactMenu() {
        int option = 0
        boolean correctInput = false
        while(!correctInput) {
            println("LIST CONTACTS")
            println("--------------------------")
            println("1 ) Sort alphabetically by last name")
            println("2 ) Sort alphabetically by first name")
            println("--------------------------")
            println("0 ) Return to Main Menu")
            println("--------------------------")
            println("===>")
            try {
                option = input.nextInt()
                correctInput = true
            } catch (Exception e) {
                println("Incorrect Input. Expecting a number. Press Any Key to Continue")
                input.nextLine()//these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                input.nextLine()
            }
        }
        return option
    }

    private void listContacts() {
        contacts = cAPI.contacts
        int option = listContactMenu()
        while (option != 0) {
            switch (option) {
                case 1:
                    ArrayList<Contact> sortedContactsLastName = cAPI.sortLastName(contacts)  //this will sort the contacts list alphabetically by last name, see method in SortByName file
                    String result = cAPI.formatResult(sortedContactsLastName)
                    println(result)
                    println("Press enter to continue..")
                    input.nextLine()//these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                    input.nextLine()
                    break
                case 2:
                    ArrayList<Contact> sortedContactsFirstName = cAPI.sortFirstName(contacts) //this will sort the contacts list alphabetically by first name, see method in SortByName file
                    String result2 = cAPI.formatResult(sortedContactsFirstName)
                    println(result2)
                    println("Press enter to continue..")
                    input.nextLine()//these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                    input.nextLine()
                    break
                default:
                    println("Invalid option entered: " + option)
                    println("Press enter to continue..")
                    input.nextLine()//these are to absorb the carriage return that remains in the scanner buffer after an integer input.
                    input.nextLine()
                    break
            }
            option = listContactMenu()  //runs when cases break
        }
        mainMenu() //runs if zero is selected
    }


    /*
    This is the information fed to the app every time it starts. The address book is populated with these contacts on startup.
     */
    private void bootstrapContacts() {
        Person p1 = new Person("John","Murphy","M","25/04/85")
        Person p2 = new Person("Jane","Smith","F","23/02/83")
        Person p3 = new Person("Jack", "Thompson", "M","12/04/01")
        Person p4 = new Person("David","Harris","M","13/05/94")
        Person p5 = new Person("Joanne","Carroll","M","11/02/87")
        Person p6 = new Person("Mary","Doyle","F","29/04/80")
        Person p7 = new Person("Donal","Jones","M", "12/02/45")
        Person p8 = new Person("Vincent", "Matthews", "M", "12/09/85")
        Person p9 = new Person("Daryl","Murphy","M","19/03/87")
        Person p10 = new Person("Hannah","Smith","F","16/03/86")

        cAPI.addPerson(p1)
        cAPI.addPerson(p2)
        cAPI.addPerson(p3)
        cAPI.addPerson(p4)
        cAPI.addPerson(p5)
        cAPI.addPerson(p6)
        cAPI.addPerson(p7)
        cAPI.addPerson(p8)
        cAPI.addPerson(p9)
        cAPI.addPerson(p10)

        Contact c1 = new Contact(p1,"johnmurphy@gmail.com","10 Faraway St, Brookford, London, UK", "0875432212")
        Contact c2 = new Contact(p2, "janesmith@gmail.com", "12 Mayfield Lane, Dunmore, Co. Waterford, Ireland","0876542342")
        Contact c3 = new Contact(p3, "jackthompson@gmail.com", "Woodrow House, Gorey, Co. Wexford, Ireland", "0872345674")
        Contact c4 = new Contact(p4, "davidharris@gmail.com","Bradwick House, Marygate, Luton, UK", "0875442522")
        Contact c5 = new Contact(p5, "joannecarrol@gmail.com", "12 Briarfield Ave, Woodstock, Co.Wexford","0876544563")
        Contact c6 = new Contact(p6, "marydoyle@gmail.com", "16 Arberstown Place, Co. Waterford, Ireland","0873672342")
        Contact c7 = new Contact(p7, "donaljones@gmail.com", "North House, Mayfield, Co. Waterford, Ireland","087567842")
        Contact c8 = new Contact(p8, "vincematthews@gmail.com", "16 FurFair Lane, Meadowbrook, Milton Keynes, UK","0874563342")
        Contact c9 = new Contact(p9, "darylmurphy@gmail.com", "15 Chocolate Lane, Murrayfield, Scotland, UK","0875555342")
        Contact c10 = new Contact(p10, "hannahsmith@gmail.com", "Peafield, Ferrybank, Co. Waterford, Ireland","0876542342")

        cAPI.addContact(c1)
        cAPI.addContact(c2)
        cAPI.addContact(c3)
        cAPI.addContact(c4)
        cAPI.addContact(c5)
        cAPI.addContact(c6)
        cAPI.addContact(c7)
        cAPI.addContact(c8)
        cAPI.addContact(c9)
        cAPI.addContact(c10)
    }


