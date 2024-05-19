package br.com.yaraf;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public Response adicionarProfessor(ProfParticular professor) {
        Optional<ProfParticular> optionalProfessor = service.adicionarProfessor(
            professor.getCpf(),
            professor.getNome(),
            professor.getTelefone(),
            professor.getEmail(),
            professor.getCidade(),
            professor.getBairro(),
            professor.getRua(),
            professor.getNumero(),
            professor.getApartamento()
        );

        if (optionalProfessor.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(optionalProfessor.get()).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao adicionar professor.").build();
        }
    }


    @GET
    @Path("/editar")
    public String editarProfessor(@QueryParam("nome") String nome, @QueryParam("cpf") String cpf) {
        Optional<ProfParticular> professorAtualizado = service.editarProf(nome, cpf);
        if (professorAtualizado.isPresent()) {
            return "Professor atualizado com sucesso!";
        } else {
            return "Não foi possível atualizar o professor.";
        }
    }



//     @DELETE
//     @Path("/deletar")
//     public Response deletarProfessor(@QueryParam("cpf") String cpf) {
//         boolean deletado = this.professoresService.deletarProfessor(cpf);
//         if (deletado) {
//             return Response.status(Response.Status.OK).entity("Professor deletado com sucesso").build();
//         } else {
//             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao deletar professor").build();
//         }
// }


   
}
