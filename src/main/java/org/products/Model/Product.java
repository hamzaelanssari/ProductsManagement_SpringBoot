package org.products.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @NotNull
    @Size(min=5,max=12)
    private String designation ;
    @NotNull
    @DecimalMin(value="100")
    private double price ;
    @NotNull
    @DecimalMin(value="0")
    private double quantity ;
}
