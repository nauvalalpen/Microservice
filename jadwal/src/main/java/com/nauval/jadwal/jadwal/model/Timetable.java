package com.nauval.jadwal.jadwal.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Teachers_Timetable")
public class Timetable {
    @JacksonXmlProperty(localName = "Teacher")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Teacher> teachers;
}