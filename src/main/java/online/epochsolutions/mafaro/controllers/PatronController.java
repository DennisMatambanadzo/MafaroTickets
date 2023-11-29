package online.epochsolutions.mafaro.controllers;

import lombok.RequiredArgsConstructor;
import online.epochsolutions.mafaro.dtos.patron.CreatePatronRequest;
import online.epochsolutions.mafaro.dtos.patron.UpdatePatronRequest;
import online.epochsolutions.mafaro.services.PatronService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mafaro/patrons")
public class PatronController {

    private final PatronService patronService;

    @PostMapping("/patronRegistration")
    public ResponseEntity<Boolean> patronRegistration(@RequestBody CreatePatronRequest request){
        return ResponseEntity.ok(patronService.patronRegistration(request));
    }

    @PutMapping("/updatePatron")
    public ResponseEntity<Boolean> updatePatron(@RequestBody UpdatePatronRequest request){
        return ResponseEntity.ok(patronService.updatePatron(request));
    }
}
