package com.viettq.querydsldynamicquery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public enum ValueType {
    LONG {
        @Override
        public Long convertValue(String inp) {
            return Long.parseLong(inp);
        }
    },
    STRING {
        @Override
        public String convertValue(String inp) {
            return inp;
        }
    },
    DATE {
        @Override
        public Date convertValue(String inp) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT_YYYYMMdd);
            return formatter.parse(inp);
        }
    },
    LOCAL_DATE_TIME {
        @Override
        public LocalDateTime convertValue(String inp) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT_YYYYMMdd);
            LocalDate ld = LocalDate.parse(inp, formatter);
            return LocalDateTime.of(ld, LocalTime.of(0, 0));
        }
    };

    public abstract Object convertValue(String inp) throws ParseException;
}
