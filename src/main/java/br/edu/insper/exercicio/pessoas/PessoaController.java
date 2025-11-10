package br.edu.insper.exercicio.pessoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Pessoa createCurso(@AuthenticationPrincipal Jwt jwt, @RequestBody Pessoa curso) {
        return pessoaService.createCurso(curso);
    }

    @GetMapping("/{id}")
    public Pessoa getCurso(@PathVariable Integer id) {
        return pessoaService.getCurso(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer id) {
        List<String> roles = jwt.getClaim("https://dev-c5cya7ea1phr4j8p.us.auth0.com/roles");
        
        if (roles == null || !roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas administradores podem excluir cursos");
        }
        
        pessoaService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}
