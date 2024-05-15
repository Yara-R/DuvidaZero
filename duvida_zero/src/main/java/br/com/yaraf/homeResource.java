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

@Path("/home")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class homeResource {

    @Inject
    Template homeTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance home() {
        return homeTemplate.instance();
    }

    @Inject
    homeService homeService;

    @GET
    @Path("/buscar")
    public String buscarHome(@QueryParam("Turma") String codigoT, @QueryParam("Aula") String codigoA) {
        Optional<Home> optionalHome = this.homeService.buscarHome(codigoT, codigoA);
        if (optionalHome.isPresent()) {
            Home home = optionalHome.get();
            return String.format(
                "CPF do Professor(a): %s\nNome do Professor(a): %s\nCódigo da Turma: %s\nCódigo da Aula: %s\n",
                home.getCpf(), home.getNome(), home.getCodigoT(), home.getCodigoA()
            );
        } else {
            return "Turma ou aula não encontrada";
        }
    }

    
}