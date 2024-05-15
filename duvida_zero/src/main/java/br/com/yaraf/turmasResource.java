package br.com.yaraf;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;


@Path("/turmas")
public class turmasResource {
    @Inject
    Template turmasTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance turmas() {
        return turmasTemplate.instance();
    }
}
