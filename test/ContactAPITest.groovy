import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class ContactAPITest {
    Person p1;
    Person p2;
    Person p3;
    Contact c1;
    Contact c2;
    Contact c3;

    private ContactAPI TestAPI;

    @BeforeEach
    void setUp() {
        TestAPI = new ContactAPI();

        p1 = new Person("John", "Murphy", "M", "25/04/85");
        p2 = new Person("Jane", "Smith", "F", "23/02/83");
        p3 = new Person("Jack", "Thompson", "M", "12/04/01");

        c1 = new Contact(p1, "johnmurphy@gmail.com", "10 Faraway St, Brookford, London, UK", "0875432212");
        c2 = new Contact(p2, "janesmith@gmail.com", "12 Mayfield Lane, Dunmore, Co. Waterford, Ireland", "0876542342");
        c3 = new Contact(p3, "jackthompson@gmail.com", "Woodrow House, Gorey, Co. Wexford, Ireland", "0872345674");
    }

    @AfterEach
    void tearDown() {
        TestAPI = null;
    }


    @Nested
    @DisplayName("Create, Update and Delete Contacts")
    class createUpdateDelete {
        @Test
        @DisplayName("Add a Contact")
        void addContact() {
            TestAPI.addContact(c1);
            assertEquals(TestAPI.contacts.size(), 1);
        }

        @Test
        @DisplayName("Delete a Contact")
        void removeContact() {
            TestAPI.addContact(c1);
            assertEquals(TestAPI.contacts.size(), 1);
            TestAPI.removeContact(0);
            assertEquals(TestAPI.contacts.size(), 0);
        }

        @Test
        @DisplayName("Add a Person")
        void addPerson() {
            TestAPI.addPerson(p1);
            TestAPI.addPerson(p2);
            def people = TestAPI.people
            assertEquals(people.size(), 2);
        }

        @Test
        @DisplayName("Edit a Person")
        void editPerson() {
            TestAPI.addContact(c1);
            TestAPI.editPerson(c1, "John", "Smith", "25/04/85", "M");
            String lastName = p1.getLastName();
            assertEquals(lastName, "Smith");
        }

        @Test
        @DisplayName("Edit Contact Details")
        void editContactDetails() {
            TestAPI.addContact(c1);
            TestAPI.editContactDetails(c1, "johnmurphy@hotmail.com", "10 Faraway St, Brookford, London, UK", "0875432212");
            String email = c1.getEmail();
            assertEquals(email, "johnmurphy@hotmail.com");
        }
    }

    @Nested
    @DisplayName("Search and organise Contact List")
    class searchAndOrganise {
        @Test
        @DisplayName("Get Array List of Contacts")
        void getContacts() {
            TestAPI.addContact(c1);
            TestAPI.addContact(c2);
            def contacts = TestAPI.contacts;
            assertEquals(contacts.size(), 2);
        }

        @Test
        @DisplayName("Return specific contact with Email search")
        void findSpecificContactByEmail() {
            TestAPI.addContact(c1);
            TestAPI.addContact(c2);
            TestAPI.addContact(c3);

            def contacts = TestAPI.searchByEmail("johnmurphy@gmail.com");
            assertEquals(contacts.size(), 1);
            Person person = contacts.get(0).person;
            String name = person.getName();
            assertEquals(name, "John Murphy");
        }

        @Test
        @DisplayName("Return specific contact with Name search")
        void findSpecificContactByName() {
            TestAPI.addContact(c1);
            TestAPI.addContact(c2);
            TestAPI.addContact(c3);

            def contacts = TestAPI.searchByName("John Murphy");
            assertEquals(contacts.size(), 1);
            def person = contacts.get(0).person;
            String name = person.getName();
            assertEquals(name, "John Murphy");
        }

        @Test
        @DisplayName("Return multiple contacts with partial Email search")
        void findContactsByEmail() {
            TestAPI.addContact(c1);
            TestAPI.addContact(c2);
            TestAPI.addContact(c3);

            ArrayList<Contact> contacts = TestAPI.searchByEmail("gmail.com");
            assertEquals(contacts.size(), 3);
        }

        @Test
        @DisplayName("Return multiple contacts with partial Name search")
        void findContactsByName() {
            TestAPI.addContact(c1);
            TestAPI.addContact(c2);
            TestAPI.addContact(c3);

            def contacts = TestAPI.searchByName("J");
            assertEquals(contacts.size(), 3);
        }

        @Test
        @DisplayName("Return Contacts ArrayList index by Email")
        void getIndexByEmail() {
            TestAPI.addContact(c1);
            TestAPI.addContact(c2);
            TestAPI.addContact(c3);

            String email = c2.getEmail();
            int index = TestAPI.getIndexByEmail(email);
            assertEquals(index, 1);
        }

        @Test
        @DisplayName("Format and Print Search Results")
        void formatandPrint() {
            TestAPI.addContact(c1);
            def contacts = TestAPI.getContacts();

            int index = 1;
            String formattedIndex = String.format("%-3d: ", index);
            String name = "John Murphy";
            String formattedName = String.format("%-20s", name);
            String email = ("johnmurphy@gmail.com");
            String formattedEmail = String.format("%-30s", email);
            String testString = formattedIndex + formattedName + "email: " + formattedEmail + "D.O.B: " + c1.getPerson().getDOB() + "\n";

            String testString2 = TestAPI.formatResult(contacts);

            assertEquals(testString, testString2);
        }

        @Test
        @DisplayName("List Contacts Alphabetically by First Name")
        void sortFirstName() {
            TestAPI.addContact(c1);
            TestAPI.addContact(c2);
            TestAPI.addContact(c3);

            boolean resultNegative = false;
            def contacts = TestAPI.getContacts();
            def sortedContacts = TestAPI.sortFirstName(contacts);
            def firstPerson = sortedContacts.get(0).person;
            def secondPerson = sortedContacts.get(1).person;
            int result = firstPerson.firstName.compareToIgnoreCase(secondPerson.firstName);
            if (result < 0) {
                resultNegative = true;
            }
            assertTrue(resultNegative);
        }

        @Test
        @DisplayName("List Contacts Alphabetically by Last Name")
        void sortLastName() {
            TestAPI.addContact(c1);
            TestAPI.addContact(c2);
            TestAPI.addContact(c3);

            boolean resultNegative = false;
            def contacts = TestAPI.getContacts();
            def sortedContacts = TestAPI.sortLastName(contacts);
            def firstPerson = sortedContacts.get(0).person;
            def secondPerson = sortedContacts.get(1).person;
            int result = firstPerson.lastName.compareToIgnoreCase(secondPerson.lastName);
            if (result < 0) {
                resultNegative = true;
            }
            assertTrue(resultNegative);
        }
    }
}


