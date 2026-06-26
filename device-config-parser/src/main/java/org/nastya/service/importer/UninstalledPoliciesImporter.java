package org.nastya.service.importer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.nastya.service.JsonImporter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class UninstalledPoliciesImporter implements JsonImporter {

    private final PoliciesImporter delegate;

    public UninstalledPoliciesImporter(PoliciesImporter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void importData(JsonParser parser) {
        try {
            if (parser.currentToken() != JsonToken.START_OBJECT) {
                parser.skipChildren();
                return;
            }

            while (parser.nextToken() != JsonToken.END_OBJECT) {

                parser.nextToken();

                if (parser.currentToken() != JsonToken.START_OBJECT) {
                    parser.skipChildren();
                    continue;
                }

                delegate.importData(parser);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> getSupportedJsonFields() {
        return Set.of("uninstalled_policies");
    }
}