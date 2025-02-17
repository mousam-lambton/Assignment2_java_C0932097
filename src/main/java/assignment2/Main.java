package assignment2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Read JSON files
            List<Person> people = readJsonFile(mapper, "person.json", new TypeReference<List<Person>>() {
            });
            List<BlogPost> posts = readJsonFile(mapper, "blogPosts.json", new TypeReference<List<BlogPost>>() {
            });

            // Create Blog instance, the validation for invalid author IDs is done in the constructor itself
            Blog blog = new Blog(posts, people);

            // Print statistics
            System.out.println("Total Posts: " + blog.getPosts().size());
            System.out.println("Total Contributors: " + blog.getContributors().size());

            // Demo getPostsByAuthorAge
            Integer sampleAge = 25;
            List<String> postsForAge = blog.getPostsByAuthorAge(sampleAge);
            System.out.println("Posts by authors aged " + sampleAge + ": " + postsForAge);

        } catch (IOException e) {
            System.err.println("Error reading JSON files: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Invalid data found in files : " + e.getMessage());
        }
    }

    private static <T> List<T> readJsonFile(ObjectMapper mapper, String filename, TypeReference<List<T>> type) throws IOException {
        return mapper.readValue(new File(filename), type);
    }
}