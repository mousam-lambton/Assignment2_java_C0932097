package assignment2;

import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

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
}