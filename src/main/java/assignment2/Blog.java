package assignment2;

import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString
@EqualsAndHashCode
public class Blog {
    private final List<BlogPost> posts;
    private final List<Person> contributors;

    public Blog(List<BlogPost> posts, List<Person> contributors) {
        Objects.requireNonNull(posts, "Posts list cannot be null");
        Objects.requireNonNull(contributors, "Contributors list cannot be null");

        // Validate author IDs before setting posts and contributors and only set
        validateAuthorIds(posts, contributors);

        this.posts = new ArrayList<>(posts);
        this.contributors = new ArrayList<>(contributors);
    }

    // Check if all author IDs in posts are valid
    private static void validateAuthorIds(List<BlogPost> posts, List<Person> contributors) {
        // Create a Set of all valid author IDs from the people list for O(1) lookup
        Set<String> validAuthorIds = contributors.stream()
                .map(Person::getId)
                .collect(Collectors.toSet());

        // Find any post author IDs that don't exist in the valid authors set
        List<String> invalidAuthorIds = posts.stream()
                .map(BlogPost::getAuthorId)
                .filter(id -> !validAuthorIds.contains(id))
                .collect(Collectors.toList());

        // If any invalid author IDs were found, throw an exception
        if (!invalidAuthorIds.isEmpty()) {
            throw new IllegalStateException("Invalid author IDs found: " + invalidAuthorIds);
        }
    }

    // Get all posts by authors of a certain age
    public List<String> getPostsByAuthorAge(Integer age) {
        Set<String> authorIds = contributors.stream()
                .filter(person -> person.getAge().equals(age))
                .map(Person::getId)
                .collect(Collectors.toSet());

        return posts.stream()
                .filter(post -> authorIds.contains(post.getAuthorId()))
                .map(BlogPost::getId)
                .collect(Collectors.toList());
    }
}