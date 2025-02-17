package assignment2Test;

import assignment2.Blog;
import assignment2.BlogPost;
import assignment2.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlogTest {
    private List<Person> people;
    private List<BlogPost> posts;
    private Blog blog;

    // Creating people and posts for testing before each test
    @BeforeEach
    void setUp() {
        // Create test people
        people = Arrays.asList(
            Person.builder()
                .withId("1")
                .withFirstName("Mousam")
                .withLastName("Dhakal")
                .withAge(25)
                .withGender("male")
                .build(),
            Person.builder()
                .withId("2")
                .withFirstName("Robin")
                .withLastName("Schebarsky")
                .withAge(30)
                .withGender("female")
                .build()
        );

        // Create test posts
        posts = Arrays.asList(
            BlogPost.builder()
                .withId("p1")
                .withAuthorId("1")
                .withPostContent("Test post 1")
                .build(),
            BlogPost.builder()
                .withId("p2")
                .withAuthorId("2")
                .withPostContent("Test post 2")
                .build()
        );

        blog = new Blog(posts, people);
    }

    // Test for new method getPostsByAuthorAge
    @Test
    void testGetPostsByAuthorAge() {
        List<String> postsAge25 = blog.getPostsByAuthorAge(25);
        assertEquals(1, postsAge25.size());
        assertEquals("p1", postsAge25.get(0));

        List<String> postsAge30 = blog.getPostsByAuthorAge(30);
        assertEquals(1, postsAge30.size());
        assertEquals("p2", postsAge30.get(0));
    }

    // Case where there is not posts for the given age
    @Test
    void testGetPostsByAuthorAgeNoMatch() {
        List<String> posts = blog.getPostsByAuthorAge(40);
        assertTrue(posts.isEmpty());
    }

    // Case where there are no posts
    @Test
    void testBlogWithEmptyLists() {
        Blog emptyBlog = new Blog(new ArrayList<>(), new ArrayList<>());
        assertTrue(emptyBlog.getPostsByAuthorAge(25).isEmpty());
    }

    // Case where there are null lists
    @Test
    void testBlogWithNullLists() {
        assertThrows(NullPointerException.class, () -> new Blog(null, null));
    }

    // Case where there is an invalid author ID
    @Test
    void testBlogWithInvalidAuthorId() {
        List<BlogPost> invalidPosts = Collections.singletonList(
            BlogPost.builder()
                .withId("p3")
                .withAuthorId("999")
                .withPostContent("Invalid author post")
                .build()
        );

        assertThrows(IllegalStateException.class, () -> new Blog(invalidPosts, people));
    }

    // Test contributors are correctly set
    @Test
    void testGetContributors() {
        assertEquals(2, blog.getContributors().size());
        assertTrue(blog.getContributors().containsAll(people));
    }

    // Test posts are correctly set
    @Test
    void testGetPosts() {
        assertEquals(2, blog.getPosts().size());
        assertTrue(blog.getPosts().containsAll(posts));
    }

    // Test if posts and authors are correctly associated
    @Test
    void testBlogPostAuthorAssociation() {
        List<String> mousamPosts = blog.getPostsByAuthorAge(25);
        List<String> robinPosts = blog.getPostsByAuthorAge(30);

        assertEquals(1, mousamPosts.size());
        assertEquals(1, robinPosts.size());
        assertEquals("p1", mousamPosts.get(0));
        assertEquals("p2", robinPosts.get(0));
    }
}