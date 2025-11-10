package br.edu.insper.exercicio.pessoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> getCursos() {
        return pessoaService.getCursos();
    }

    @PostMapping
    public Pessoa createCurso(@RequestBody Pessoa curso) {
        return pessoaService.createCurso(curso);
    }

    @GetMapping("/{id}")
    public Pessoa getCurso(@PathVariable Integer id) {
        return pessoaService.getCurso(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Integer id) {
        pessoaService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}
