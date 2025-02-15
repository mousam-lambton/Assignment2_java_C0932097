package assignment2Test;

import assignment2.BlogPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlogPostTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Testing if blog post created is valid and all fields are set correctly
    @Test
    public void testValidBlogPostCreation() {
        BlogPost post = BlogPost.builder()
                .withId("1")
                .withAuthorId("Mousam")
                .withPostContent("Mousam's Blog")
                .build();

        assertEquals("1", post.getId());
        assertEquals("Mousam", post.getAuthorId());
        assertEquals("Mousam's Blog", post.getPostContent());
    }

    // Testing if validation works for null ID
    @Test
    public void testNullId() {
        assertThrows(IllegalArgumentException.class, () ->
                BlogPost.builder()
                        .withAuthorId("Mousam")
                        .withPostContent("Mousam's Blog")
                        .build()
        );
    }

    // Testing if validation works for null author ID
    @Test
    public void testNullAuthorId() {
        assertThrows(IllegalArgumentException.class, () ->
                BlogPost.builder()
                        .withId("1")
                        .withPostContent("Mousam's Blog")
                        .build()
        );
    }

    // Testing if serialization and deserialization works correctly with builder pattern
    @Test
    public void testJsonSerialization() throws Exception {
        BlogPost post = BlogPost.builder()
                .withId("1")
                .withAuthorId("Mousam")
                .withPostContent("Mousam's Blog")
                .build();

        String json = objectMapper.writeValueAsString(post);
        BlogPost deserializedPost = objectMapper.readValue(json, BlogPost.class);

        assertEquals(post, deserializedPost);
    }

    // Testing if deserialization works correctly with a JSON string
    @Test
    public void testJsonDeserialization() throws Exception {
        String json = "{"
                + "\"id\": \"1\","
                + "\"authorId\": \"Mousam\","
                + "\"postContent\": \"Mousam's Blog\""
                + "}";

        BlogPost post = objectMapper.readValue(json, BlogPost.class);

        assertEquals("1", post.getId());
        assertEquals("Mousam", post.getAuthorId());
        assertEquals("Mousam's Blog", post.getPostContent());
    }
}