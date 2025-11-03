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

    // Map sekarang akan menggunakan KODE ALIAS sebagai kunci
    private Map<String, Teacher> teacherSchedulesByAlias = Collections.emptyMap();

    @Override
    public void afterPropertiesSet() throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        InputStream inputStream = new ClassPathResource("jadwal_dosen.xml").getInputStream();
        Timetable timetable = xmlMapper.readValue(inputStream, Timetable.class);

        if (timetable != null && timetable.getTeachers() != null) {
            this.teacherSchedulesByAlias = timetable.getTeachers().stream()
                    // PERUBAHAN #1: Gunakan KODE ALIAS sebagai kunci Map
                    .collect(Collectors.toMap(this::extractAlias, Function.identity()));
            System.out.println("Berhasil memuat jadwal untuk " + this.teacherSchedulesByAlias.size() + " dosen.");
        }
    }

    /**
     * Helper method untuk mengekstrak kode alias dari nama lengkap.
     * Contoh: "AINIL MARDIAH-AINIL" -> "AINIL"
     */
    private String extractAlias(Teacher teacher) {
        String fullName = teacher.getName();
        if (fullName != null && fullName.contains("-")) {
            return fullName.substring(fullName.lastIndexOf("-") + 1);
        }
        return fullName; // Fallback jika tidak ada tanda strip
    }

    /**
     * Mengambil daftar semua KODE ALIAS dosen.
     */
    public List<String> getAllTeacherAliases() {
        return this.teacherSchedulesByAlias.keySet().stream().sorted().collect(Collectors.toList());
    }

    /**
     * Mencari jadwal seorang dosen berdasarkan KODE ALIAS-nya.
     */
    public Optional<Teacher> getScheduleByTeacherAlias(String alias) {
        Teacher originalTeacher = this.teacherSchedulesByAlias.get(alias.toUpperCase()); // <-- Tambahkan toUpperCase
                                                                                         // untuk konsistensi

        if (originalTeacher == null) {
            System.out.println("Dosen dengan alias '" + alias + "' tidak ditemukan di Map.");
            return Optional.empty();
        }

        // Buat salinan objek Teacher
        Teacher filteredTeacher = new Teacher();
        filteredTeacher.setName(originalTeacher.getName());

        // Filter hari dan jam
        List<Day> filteredDays = originalTeacher.getDays().stream()
                .map(day -> {
                    Day filteredDay = new Day();
                    filteredDay.setName(day.getName());

                    // === PERBAIKI LOGIKA FILTER DI SINI ===
                    // Kondisi yang lebih sederhana dan aman: Cek apakah activityDetail tidak null.
                    List<Hour> nonEmptyHours = day.getHours().stream()
                            .filter(hour -> hour.getActivityDetail() != null)
                            .collect(Collectors.toList());

                    // Jika ingin debug, tambahkan log ini:
                    // System.out.println("Hari: " + day.getName() + ", Jam terisi: " +
                    // nonEmptyHours.size() + " dari " + day.getHours().size());

                    filteredDay.setHours(nonEmptyHours);
                    return filteredDay;
                })
                // Hanya sertakan hari yang memiliki jadwal mengajar
                .filter(day -> !day.getHours().isEmpty())
                .collect(Collectors.toList());

        filteredTeacher.setDays(filteredDays);

        return Optional.of(filteredTeacher);
    }

    /**
     * METHOD BARU: Mencari jadwal seorang dosen pada hari tertentu.
     */
    public Optional<Day> getScheduleByTeacherAliasAndDay(String alias, String dayName) {
        // Panggil method yang sudah ada untuk mendapatkan jadwal lengkap (yang sudah
        // difilter)
        Optional<Teacher> teacherOpt = getScheduleByTeacherAlias(alias);

        // Jika dosen ditemukan, cari hari yang cocok (tidak case-sensitive)
        return teacherOpt.flatMap(teacher -> teacher.getDays().stream()
                .filter(day -> day.getName().equalsIgnoreCase(dayName))
                .findFirst());
    }
}