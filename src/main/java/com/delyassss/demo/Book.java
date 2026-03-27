package com.delyassss.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    @NotBlank(message = "Title should not be Empty !")
    @Size(min = 1, max = 100, message = "title size should be 1 to 100 character !")
        private String title;
    @Size(min = 1 , message = "Book should have an author !")
    @ManyToMany(cascade = CascadeType.PERSIST) // PERSIST = only save , ALL = CRUD
        private List<Author> authors;

    private Boolean isBorrowed =  false;


}
