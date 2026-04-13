package com.delyassss.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

   // @NotBlank(message = "Author should have a name !")
    @NotBlank(message = "Please Enter a valid author!")
    @Size(min = 3, max = 30, message = "Invalid author Name")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
