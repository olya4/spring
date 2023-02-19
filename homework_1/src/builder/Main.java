package builder;

public class Main {
    public static void main(String[] args) {
        Person person = Person.builder()
                .firstName("Tom")
                .lastName("Johns")
                .middleName("Robert")
                .country("Canada")
                .age(25)
                .build();

        System.out.println(person.getFirstName());
        System.out.println(person.getMiddleName());
        System.out.println(person.getLastName());
        System.out.println(person.getAge());
    }
}
