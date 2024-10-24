package co.edu.usco.TM.controller;

import co.edu.usco.TM.controller.dto.MakerDTO;
import co.edu.usco.TM.persistence.entity.commerce.Maker;
import co.edu.usco.TM.service.IMakerService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/maker")
@RestController
public class MakerController {
    
    @Autowired
    private IMakerService makerService;
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        
        Optional<Maker> makerOptional = makerService.findById(id);
        
        if (makerOptional.isPresent()) {
            Maker maker = makerOptional.get();
            
            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .username(maker.getUsername())
                    .email(maker.getEmail())
                    .password(maker.getPassword())
                    .role(maker.getRole())
                    .name(maker.getName())
                    .address(maker.getAddress())
                    .phone(maker.getPhone())
                    .build();
            
            return ResponseEntity.ok(makerDTO);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/find")
    public ResponseEntity<?> findAll() {
        
        List<MakerDTO> makers = makerService.findAll()
                .stream().map(maker -> MakerDTO.builder()
                .id(maker.getId())
                .username(maker.getUsername())
                .email(maker.getEmail())
                .password(maker.getPassword())
                .role(maker.getRole())
                .name(maker.getName())
                .address(maker.getAddress())
                .phone(maker.getPhone())
                .build())
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(makers);
    }
    
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {
        
        if (makerDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        
        makerService.save(Maker.builder()
                .username(makerDTO.getUsername())
                .email(makerDTO.getEmail())
                .password(makerDTO.getPassword())
                .name(makerDTO.getName())
                .address(makerDTO.getAddress())
                .phone(makerDTO.getPhone())
                .build());
        
        return ResponseEntity.created(new URI("/api/maker/save")).build();
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MakerDTO makerDTO) {
        
        Optional<Maker> makerOptional = makerService.findById(id);
        
        if (makerOptional.isPresent()) {
            Maker maker = makerOptional.get();
            maker.setUsername(makerDTO.getUsername());
            maker.setEmail(makerDTO.getEmail());
            maker.setPassword(makerDTO.getPassword());
            maker.setName(makerDTO.getName());
            maker.setAddress(makerDTO.getAddress());
            maker.setPhone(makerDTO.getPhone());
            
            makerService.save(maker);
            
            return ResponseEntity.ok("updated maker");
        }
        
        return ResponseEntity.notFound().build();
        
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        
        Optional<Maker> makerOptional = makerService.findById(id);
        
        if (!makerOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        } else if (id != null) {
            
            makerService.deleteById(id);
            
            return ResponseEntity.ok("deleted maker");
        }
        
        return ResponseEntity.badRequest().build();
    }
}
