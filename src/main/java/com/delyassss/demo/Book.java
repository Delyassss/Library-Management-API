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
import org.hibernate.annotations.SQLDelete;
//import org.hibernate.annotations.Where;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@SQLDelete(sql = "UPDATE Book SET deleted = TRUE WHERE id=?")
@SQLRestriction("deleted = false" )
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    @NotNull(message = "Title necessary !")
    @NotBlank(message = "Title should not be Empty !")
    @Size(min = 1, max = 100, message = "title size should be 1 to 100 character !")
        private String title;

    @NotNull(message = "Authors list must not be null")
    @Size(min = 1 , message = "Book should have an author !")
    @OneToMany(mappedBy = "book" , cascade = CascadeType.ALL , orphanRemoval = true)                                  //@ManyToMany  (cascade = CascadeType.PERSIST) // PERSIST = only save , ALL = CRUD
        private List<Author> authors = new ArrayList<>();


    private Boolean isBorrowed =  false;

    private Boolean deleted =  false;


}
