package assignment2Test;

import assignment2.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Testing if person created is valid and all fields are set correctly
    @Test
    public void testValidPersonCreation() {
        Person person = Person.builder()
                .withId("1")
                .withFirstName("Mousam")
                .withLastName("Dhakal")
                .withAge(25)
                .withGender("Male")
                .build();

        assertEquals("1", person.getId());
        assertEquals("Mousam", person.getFirstName());
        assertEquals("Dhakal", person.getLastName());
        assertEquals(Integer.valueOf(25), person.getAge());
        assertEquals("Male", person.getGender());
    }

    // Testing if validation works for null ID
    @Test
    public void testNullId() {
        assertThrows(IllegalArgumentException.class, () ->
                Person.builder()
                        .withFirstName("Mousam")
                        .withLastName("Dhakal")
                        .withAge(25)
                        .build()
        );
    }

    // Testing if validation works for blank first name
    @Test
    public void testBlankFirstName() {
        assertThrows(IllegalArgumentException.class, () ->
                Person.builder()
                        .withId("1")
                        .withFirstName(" ")
                        .withLastName("Dhakal")
                        .withAge(25)
                        .build()
        );
    }

    // Testing if validation works for null last name
    @Test
    public void testNullLastName() {
        assertThrows(IllegalArgumentException.class, () ->
                Person.builder()
                        .withId("1")
                        .withFirstName("Mousam")
                        .withAge(25)
                        .build()
        );
    }

    // Testing if validation works for blank last name
    @Test
    public void testNegativeAge() {
        assertThrows(IllegalArgumentException.class, () ->
                Person.builder()
                        .withId("1")
                        .withFirstName("Mousam")
                        .withLastName("Dhakal")
                        .withAge(-25)
                        .build()
        );
    }

    // Testing if serialization and deserialization works correctly with builder pattern
    @Test
    public void testJsonSerialization() throws Exception {
        Person person = Person.builder()
                .withId("1")
                .withFirstName("Mousam")
                .withLastName("Dhakal")
                .withAge(25)
                .withGender("Male")
                .build();

        String json = objectMapper.writeValueAsString(person);
        Person deserializedPerson = objectMapper.readValue(json, Person.class);

        assertEquals(person, deserializedPerson);
    }

    // Testing if deserialization works correctly with a JSON string
    @Test
    public void testJsonDeserialization() throws Exception {
        String json = "{"
                + "\"id\": \"1\","
                + "\"firstName\": \"Mousam\","
                + "\"lastName\": \"Dhakal\","
                + "\"age\": 25,"
                + "\"gender\": \"Male\""
                + "}";

        Person person = objectMapper.readValue(json, Person.class);
        assertEquals("1", person.getId());
        assertEquals("Mousam", person.getFirstName());
        assertEquals("Dhakal", person.getLastName());
        assertEquals(Integer.valueOf(25), person.getAge());
        assertEquals("Male", person.getGender());
    }
}