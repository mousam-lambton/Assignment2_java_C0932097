package assignment2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.extern.jackson.Jacksonized;

// Lombok annotations for generating boilerplate code
@Getter
@ToString
@EqualsAndHashCode
@Builder(builderClassName = "Builder", setterPrefix = "with")
@Jacksonized
public class Person {
    // Fields are final to ensure immutability
    private final String id;
    private final String firstName;
    private final String lastName;
    private final Integer age;
    private final String gender;

    // Private constructor for builder pattern, with Jackson annotations for deserialization
    private Person(@JsonProperty("id") String id, @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName, @JsonProperty("age") Integer age, @JsonProperty("gender") String gender) {
        validateInput(id, firstName, lastName, age);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    // Input validation for fields, this is static so it can be called from the Builder as well
    private static void validateInput(String id, String firstName, String lastName, Integer age) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }
        if (age != null && age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }

    // Builder class using validation from Person
    public static class Builder {
        public Person build() {
            validateInput(id, firstName, lastName, age);
            return new Person(id, firstName, lastName, age, gender);
        }
    }
}