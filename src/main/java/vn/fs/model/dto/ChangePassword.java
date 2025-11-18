package vn.fs.model.dto;

import jakarta.validation.constraints.NotEmpty;   // ✅ đổi từ javax → jakarta
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {

	@NotEmpty
	@Length(min = 6)
	private String newPassword;

	@NotEmpty
	@Length(min = 6)
	private String confirmPassword;
}
