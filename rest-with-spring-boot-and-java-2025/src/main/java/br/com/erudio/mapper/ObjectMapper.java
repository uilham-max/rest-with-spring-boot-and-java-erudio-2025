package br.com.erudio.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária responsável por realizar o mapeamento
 * entre objetos utilizando a biblioteca Dozer.
 *
 * O objetivo desta classe é facilitar a conversão entre:
 *
 * - Entidades (Entity)
 * - DTOs (Data Transfer Object)
 * - View Objects
 *
 * Exemplo:
 *
 * PersonEntity -> PersonDTO
 * PersonDTO -> PersonEntity
 *
 * O Dozer faz o mapeamento automaticamente entre atributos
 * com nomes compatíveis.
 */
public class ObjectMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {

        List<D> destinations = new ArrayList<>();
        for (O object : origin) {
            destinations.add(mapper.map(object, destination));
        }

        return destinations;
    }
}