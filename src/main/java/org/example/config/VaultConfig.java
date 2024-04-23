package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import java.util.Map;

@Configuration
public class VaultConfig extends AbstractVaultConfiguration {

    @Override
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication("00000000-0000-0000-0000-000000000000");
    }

    @Override
    public VaultEndpoint vaultEndpoint() {
        VaultEndpoint endpoint = VaultEndpoint.create("localhost", 8200);
        endpoint.setScheme("http");
        return endpoint;

    }

    @Bean
    public VaultTemplate vaultTemplate() {
        return new VaultTemplate(vaultEndpoint(), clientAuthentication());
    }

    @Bean
    public String googleBooksApiKey(VaultTemplate vaultTemplate) {
        VaultResponse response = vaultTemplate.read("secret/data/googlebooksapi");
        String apiKey = "";
        if(response != null && response.getData() != null) {
            Map<String, Object> map = response.getData();
            if(map.containsKey("data")) {
                Object data = map.get("data");
                if(data instanceof Map) {
                    Map<String,String> innerMap = (Map<String, String>)data;
                    if(innerMap.containsKey("key")){
                        apiKey = innerMap.get("key");
                    }
                }
            }

        }else {
            throw new IllegalStateException("Data is not in the expected format.");
        }
        return apiKey;
    }

}
