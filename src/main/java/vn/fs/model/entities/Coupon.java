package vn.fs.model.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.Data;

@Entity
@Table(name = "coupon")
@Data
public class Coupon {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "expires", columnDefinition = "boolean default false")
    private Boolean expires;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;

}
