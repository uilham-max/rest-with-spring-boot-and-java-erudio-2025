package br.com.erudio.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração responsável por customizar o comportamento
 * do ObjectMapper utilizado pelo Spring Boot.
 *
 * O ObjectMapper é o componente do Jackson responsável por realizar
 * a conversão entre objetos Java e JSON.
 *
 * Nesta configuração foi adicionado um filtro para impedir que
 * determinados atributos sejam serializados nas respostas da API.
 */
@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){

        ObjectMapper mapper = new ObjectMapper();

        SimpleFilterProvider filters = new SimpleFilterProvider()
                .addFilter(
                        "PersonFilter",
                        SimpleBeanPropertyFilter.serializeAllExcept(
                                "sensitiveData",
                                "last_name"
                        )
                );

        mapper.setFilterProvider(filters);

        return mapper;
    }

}