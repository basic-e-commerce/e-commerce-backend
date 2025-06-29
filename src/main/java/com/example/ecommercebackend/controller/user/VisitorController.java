package com.example.ecommercebackend.controller.user;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.anotation.RateLimit;
import com.example.ecommercebackend.dto.user.TimeDto;
import com.example.ecommercebackend.dto.user.visitor.VisitorDto;
import com.example.ecommercebackend.service.user.VisitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/visitors")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    /**
     * ✔️ Ziyaretçi kaydı (bu endpoint çağrıldığında kayıt yapılır)
     */
    @PostMapping("/visit")
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<String> visit() {
        visitorService.visit();
        return ResponseEntity.ok("Visitor counted successfully.");
    }

    /**
     * ✔️ Toplam ziyaretçi sayısı
     */
    @GetMapping("/total")
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<Long> getTotalVisitor() {
        return ResponseEntity.ok(visitorService.getTotalVisitor());
    }

    /**
     * ✔️ Benzersiz ziyaretçi sayısı
     */
    @GetMapping("/unique")
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<Long> getUniqueVisitor() {
        return ResponseEntity.ok(visitorService.getUniqueVisitor());
    }

    /**
     * ✔️ Bugünün ziyaretçi sayısı
     */
    @GetMapping("/today")
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<Long> getTodayVisitor() {
        return ResponseEntity.ok(visitorService.getTodayVisitor());
    }

    /**
     * ✔️ Belirli bir tarihe ait günlük ziyaretçi sayısı
     * Format: yyyy-MM-dd
     */
    @GetMapping("/daily")
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<Long> getDailyVisitor(
            @RequestParam("date") String date
    ) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(visitorService.getDailyVisitor(localDate));
    }

    @GetMapping("/last-ten")
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<Map<LocalDate,Long>> getLastTenVisitor() {
        return new ResponseEntity<>(visitorService.getLastTenVisitor(), HttpStatus.OK);
    }

    @PostMapping("/between-visitor")
    @RateLimit(limit = 5, duration = 1, unit = TimeUnit.SECONDS)
    public ResponseEntity<VisitorDto> getDailyVisitor(@RequestBody(required = false) TimeDto timeDto){
        return new ResponseEntity<>(visitorService.getDailyVisitor(timeDto), HttpStatus.OK);
    }
}
