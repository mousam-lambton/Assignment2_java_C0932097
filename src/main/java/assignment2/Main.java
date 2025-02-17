package assignment2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Read JSON files
            List<Person> people = readJsonFile(mapper, "person.json", new TypeReference<List<Person>>() {
            });
            List<BlogPost> posts = readJsonFile(mapper, "blogPosts.json", new TypeReference<List<BlogPost>>() {
            });

            // Validate author IDs
            validateAuthorIds(people, posts);

            // Create Blog instance
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

    /**
     * Validates that all blog posts have valid author IDs by checking against the list of people.
     */
    private static void validateAuthorIds(List<Person> people, List<BlogPost> posts) {
        // Create a Set of all valid author IDs from the people list for O(1) lookup
        Set<String> validAuthorIds = people.stream()
                .map(Person::getId)
                .collect(Collectors.toSet());

        // Find any post author IDs that don't exist in the valid authors set
        Set<String> invalidAuthorIds = posts.stream()
                .map(BlogPost::getAuthorId)
                .filter(id -> !validAuthorIds.contains(id))
                .collect(Collectors.toSet());

        // If any invalid author IDs were found, throw an exception
        if (!invalidAuthorIds.isEmpty()) {
            throw new IllegalStateException("Found posts with invalid author IDs: " + invalidAuthorIds);
        }
    }
}