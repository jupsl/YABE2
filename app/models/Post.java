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
    
    public Post(String title, String content, User author) {
        this.comments = new ArrayList<Comment>();
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
            
}
