package es.codeurjc.daw;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    List<Entrada> findByName(String name);

}
