package builder;

public class Person {
    private String firstName;
    private String lastName;
    private String middleName;
    private String country;
    private String address;
    private String phone;
    private int age;
    private String gender;

    private Person(Person.Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.middleName = builder.middleName;
        this.country = builder.country;
        this.address = builder.address;
        this.phone = builder.phone;
        this.age = builder.age;
        this.gender = builder.gender;
    }

    public static Person.Builder builder() {
        return new Person.Builder();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String middleName;
        private String country;
        private String address;
        private String phone;
        private int age;
        private String gender;

        public Builder() {
        }

        public Person.Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Person.Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Person.Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Person.Builder country(String country) {
            this.country = country;
            return this;
        }

        public Person.Builder address(String address) {
            this.address = address;
            return this;
        }

        public Person.Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Person.Builder age(int age) {
            if (age > 0) {
                this.age = age;
            }
            return this;
        }

        public Person.Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
