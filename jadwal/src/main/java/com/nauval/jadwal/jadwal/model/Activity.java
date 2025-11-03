package com.nauval.jadwal.jadwal.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Activity {
    @JacksonXmlProperty(isAttribute = true)
    private Integer id;

    @JsonValue
    public Integer getId() {
        return id;
    }
}