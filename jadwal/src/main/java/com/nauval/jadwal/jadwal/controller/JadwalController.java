package com.nauval.jadwal.jadwal.controller; // Sesuaikan package Anda

import com.nauval.jadwal.jadwal.model.Day;
import com.nauval.jadwal.jadwal.model.Hour;
import com.nauval.jadwal.jadwal.model.Teacher;
import com.nauval.jadwal.jadwal.service.JadwalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jadwal/dosen")
public class JadwalController {

    private final JadwalService jadwalService;

    public JadwalController(JadwalService jadwalService) {
        this.jadwalService = jadwalService;
    }

    @GetMapping
    public List<String> getAllTeacherAliases() {
        return jadwalService.getAllTeacherAliases();
    }

    @GetMapping("/{alias}")
    public ResponseEntity<Teacher> getScheduleByTeacherAlias(@PathVariable String alias) {
        return jadwalService.getScheduleByTeacherAlias(alias)
                .map(teacher -> {
                    // --- LOGIKA FILTER PINDAH KE SINI ---
                    // Buat salinan objek untuk diubah
                    Teacher filteredTeacher = new Teacher();
                    filteredTeacher.setName(teacher.getName());

                    List<Day> filteredDays = teacher.getDays().stream()
                            .map(day -> {
                                Day filteredDay = new Day();
                                filteredDay.setName(day.getName());

                                // Filter jam yang 'subject'-nya tidak null
                                List<Hour> nonEmptyHours = day.getHours().stream()
                                        .filter(hour -> hour.getSubject() != null)
                                        .collect(Collectors.toList());

                                filteredDay.setHours(nonEmptyHours);
                                return filteredDay;
                            })
                            .filter(day -> !day.getHours().isEmpty())
                            .collect(Collectors.toList());

                    filteredTeacher.setDays(filteredDays);
                    return ResponseEntity.ok(filteredTeacher);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{alias}/{hari}")
    public ResponseEntity<Day> getScheduleByTeacherAliasAndDay(
            @PathVariable String alias,
            @PathVariable String hari) {
        return jadwalService.getScheduleByTeacherAliasAndDay(alias, hari)
                .map(day -> {
                    // --- LOGIKA FILTER PINDAH KE SINI JUGA ---
                    Day filteredDay = new Day();
                    filteredDay.setName(day.getName());

                    List<Hour> nonEmptyHours = day.getHours().stream()
                            .filter(hour -> hour.getSubject() != null)
                            .collect(Collectors.toList());

                    filteredDay.setHours(nonEmptyHours);

                    // Jika setelah difilter hari itu jadi kosong, kembalikan Not Found
                    if (filteredDay.getHours().isEmpty()) {
                        return ResponseEntity.notFound().<Day>build();
                    }

                    return ResponseEntity.ok(filteredDay);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}