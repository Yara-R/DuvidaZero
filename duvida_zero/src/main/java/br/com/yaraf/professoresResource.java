package br.com.yaraf;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.Optional;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;


@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class professoresResource {

    @Inject
    Template professoresTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance professores() {
        return professoresTemplate.instance();
    }

    @Inject
    professoresService professoresService;

    @GET
    @Path("/buscar")
    public String buscarPorCpf(@QueryParam("cpf") String cpf) {
        Optional<ProfParticular> optionalProf = this.professoresService.buscarPorCpf(cpf);
        if (optionalProf.isPresent()) {
            ProfParticular professor = optionalProf.get();
            return String.format(
                "CPF: %s\nNome: %s\nTelefone: %s\nEmail: %s\nCidade: %s\nBairro: %s\nRua: %s\nNúmero: %d\nApartamento: %s",
                professor.getCpf(), professor.getNome(), professor.getTelefone(), professor.getEmail(),
                professor.getCidade(), professor.getBairro(), professor.getRua(), professor.getNumero(),
                professor.getApartamento()
            );
        } else {
            return "Professor não encontrado";
        }
    }


    @GET
    @Path("/adicionar")
    public String adicionarProfessor(ProfParticular professor) {
        professoresService.inserirProfessor(professor);
        return "Professor adicionado com sucesso!";
    }


    @GET
    @Path("/deletar")
    public String deletarProf(@QueryParam("cpf") String cpf) {
        this.professoresService.deletarProf(cpf);
        return "Professor deletado com sucesso!";
    }


}
