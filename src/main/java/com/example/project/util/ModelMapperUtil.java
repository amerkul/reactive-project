package com.example.project.util;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ModelMapperUtil {

    private final ModelMapper modelMapper;

    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(e -> converter.apply(e)).collect(Collectors.toList());
    }

    public <T,R> R entityBuilder(T dto, Class<R> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public <T,R> R dtoBuilder(T entity, Class<R> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

}
