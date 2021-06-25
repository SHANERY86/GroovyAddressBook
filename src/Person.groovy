//This class sets the structure for the Person object with fields that stores their personal information.

 class Person {
     String firstName
     String lastName
     String gender
     String DOB


     void setGender(String gender) {
        if(gender.equals("M") || gender.equals("F") || gender.equals("m") || gender.equals("f")){
            this.gender = gender.toUpperCase()
        }
        else{
            this.gender = "Unspecified"
        }
    }

     String getName(){
         return firstName + " " + lastName
     }


     Person(String firstName,String lastName, String gender, String DOB) {
        this.firstName = firstName
        this.lastName = lastName
        this.gender = gender
        this.DOB = DOB
    }

     String toString(){
        return """ Name: $firstName $lastName
        \n D.O.B: $DOB
        "\n Gender: $gender""".stripIndent()
    }
}



