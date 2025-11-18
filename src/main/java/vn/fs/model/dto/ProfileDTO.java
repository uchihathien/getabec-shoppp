package vn.fs.model.dto;



import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
	@NotNull(message = "Name must not be null")
	private String name;
	
	@NotNull(message = "Email must not be null")
	private String email;
	
	private String avatar;
	
	private String address;
}
