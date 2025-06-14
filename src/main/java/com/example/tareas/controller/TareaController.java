package com.example.tareas.controller;

import com.example.tareas.model.Tarea;
import com.example.tareas.repository.TareaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaRepository repository;

    public TareaController(TareaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Tarea> obtenerTodas() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tarea crear(@RequestBody Tarea tarea) {
        return repository.save(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable Long id, @RequestBody Tarea tareaNueva) {
        return repository.findById(id)
                .map(tarea -> {
                    tarea.setTitulo(tareaNueva.getTitulo());
                    tarea.setDescripcion(tareaNueva.getDescripcion());
                    tarea.setEstado(tareaNueva.getEstado());
                    return ResponseEntity.ok(repository.save(tarea));
                }).orElse(ResponseEntity.notFound().build());
    }

   @DeleteMapping("/{id}")
public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    if (repository.existsById(id)) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}
}
