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

    @Inject
    professoresService service;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance professores() {
        return professoresTemplate.instance();
    }


    @GET
    @Path("/buscar")
    public String buscarPorCpf(@QueryParam("cpf") String cpf) {
        Optional<ProfParticular> optionalProf = this.service.buscarPorCpf(cpf);
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
    public String adicionarProfessor(@QueryParam("cpf") String cpf, @QueryParam("nome") String nome, @QueryParam("telefone") String telefone, @QueryParam("email") String email, @QueryParam("cidade") String cidade, @QueryParam("bairro") String bairro, @QueryParam("rua") String rua, @QueryParam("numero") Integer numero, @QueryParam("apartamento") String apartamento) {
        Optional<ProfParticular> professorAdicionado = service.adicionarProfessor(cpf, nome, telefone, email, cidade, bairro, rua, numero, apartamento);
        if (professorAdicionado.equals(professorAdicionado)) {
            return "Professor adicionado com sucesso!";
        } else {
            return "Não foi possível adicionar o professor.";
        }
    }


    @GET
    @Path("/editar")
    public String editarProfessor(@QueryParam("nome") String nome, @QueryParam("telefone") String telefone, @QueryParam("cpf") String cpf) {
        Optional<ProfParticular> professorAtualizado = service.editarProf(nome, telefone, cpf);
        if (professorAtualizado.isPresent()) {
            return "Professor atualizado com sucesso!";
        } else {
            return "Não foi possível atualizar o professor.";
        }
    }


    @GET
    @Path("/deletar")
    public String deletarProf(@QueryParam("cpf") String cpf) {
        Optional<ProfParticular> professorDeletado = service.deletarProf(cpf);
        if (professorDeletado.isPresent()) {
            return "Professor deletado com sucesso!";
        } else {
            return "Não foi possível deletar o professor.";
        }
    }




   
}
