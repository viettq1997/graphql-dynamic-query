package com.viettq.querydsldynamicquery;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FilterValue {
    private String value;
    private ValueType type;
}

