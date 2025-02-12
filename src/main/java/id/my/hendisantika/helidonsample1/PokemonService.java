package id.my.hendisantika.helidonsample1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.ClientFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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
    private final String url;
    private final String schema;
    private final String collection;
    private final Client cli;

    @Inject
    public PokemonService(@ConfigProperty(name = "demo.user") String user,
                          @ConfigProperty(name = "demo.password") String password,
                          @ConfigProperty(name = "demo.host") String host,
                          @ConfigProperty(name = "demo.port") String port,
                          @ConfigProperty(name = "demo.schema") String schema,
                          @ConfigProperty(name = "demo.collection") String collection) {
        this.url = "mysqlx://" + user + ":" + password + "@" + host + ":" + port + "/";
        this.schema = schema;
        this.collection = collection;
        this.cli = clientFactory.getClient(this.url, "{\"pooling\":{\"enabled\":true, \"maxSize\":8,\"maxIdleTime\":30000, \"queueTimeout\":10000} }");
    }
}
