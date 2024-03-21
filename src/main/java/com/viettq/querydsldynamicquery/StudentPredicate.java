package com.viettq.querydsldynamicquery;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;

public class StudentPredicate extends AbstractPredicate<Student> {
    @Override
    public EntityPathBase<Student> getQEntity() {
        return QStudent.student;
    }

    @Override
    public BooleanExpression expressionAlwaysTrue() {
        return Expressions.asBoolean(true).isTrue();
    }

    @Override
    public BooleanExpression expressionAlwaysFalse() {
        return Expressions.asBoolean(true).isFalse();
    }
}
