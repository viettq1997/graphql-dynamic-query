package com.viettq.querydsldynamicquery;

import com.querydsl.core.types.Operator;
import com.querydsl.core.types.Ops;
import lombok.Getter;

import java.util.Set;

@Getter
public enum QueryOperator {

    EQ(Ops.EQ),

    NE(Ops.NE),

    GT(Ops.GT),

    LT(Ops.LT),

    GOE(Ops.GOE),

    LOE(Ops.LOE),

    LIKE(Ops.LIKE),

    IN(Ops.IN),

    NOT_IN(Ops.NOT_IN);
    private final Operator op;
    public static final Set<QueryOperator> oneValueOps = Set.of(EQ, NE, GT, LT, GOE, LOE, LIKE);
    public static final Set<QueryOperator> manyValueOps = Set.of(IN, NOT_IN);

    QueryOperator(Operator op) {this.op = op;}

}
