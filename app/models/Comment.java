/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;
import java.util.*;
import javax.persistence.*;
import play.data.validation.MaxSize;
import play.data.validation.Required;
 
import play.db.jpa.*;
 

/**
 *
 * @author juan
 */
@Entity
public class Comment extends Model {
   @Required
    public String author;
    public Date postedAt;
     
    @Lob
    @Required
    @MaxSize(10000)
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
