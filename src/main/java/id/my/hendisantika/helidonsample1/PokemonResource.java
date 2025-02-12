package id.my.hendisantika.helidonsample1;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * Project : helidon-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 12/02/25
 * Time: 14.23
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
@Path("/pokemons")
public class PokemonResource {
    private final PokemonService pokemonService;
    private final Integer defaultLimit = 5;

    @Inject
    public PokemonResource(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String defaultList() throws Exception {
        ArrayList<Object> pokemons = pokemonService.getPokemons(defaultLimit);
        return getResult(pokemons);
    }

    @GET
    @Path("/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listLimit(@PathParam("limit") Integer limit) throws Exception {
        ArrayList<Object> pokemons = pokemonService
                .getPokemons(!Objects.isNull(limit) ? limit : defaultLimit);
        return getResult(pokemons);
    }

    @POST
    public long addPokemon(JsonObject jsonObject) {
        return pokemonService
                .addPokemon(jsonObject.toString())
                .getAffectedItemsCount();
    }
}
