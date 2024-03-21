package com.viettq.querydsldynamicquery;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import lombok.SneakyThrows;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractPredicate<T> {

    public abstract EntityPathBase<T> getQEntity();

    public abstract BooleanExpression expressionAlwaysTrue();

    public abstract BooleanExpression expressionAlwaysFalse();

    @SneakyThrows
    protected BooleanExpression predicate(Filter filter, Class<T> tClass) {
        return gen(filter, tClass);
    }

    private BooleanExpression genArr(List<Filter> filters, Class<T> tClass, BooleanOperator operator) {
        BooleanExpression expression = null;
        if (!ObjectUtils.isEmpty(filters)) {
            switch (operator) {
                case OR:
                    expression = expressionAlwaysFalse();
                    for (Filter f : filters) {
                        expression = expression.or(gen(f, tClass));
                    }
                    break;
                default:
                    expression = expressionAlwaysTrue();
                    for (Filter f : filters) {
                        expression = expression.and(gen(f, tClass));
                    }
                    break;
            }
        }
        return expression;
    }

    @SneakyThrows
    private BooleanExpression gen(Filter filter, Class<T> tClass) {
        EntityPathBase<T> fileEntity = getQEntity();
        BooleanExpression expression;

        if (!ObjectUtils.isEmpty(filter.getAnd())) {
            expression = expressionAlwaysTrue();
            BooleanExpression additionCond = genArr(filter.getAnd(), tClass, BooleanOperator.AND);
            if (additionCond != null) {
                expression = expression.and(additionCond);
            }
        } else if (!ObjectUtils.isEmpty(filter.getOr())) {
            expression = expressionAlwaysFalse();
            BooleanExpression additionCond = genArr(filter.getOr(), tClass, BooleanOperator.OR);
            if (additionCond != null) {
                expression = expression.or(additionCond);
            }
        } else {
            expression = expressionAlwaysTrue();
            Field field = getFieldByName(filter.getField(), tClass);
            if (field == null) {
                throw new Exception();
            }
            List<FilterValue> values = filter.getValues();
            if (QueryOperator.manyValueOps.contains(filter.getOperator())) {

                if (!ObjectUtils.isEmpty(values)) {

                    List<?> convertedValues = values.parallelStream().map(v -> {
                        Object outVal = null;
                        try {
                            outVal = v.getType().convertValue(v.getValue());
                        } catch (Exception ignored) {
                        }
                        return outVal;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

                    if (!ObjectUtils.isEmpty(convertedValues)) {
                        expression = Expressions.predicate(
                                filter.getOperator().getOp(),
                                Expressions.path(field.getType(), fileEntity, filter.getField()),
                                Expressions.constant(convertedValues)
                        );
                    }
                }
            } else {
                if (!ObjectUtils.isEmpty(values)) {

                    Object outVal = null;
                    FilterValue v = values.get(0);
                    try {
                        outVal = v.getType().convertValue(v.getValue());
                    } catch (Exception ignored) {
                    }

                    if (outVal != null) {
                        expression = Expressions.predicate(
                                filter.getOperator().getOp(),
                                Expressions.path(field.getType(), fileEntity, filter.getField()),
                                Expressions.constant(outVal)
                        );
                    }
                }
            }
        }
        return expression;
    }

    private Field getFieldByName(String fieldName, Class tClass) {
        Optional<Field> fieldOpt = Arrays.asList(tClass.getDeclaredFields()).parallelStream()
                .filter(i -> i.getName().equals(fieldName)).findFirst();
        if (tClass.getSuperclass() != null) {
            return fieldOpt.orElseGet(() -> getFieldByName(fieldName, tClass.getSuperclass()));
        }
        return null;
    }

}