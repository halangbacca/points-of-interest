package com.halan.poi.controller;

import com.halan.poi.controller.dto.CreatePointOfInterest;
import com.halan.poi.entity.PointOfInterest;
import com.halan.poi.repository.PointOfInterestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PointOfInterestController {
    private final PointOfInterestRepository repository;

    public PointOfInterestController(PointOfInterestRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/points-of-interest")
    public ResponseEntity<Void> createPoi(@RequestBody CreatePointOfInterest request) {
        repository.save(new PointOfInterest(request.name(), request.x(), request.y()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/points-of-interest")
    public ResponseEntity<Page<PointOfInterest>> findAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        var body = repository.findAll(PageRequest.of(page, pageSize));
        return ResponseEntity.ok(body);
    }

    @GetMapping("/near-me")
    public ResponseEntity<List<PointOfInterest>> nearMe(
            @RequestParam(name = "x") Long x,
            @RequestParam(name = "y") Long y,
            @RequestParam(name = "dMax") Long dMax) {

        var xMin = x - dMax;
        var xMax = x + dMax;
        var yMin = y - dMax;
        var yMax = y + dMax;

        var body = repository.findNearMe(xMin, xMax, yMin, yMax)
                .stream()
                .filter(p -> distanceBetweenPoints(x, y, p.getX(), p.getY()) <= dMax)
                .toList();

        return ResponseEntity.ok(body);
    }

    private Double distanceBetweenPoints(Long x1, Long y1, Long x2, Long y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}
