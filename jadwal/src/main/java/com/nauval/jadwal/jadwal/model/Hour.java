package com.nauval.jadwal.jadwal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hour {
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JsonUnwrapped
    private ActivityDetail activityDetail;
}