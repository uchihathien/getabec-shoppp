package vn.fs.model.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double rating;
	
	@Size(max = 225, message = "Content's length should be less than 255 characters")
	@Column(length = 225)
	private String content;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "rate_date")
	private Date rateDate;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	@OneToOne
	@JoinColumn(name = "orderDetailId")
	private OrderDetail orderDetail;

}
