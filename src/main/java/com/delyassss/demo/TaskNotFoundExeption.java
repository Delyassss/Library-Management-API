package com.delyassss.demo;

public class TaskNotFoundExeption extends RuntimeException
{
    public TaskNotFoundExeption(Long id)
    {
        super("Book with id "+id+" not found");
    }



}
