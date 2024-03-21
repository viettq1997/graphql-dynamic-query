package com.viettq.querydsldynamicquery;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository repository;

    @QueryMapping
    public Optional<Student> getStudentById(@Argument Long id) {
        return repository.findById(id);
    }

    @QueryMapping
    public Optional<Student> getStudentByFilter(@Argument Filter filter) {
        AbstractPredicate<Student> studentAbstractPredicate = new StudentPredicate();
        BooleanExpression expression = studentAbstractPredicate.predicate(filter, Student.class);
        return repository.findOne(expression);
    }
}
