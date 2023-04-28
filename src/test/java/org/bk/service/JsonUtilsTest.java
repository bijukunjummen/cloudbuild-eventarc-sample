package org.bk.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class JsonUtilsTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testConvertJsonToString() throws Exception {
        ObjectNode addressNode = JsonNodeFactory.instance.objectNode()
                .put("street", "test street")
                .put("zip", "0000")
                .put("country", "USA");

        ObjectNode rootNode = JsonNodeFactory.instance.objectNode()
                .put("name", "test");

        rootNode.put("address", addressNode);
        System.out.println(JsonUtils.writeValueAsString(rootNode, objectMapper));
        assertThat(JsonUtils.writeValueAsString(rootNode, objectMapper))
                .contains("""
                        "street":"test""");
    }

    @Test
    void testConvertJsonToType() {
        String json = """
                {
                   "name": "test",
                   "address": {
                     "street": "test street",
                     "zip": "0000",
                     "country": "USA"
                   }
                }
                """;

        Person person = JsonUtils.readValue(json, Person.class, objectMapper);
        assertThat(person).isEqualTo(new Person("test", new Address("test street", "0000", "USA")));
    }

    @Test
    void testConvertListOfTypes() {
        String json = """
                [
                {
                   "name": "test",
                   "address": {
                     "street": "test street",
                     "zip": "0000",
                     "country": "USA"
                   }
                },
                {
                   "name": "test2",
                   "address": {
                     "street": "test street",
                     "zip": "0000",
                     "country": "USA"
                   }
                }               
                ]
                """;

        List<Person> people = JsonUtils.readValue(json,
                new TypeReference<>() {
                },
                objectMapper);
        assertThat(people).hasSize(2);
    }

    private record Address(String street, String zip, String country) {
    }

    private record Person(String name, Address address) {
    }
}
