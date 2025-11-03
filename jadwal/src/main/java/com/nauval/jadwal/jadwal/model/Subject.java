package com.nauval.jadwal.jadwal.model; // Sesuaikan package Anda

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Subject {
    // UBAH dari @JacksonXmlText menjadi anotasi untuk membaca atribut 'name'
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    // @JsonValue akan menampilkan nilai dari field 'name' saat konversi ke JSON
    @JsonValue
    public String getName() {
        return name;
    }
}