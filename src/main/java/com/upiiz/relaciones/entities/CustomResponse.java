package com.upiiz.relaciones.entities;

import org.springframework.hateoas.Link;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse<T> {
    private int estado;
    private String msg;
    private T data;
    private List<Link> links;
}
