// This class sets the object structure for the Contact. It contains a Person object, and contact details for that person.

 class Contact {
     String email
     String address
     String phoneNo
     Person person

     Contact(Person person, String email, String address, String phoneNo){
        this.person = person;
        this.email = email;
        this.address = address;
        this.phoneNo = phoneNo;
    }

     String toString(){
        return """
                Name: $person.firstName $person.lastName 
                D.O.B: $person.DOB
                Gender: $person.gender
                email: $email
                Address: $address
                Phone Number: $phoneNo
                """.stripIndent()
    }
}

