package id.my.hendisantika.helidonsample1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.AddResult;
import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.ClientFactory;
import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.DocResult;
import com.mysql.cj.xdevapi.ModifyStatement;
import com.mysql.cj.xdevapi.Result;
import com.mysql.cj.xdevapi.Schema;
import com.mysql.cj.xdevapi.Session;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Object> getPokemons(Integer limit) throws Exception {
        return callInSession(col -> {
            DocResult result = col.find().limit(limit).execute();
            return processResults(result.fetchAll());
        });
    }

    public AddResult addPokemon(String json) {
        return callInSession(col -> col.add(json).execute()
        );
    }

    public Result removePokemon(String id) {
        return callInSession(col -> col.removeOne(id)
        );
    }

    public Result updatePokemonName(String id, String name) {
        return callInSession(col -> {
                    ModifyStatement modifyStatement = col.modify("_id='" + id + "'").set("name", name);
                    return modifyStatement.execute();
                }
        );
    }

    private ArrayList<Object> processResults(List<DbDoc> docs) {
        ArrayList<Object> result = new ArrayList<>();
        for (DbDoc doc : docs) {
            try {
                result.add(objectMapper.readTree(doc.toString()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    //Helper: run code in a Session, using the collection.
    private <R> R callInSession(CallInSession<R> caller) {
        Session session = cli.getSession();
        Schema schema = session.getSchema(this.schema);
        Collection col = schema.getCollection(this.collection);

        R result = caller.call(col);

        session.close();
        return result;
    }

    @FunctionalInterface
    private interface CallInSession<R> {
        R call(Collection collection);
    }

}
