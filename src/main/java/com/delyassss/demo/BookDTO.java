package com.delyassss.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Data
//@SQLRestriction("deleted = false" )
public class BookDTO
{
    @NotBlank(message = "Title should not be Empty !")
    @NotNull(message = "Title necessary !")
    @Size(min = 1, max = 100, message = "title size should be 1 to 100 character !")
    private String title;

    @NotNull(message = "Authors list must not be null")
    @Size(min = 1 , message = "Book should have an author !")
    //@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)                                                //@ManyToMany(cascade = CascadeType.PERSIST) // PERSIST = only save , ALL = CRUD
    private List<Author> authors;

    private Boolean isBorrowed =  false;
    private Boolean deleted =  false;

}
