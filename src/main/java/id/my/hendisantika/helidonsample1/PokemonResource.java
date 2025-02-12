package id.my.hendisantika.helidonsample1;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

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
}
