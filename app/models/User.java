/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;
import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import play.db.jpa.Model;
//import org.hibernate.envers.Audited;
import play.data.validation.Required;
import net.sf.oval.constraint.Email;
/**
 *
 * @author juan
 */
@Entity 
@Table(name="usuario")
//@Audited
public class User extends Model {
    @Email
    public String email;
    @Required
    public String password;
    @Required
    public String nombre;
    public String primer_apellido;
    public String segundo_apellido;
    public Boolean isAdmin;
    public User(String email,String password,String nombre,String primer_apellido,String segundo_apellido){
        this.email=email;
        this.password=password;
        this.nombre=nombre;
        this.primer_apellido=primer_apellido;
        this.segundo_apellido=segundo_apellido;
    }
    public static User connect(String email, String password) {
    return find("byEmailAndPassword", email, password).first();
}
    
}
