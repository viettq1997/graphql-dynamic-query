package com.viettq.querydsldynamicquery;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)

public class Filter {
    private String field;
    private QueryOperator operator;
    private List<FilterValue> values;
    private List<Filter> and;
    private List<Filter> or;
}
