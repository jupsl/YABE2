/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 

/**
 *
 * @author juan
 */
@Entity
public class Comment extends Model {
   public String author;
    public Date postedAt;
     
    @Lob
    public String content;
    
    @ManyToOne
    public Post post;
    
    public Comment(Post post, String author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }  
}
