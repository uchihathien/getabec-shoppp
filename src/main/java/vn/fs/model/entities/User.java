package vn.fs.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Size(max = 225, message = "Product_Name's length should be less than 255 characters")
	private String name;
	
	@Size(max = 225, message = "Email's length should be less than 255 characters")
	private String email;
	
	@Size(max = 225, message = "Password's length should be less than 255 characters")
	private String password;
	
	@Size(max = 2048, message = "Avatar length should be less than 2048 characters")
	private String avatar;
	
	@Size(max = 2048, message = "Address length should be less than 2048 characters")
	private String address;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "register_date")
	private Date registerDate;
	
	private Boolean status;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles",
		joinColumns = @JoinColumn(name = "user_id",
		referencedColumnName = "userId"),
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))

	private Collection<Role> roles;

}
