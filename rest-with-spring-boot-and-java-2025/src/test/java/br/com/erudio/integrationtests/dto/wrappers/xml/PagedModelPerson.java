package br.com.erudio.integrationtests.dto.wrappers.xml;

import br.com.erudio.integrationtests.dto.PersonDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class PagedModelPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("content")
    private List<PersonDTO> content;

    public PagedModelPerson() {}

    public void setContent(List<PersonDTO> content) {
        this.content = content;
    }

    public List<PersonDTO> getContent() {
        return content;
    }
}
