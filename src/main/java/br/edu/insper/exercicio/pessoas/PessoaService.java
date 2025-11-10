package br.edu.insper.exercicio.pessoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> getCursos() {
        return pessoaRepository.findAll();
    }

    public Pessoa createCurso(Pessoa curso) {
        return pessoaRepository.save(curso);
    }

    public Pessoa getCurso(Integer id) {
        return pessoaRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    }

    public void deleteCurso(Integer id) {
        if (!pessoaRepository.existsById(id)) {
            throw new RuntimeException("Curso não encontrado");
        }
        pessoaRepository.deleteById(id);
    }
}
