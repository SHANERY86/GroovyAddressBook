class SortByLastName implements Comparator<Contact> {
    @Override
     int compare(Contact a, Contact b) {
        Person p1 = a.getPerson() //this retrieves the person object from the contact object, which contains their name fields.
        Person p2 = b.getPerson()
        return p1.getLastName().compareToIgnoreCase(p2.getLastName()) //this lexicographically compares the strings, determining if it should come before or after it in an alphabetically organised list
    }
}

class SortByFirstName implements Comparator<Contact> {
    @Override
     int compare(Contact a, Contact b) {
        Person p1 = a.getPerson()
        Person p2 = b.getPerson()
        return p1.getName().compareToIgnoreCase(p2.getName())
    }
}
