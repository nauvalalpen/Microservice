package com.nauval.jadwal.jadwal.model; // Sesuaikan package Anda

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ActivityDetail {

    // Anotasi ini benar: cari tag <Activity>, ambil atribut 'id'
    @JacksonXmlProperty(localName = "Activity", isAttribute = true)
    private Integer id;

    // Anotasi ini juga sudah benar: cari tag <Subject>, ambil atribut 'name'
    // dan masukkan ke field 'subject'
    @JacksonXmlProperty(localName = "Subject", isAttribute = true)
    private String subject;

    // Field ini akan dipetakan ke atribut 'name' di tag <Activity_Tag>
    @JacksonXmlProperty(localName = "Activity_Tag", isAttribute = true)
    private String activityTag;

    @JacksonXmlProperty(localName = "Students", isAttribute = true)
    private String students;

    @JacksonXmlProperty(localName = "Room", isAttribute = true)
    private String room;
}