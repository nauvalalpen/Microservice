package com.nauval.jadwal.jadwal.model; // Sesuaikan package Anda

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Room {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JsonValue
    public String getName() {
        return name;
    }
}