/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;
import java.util.*;
import javax.persistence.*;
import models.User;
import org.hibernate.annotations.Type;
import play.db.jpa.*;
/**
 *
 * @author juan
 */
@Entity
public class Post extends Model {
    public String title;
    public Date postedAt;
    @Lob
        @Type(type = "org.hibernate.type.TextType") 
    public String content;
    @ManyToOne
    public User author;

    @OneToMany(mappedBy="post", cascade=CascadeType.ALL)
    public List<Comment> comments;
    @ManyToMany(cascade=CascadeType.PERSIST)
    public Set<Tag> tags;
    
    public Post(String title, String content, User author) {
        this.comments = new ArrayList<Comment>();
        this.tags = new TreeSet<Tag>();
        this.title = title;
        this.content = content;
        this.author = author;
        this.postedAt= new Date();
    }
    public Post addComment(String author, String content) {
    Comment newComment = new Comment(this, author, content).save();
    this.comments.add(newComment);
    this.save();
    return this;
}
     public Post previous(){
     return Post.find("postedAt < ? order by postedAt desc", this.postedAt).first();
     
     }    
     public Post next(){
     return Post.find("postedAt > ? order by postedAt asc", this.postedAt).first();
     }
     public Post tagItWith(String name) {
    tags.add(Tag.findOrCreateByName(name));
    return this;
}
     public static List<Post> findTaggedWith(String tag) {
    return Post.find(
        "select distinct p from Post p join p.tags as t where t.name = ?", tag
    ).fetch();
}
     public static List<Post> findTaggedWith(String... tags) {
    return Post.find(
            "select distinct p from Post p join p.tags as t where t.name in (:tags) group by p.id, p.author, p.title, p.content,p.postedAt having count(t.id) = :size"
    ).bind("tags", tags).bind("size", tags.length).fetch();
}
}
