package br.com.yaraf;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class alunosResource {

    @Inject
    Template alunosTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance alunos() {
        return alunosTemplate.instance();
    }
    
    @Inject
    alunosService alunosService;

    @GET
    @Path("/buscar")
    public String buscarAlunosPorCpf(@QueryParam("cpf") String cpf) {
        List<Alunos> alunosEncontrados = this.alunosService.buscarAlunosPorCpf(cpf);
        
        if (!alunosEncontrados.isEmpty()) {
            StringBuilder resultBuilder = new StringBuilder();
            
            for (Alunos alunos : alunosEncontrados) {
                resultBuilder.append(String.format(
                    "CPF: %s\nNome: %s\nContato: %s\nVestibular Desejado: %s\n" +
                    "Nome do Responsável: %s\nContato do Responsável: %s\nAno Escolar: %d\nColégio: %s\n" +
                    "Turma: %s\n\n",
                    alunos.getCpf(), alunos.getNome(), alunos.getContato(),
                    alunos.getVestibularDesejado(), alunos.getNomeResponsavel(), alunos.getContatoResponsavel(),
                    alunos.getAnoEscolar(), alunos.getColegio(), alunos.getCodigoTurma()
                ));
            }
            
            return resultBuilder.toString();
        } else {
            return "Aluno não encontrado";
        }
    }



    @DELETE
    @Path("/delete")
    public Response deleteAluno(@PathParam("cpf") String cpf) {
        try {
            alunosService.delete(cpf);
            return Response.ok("Aluno deletado com sucesso.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro ao deletar o aluno: " + e.getMessage())
                           .build();
        }
    }

    
}
