package com.example.demo.model;  
import java.util.Collection;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.example.demo.validations.FirstOrder;
import com.example.demo.validations.SecondOrder;
import com.example.demo.validations.ValidEmail;
import com.example.demo.validations.ValidPassword;

import lombok.Data;  

@Entity 
@Data
@Table(name="users")
@Inheritance(
	    strategy = InheritanceType.JOINED
	)
public class UserRecord {  
	private @Id  @GeneratedValue(strategy = GenerationType.AUTO) @Column(name="user_id") Long userId;  
	private @Column(name="name") @NotBlank(message = "Name is mandatory",groups = FirstOrder.class) String name;  
    private @Column(name="email",unique=true) @NotBlank(message = "Email is mandatory",groups = FirstOrder.class) @ValidEmail(groups = SecondOrder.class) String email; 
    private @Column(name="password") @NotBlank(groups = FirstOrder.class) @ValidPassword(groups = SecondOrder.class) String password;
    private @Column(name = "enabled") boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY,

    targetEntity = Role.class)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id",
            nullable = false,
            updatable = false),
    inverseJoinColumns = @JoinColumn(name = "role_id",
            nullable = false,
            updatable = false),
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT),
    inverseForeignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Collection<Role> roles;
    public UserRecord() {
    	super();
    	this.enabled=false;
    }
}  