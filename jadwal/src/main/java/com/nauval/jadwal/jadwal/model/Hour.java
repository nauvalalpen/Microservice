package com.nauval.jadwal.jadwal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Sembunyikan field yang null di output JSON
public class Hour {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    // Definisikan setiap kemungkinan tag anak sebagai field
    @JacksonXmlProperty(localName = "Activity")
    private Activity activity;

    @JacksonXmlProperty(localName = "Subject")
    private Subject subject;

    @JacksonXmlProperty(localName = "Activity_Tag")
    private ActivityTag activityTag;

    @JacksonXmlProperty(localName = "Students")
    private Students students;

    @JacksonXmlProperty(localName = "Room")
    private Room room;
}