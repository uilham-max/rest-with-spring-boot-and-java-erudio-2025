package br.com.erudio.integrationtests.dto.wrappers.xml;

import br.com.erudio.integrationtests.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class PagedModelBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("content")
    List<BookDTO> content;

    public PagedModelBook() {}

    public void setContent(List<BookDTO> content) {
        this.content = content;
    }

    public List<BookDTO> getContent() {
        return content;
    }
}
