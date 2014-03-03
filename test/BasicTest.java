import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Test
    public void aVeryImportantThingToTest() {
       // assertEquals(2, 1 + 1);
   
    }
    @Test
    public void testUser(){
          // Create a new user and save it
    new User("juan@gmail.com", "secret", "juan","segovia","lopez").save();
    
    // Retrieve the user with e-mail address bob@gmail.com
    User bob = User.find("byEmail", "juan@gmail.com").first();
    
    // Test 
    assertNotNull(bob);
    assertEquals("juan", bob.nombre);
    assertNotNull(User.connect("juan@gmail.com", "secret"));
    }
    @Test
public void createPost() {
    // Create a new user and save it
    User bob = new User("luis@gmail.com", "luis1234", "luis","martinez","garcia").save();
    
    // Create a new post
    new Post("My first post","Hello World", bob).save();
    
    // Test that the post has been created
    //assertEquals(1, Post.count());
    
    // Retrieve all posts created by Bob
    List<Post> bobPosts = Post.find("byAuthor", bob).fetch();
    
    // Tests
    assertEquals(1, bobPosts.size());
    Post firstPost = bobPosts.get(0);
    assertNotNull(firstPost);
    assertEquals(bob, firstPost.author);
    assertEquals("My first post", firstPost.title);
    assertEquals("Hello World", firstPost.content);
    assertNotNull(firstPost.postedAt);
}
@Test
public void postComments() {
    // Create a new user and save it
    User bob = new User("jose@gmail.com", "supersecret", "jose","garcia","rodriguez").save();
    // Create a new post
    Post bobPost =  new Post("My Second post","Hello World", bob).save();
 
    // Post a first comment
    new Comment(bobPost, "Jeff", "Nice post").save();
    new Comment(bobPost, "Tom", "I knew that !").save();
 
    // Retrieve all comments
    List<Comment> bobPostComments = Comment.find("byPost", bobPost).fetch();
 
    // Tests
    assertEquals(2, bobPostComments.size());
 
    Comment firstComment = bobPostComments.get(0);
    assertNotNull(firstComment);
    assertEquals("Jeff", firstComment.author);
    assertEquals("Nice post", firstComment.content);
    assertNotNull(firstComment.postedAt);
 
    Comment secondComment = bobPostComments.get(1);
    assertNotNull(secondComment);
    assertEquals("Tom", secondComment.author);
    assertEquals("I knew that !", secondComment.content);
    assertNotNull(secondComment.postedAt);
}
@Test
public void useTheCommentsRelation() {
    // Create a new user and save it
    User bob = new User("sergio@gmail.com", "secretsecret", "sergio","burrola","dominguez").save();
 
    // Create a new post
    Post bobPost = new Post("My first post", "Hello world",bob).save();
 
    // Post a first comment
    bobPost.addComment("Jeff", "Nice post");
    bobPost.addComment("Tom", "I knew that !");
 
    // Count things
    /*assertEquals(1, User.count());
    assertEquals(1, Post.count());
    assertEquals(2, Comment.count());*/
 
    // Retrieve Bob's post
    bobPost = Post.find("byAuthor", bob).first();
    assertNotNull(bobPost);
 
    // Navigate to comments
    assertEquals(2, bobPost.comments.size());
    assertEquals("Jeff", bobPost.comments.get(0).author);
    
    // Delete the post
    bobPost.delete();
    
    // Check that all comments have been deleted
   /* assertEquals(1, User.count());
    assertEquals(0, Post.count());
    assertEquals(0, Comment.count());*/
}
@Test
public void fullTest() {
    Fixtures.load("data.yml");
 
    // Count things
    assertEquals(2, User.count());
    assertEquals(3, Post.count());
    assertEquals(3, Comment.count());
 
    // Try to connect as users
    assertNotNull(User.connect("bob@gmail.com", "secret"));
    assertNotNull(User.connect("luis@gmail.com", "luis1234"));
    assertNull(User.connect("jeff@gmail.com", "badpassword"));
    assertNull(User.connect("tom@gmail.com", "secret"));
 
    // Find all of Bob's posts
    List<Post> bobPosts = Post.find("author.email", "bob@gmail.com").fetch();
    assertEquals(2, bobPosts.size());
 
    // Find all comments related to Bob's posts
    List<Comment> bobComments = Comment.find("post.author.email", "bob@gmail.com").fetch();
    assertEquals(3, bobComments.size());
 
    // Find the most recent post
    Post frontPost = Post.find("order by postedAt desc").first();
    assertNotNull(frontPost);
    assertEquals("About the model layer", frontPost.title);
 
    // Check that this post has two comments
    assertEquals(2, frontPost.comments.size());
 
    // Post a new comment
    frontPost.addComment("Jim", "Hello guys");
    assertEquals(3, frontPost.comments.size());
    assertEquals(4, Comment.count());
}
@Test
public void testTags() {
    // Create a new user and save it
    User bob = new User("adm@gmail.com", "zas4321", "jose","gonzalez","suarez").save();
 
    // Create a new post
    Post bobPost = new Post( "My first post", "Hello world",bob).save();
    Post anotherBobPost = new Post( "Hop", "Hello world",bob).save();
 
    // Well
    assertEquals(0, Post.findTaggedWith("Red").size());
 
    // Tag it now
    bobPost.tagItWith("Red").tagItWith("Blue").save();
    anotherBobPost.tagItWith("Red").tagItWith("Green").save();
 
    // Check
    assertEquals(2, Post.findTaggedWith("Red").size());
    assertEquals(1, Post.findTaggedWith("Blue").size());
    assertEquals(1, Post.findTaggedWith("Green").size());
    assertEquals(1, Post.findTaggedWith("Red", "Blue").size());
assertEquals(1, Post.findTaggedWith("Red", "Green").size());
assertEquals(0, Post.findTaggedWith("Red", "Green", "Blue").size());
assertEquals(0, Post.findTaggedWith("Green", "Blue").size());
List<Map> cloud = Tag.getCloud();
assertEquals(
    "[{tag=Blue, pound=1}, {tag=Green, pound=1}, {tag=Red, pound=2}]",
    cloud.toString()
);
}

}
