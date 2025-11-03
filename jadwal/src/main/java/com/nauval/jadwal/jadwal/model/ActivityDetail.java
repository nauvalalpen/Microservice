package com.nauval.jadwal.jadwal.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ActivityDetail {

    @JacksonXmlProperty(localName = "Activity", isAttribute = true)
    private Integer id;

    // Ganti field String dengan objek-objek baru kita
    @JacksonXmlProperty(localName = "Subject")
    private Subject subject;

    @JacksonXmlProperty(localName = "Activity_Tag")
    private ActivityTag activityTag;

    @JacksonXmlProperty(localName = "Students")
    private Students students;

    @JacksonXmlProperty(localName = "Room")
    private Room room;
}