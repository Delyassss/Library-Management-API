package com.delyassss.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Data
//@SQLRestriction("deleted = false" )
public class BookDTO
{
    @NotBlank(message = "Title should not be Empty !")
    private String title;

    @NotNull(message = "Authors list must not be null")
    private List<String> authors = new ArrayList<>();

    private Boolean isBorrowed =  false;
    private Boolean deleted =  false;

}
