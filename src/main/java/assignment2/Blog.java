package assignment2;

import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString
@EqualsAndHashCode
public class Blog {
    private final List<BlogPost> posts;
    private final List<Person> contributors;

    public Blog(List<BlogPost> posts, List<Person> contributors) {
        this.posts = posts != null ? new ArrayList<>(posts) : new ArrayList<>();
        this.contributors = contributors != null ? new ArrayList<>(contributors) : new ArrayList<>();
    }

    public List<String> getPostsByAuthorAge(Integer age) {
        // Get author IDs of contributors with the given age
        Set<String> authorIds = contributors.stream()
                .filter(person -> person.getAge().equals(age))
                .map(Person::getId)
                .collect(Collectors.toSet());

        // Get IDs of posts by authors with the given age from above
        return posts.stream()
                .filter(post -> authorIds.contains(post.getAuthorId()))
                .map(BlogPost::getId)
                .collect(Collectors.toList());
    }
}