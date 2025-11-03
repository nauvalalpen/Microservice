package com.nauval.jadwal.jadwal.service; // Sesuaikan package Anda

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nauval.jadwal.jadwal.model.Day;
import com.nauval.jadwal.jadwal.model.Hour;
import com.nauval.jadwal.jadwal.model.Teacher;
import com.nauval.jadwal.jadwal.model.Timetable;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JadwalService implements InitializingBean {

    // Map tetap berisi data mentah yang diparsing dari XML
    private Map<String, Teacher> teacherSchedulesByAlias = Collections.emptyMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        InputStream inputStream = new ClassPathResource("jadwal_dosen.xml").getInputStream();
        Timetable timetable = xmlMapper.readValue(inputStream, Timetable.class);

        if (timetable != null && timetable.getTeachers() != null) {
            this.teacherSchedulesByAlias = timetable.getTeachers().stream()
                    .collect(Collectors.toMap(this::extractAlias, Function.identity()));
            System.out.println("Berhasil memuat jadwal untuk " + this.teacherSchedulesByAlias.size() + " dosen.");
        }
    }

    private String extractAlias(Teacher teacher) {
        String fullName = teacher.getName();
        if (fullName != null && fullName.contains("-")) {
            return fullName.substring(fullName.lastIndexOf("-") + 1);
        }
        return fullName;
    }

    public List<String> getAllTeacherAliases() {
        return this.teacherSchedulesByAlias.keySet().stream().sorted().collect(Collectors.toList());
    }

    /**
     * Mencari jadwal seorang dosen berdasarkan KODE ALIAS-nya.
     * Method ini sekarang akan melakukan SEMUA logika pembersihan.
     */
    public Optional<Teacher> getScheduleByTeacherAlias(String alias) {
        Teacher originalTeacher = this.teacherSchedulesByAlias.get(alias.toUpperCase());

        if (originalTeacher == null) {
            return Optional.empty();
        }

        // Kita tidak perlu lagi membuat salinan, cukup kembalikan data asli
        // karena filtering sekarang ada di controller atau bisa dilakukan di sini jika
        // perlu
        return Optional.of(originalTeacher);
    }

    /**
     * Mencari jadwal seorang dosen pada hari tertentu.
     */
    public Optional<Day> getScheduleByTeacherAliasAndDay(String alias, String dayName) {
        Optional<Teacher> teacherOpt = Optional.ofNullable(this.teacherSchedulesByAlias.get(alias.toUpperCase()));

        return teacherOpt.flatMap(teacher -> teacher.getDays().stream()
                .filter(day -> day.getName().equalsIgnoreCase(dayName))
                .findFirst());
    }
}