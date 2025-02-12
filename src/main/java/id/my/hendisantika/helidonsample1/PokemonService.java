package id.my.hendisantika.helidonsample1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.ClientFactory;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Created by IntelliJ IDEA.
 * Project : helidon-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 12/02/25
 * Time: 14.20
 * To change this template use File | Settings | File Templates.
 */

@ApplicationScoped
public class PokemonService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ClientFactory clientFactory = new ClientFactory();
    private String url;
    private String schema;
    private String collection;
    private Client cli;
}
