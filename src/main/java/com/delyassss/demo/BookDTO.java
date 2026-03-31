package com.delyassss.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BookDTO
{
    @NotBlank(message = "Title should not be Empty !")
    @NotNull(message = "Title necessary !")
    @Size(min = 1, max = 100, message = "title size should be 1 to 100 character !")
    private String title;

    @Size(min = 1 , message = "Book should have an author !")
    @ManyToMany(cascade = CascadeType.ALL) // PERSIST = only save , ALL = CRUD
    private List<Author> authors;

    private Boolean isBorrowed =  false;

}
