import java.util.ArrayList
import java.util.Collections

// this class contains many methods that serve as the storing, updating and fetching mechanisms for the information in the Address Book.

 class ContactAPI {
     private ArrayList<Contact> contacts
     private ArrayList<Person> people

     ContactAPI() {
         this.contacts = new ArrayList<>()
         this.people = new ArrayList<>()
     }


     ArrayList<Contact> getContacts() {
         return contacts
     }

     ArrayList<Person> getPeople() {
         contacts.each {
             people.add(it.getPerson())
         }
         return people
     }

     void addPerson(Person person) {
         people.add(person)
     }

     void addContact(Contact contact) {
         contacts.add(contact)
     }

     void removeContact(int index) {
         contacts.remove(index)
     }

     // the editPerson and editContactDetails class takes in information input by the user and uses setters to update the information about the selected contact
     void editPerson(Contact contact, String firstName, String lastName, String DOB, String gender) {
         Person person = contact.getPerson()
         person.setFirstName(firstName)
         person.setLastName(lastName)
         person.setDOB(DOB)
         person.setGender(gender)
     }

     void editContactDetails(Contact contact, String email, String address, String phoneNo) {
         contact.setEmail(email)
         contact.setAddress(address)
         contact.setPhoneNo(phoneNo)
     }

     /* The searchByEmail and searchByName methods are taking in a search term entered by the user, and is searching the list of stored contacts for a match
    If a match is found, this contact is added to the 'contactsFound' ArrayList which is returned by these methods
    The list of contacts is iterated over by a for loop, and for each match, a new entry is made to the 'contactsFound' ArrayList
     This allows the user to simply use a loose search term when searching for contacts instead of knowing a specific field value */

     ArrayList<Contact> searchByEmail(String emailEntered) {
         ArrayList<Contact> contactsFound = new ArrayList<>()
         boolean contactFound = false
         String lcEmail = emailEntered.toLowerCase()
         contacts.each {
             String lcContactEmail = it.getEmail().toLowerCase()
             if (lcContactEmail.contains(lcEmail)) {
                 contactsFound.add(it)
                 contactFound = true
             }
         }
         if (contactFound) {
             return contactsFound
         } else {
             return null
         }
     }

     ArrayList<Contact> searchByName(String nameEntered) {
         ArrayList<Contact> contactsFound = new ArrayList<>()
         boolean contactFound = false
         String lcName = nameEntered.toLowerCase()
         contacts.each {
             String lcContactName = it.getPerson().getName().toLowerCase()
             if (lcContactName.contains(lcName)) {
                 contactsFound.add(it)
                 contactFound = true
             }
         }
         if (contactFound) {
             return contactsFound
         } else {
             return null
         }
     }

     /* The getIndexByEmail method is taking in a text string containing an email address of a specific contact, and returning the index value indicating its position within the array
    this value is passed back to the deleteContact method in the ContactAPI, so the correct Contact will be deleted*/

     int getIndexByEmail(String emailEntered) {
         int contactIndex = 0
         contacts.each {
             if (emailEntered.equalsIgnoreCase(it.getEmail())) {
                 contactIndex = contacts.indexOf(it)
             }
         }
         return contactIndex
     }

     /* the formatandPrint method is taking in an ArrayList of contacts, and extracts basic information on that contact, and formats the text into
    an easily readable format which is returned by this method and can be printed by other methods in the ContactAPI */

     String formatResult(ArrayList<Contact> contacts) {
         int index = 1
         String returnStr = ""
         contacts.each {
             String formattedIndex = String.format("%-3d: ", index)
             String name = it.getPerson().getName()
             String contactEmail = it.getEmail()
             String formattedName = String.format("%-20s", name)
             String formattedEmail = String.format("%-30s", contactEmail)
             String contactStr = formattedIndex + formattedName + "email: " + formattedEmail + "D.O.B: " + it.getPerson().getDOB()
             returnStr += contactStr + "\n"
             index++
         }
         return returnStr
     }

     /*the sortFirstName and sortLastName methods are using the collections sort method, with custom comparators that can be viewed in the
    "SortByName" class file, to sort the list of contacts alphabetically by first or last name */

     ArrayList<Contact> sortFirstName(ArrayList<Contact> contacts) {
         Collections.sort(contacts, new SortByFirstName())
         return contacts
     }

     ArrayList<Contact> sortLastName(ArrayList<Contact> contacts) {
         Collections.sort(contacts, new SortByLastName())
         return contacts
     }

 }



