package br.com.yaraf;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.Optional;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/materias")
public class materiasResource {
    @Inject
    Template materiasTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance materias() {
        return materiasTemplate.instance();
    } 

    @Inject
    materiasService materiasService;
    
    @GET
    @Path("/buscar")
    public String buscarAulas(@QueryParam("codigoA") String codigoAula) {
        Optional<Materias> optionalMaterias = this.materiasService.buscarAulas(codigoAula);
        if (optionalMaterias.isPresent()) {
            Materias materias = optionalMaterias.get();
            return String.format(
                "Codigo: %s\nCronograma: %s\nHorário: %s",
                materias.getCodigoA(), materias.getCronograma(), materias.getHorario()
            );
        } else {
            return "Matéria não encontrada";
        }
    }

    @GET
    @Path("/adicionar")
    public String adicionarAula(@QueryParam("codigoA") String codigoAula, @QueryParam("cronograma") String cronograma, @QueryParam("horario") String horario) {
        Optional<Materias> aulaAdicionada = materiasService.adicionarAula(codigoAula, cronograma, horario);
        if (aulaAdicionada.equals(aulaAdicionada)) {
            return "Aula adicionada com sucesso!";
        } else {
            return "Não foi possível adicionar aula.";
        }
    }
    
    @GET
    @Path("/editar")
    public String editarAula(@QueryParam("codigoA") String codigoAula, @QueryParam("cronograma") String cronograma, @QueryParam("horario") String horario) {
        Optional<Materias> aulaAtualizada = materiasService.editarAula(codigoAula, cronograma, horario);
        if (aulaAtualizada.isPresent()) {
            return "Professor atualizado com sucesso!";
        } else {
            return "Não foi possível atualizar o professor.";
        }
    }
}