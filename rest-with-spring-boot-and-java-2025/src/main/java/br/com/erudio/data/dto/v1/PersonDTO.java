package br.com.erudio.data.dto.v1;

import br.com.erudio.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id", "first_name", "   last_name", "gender", "address"})
//@JsonFilter("PersonFilter")
//@JacksonXmlRootElement(localName = "Person")
public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

//    @JsonProperty("sensitive_data")
//    private String sensitiveData;

    @JsonProperty("first_name")
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("last_name")
    private String lastName;

//    @JsonIgnore
    private String address;

    @JsonProperty("phone_number")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    @JsonSerialize(using = GenderSerializer.class)
    private String gender;

//    @JsonProperty("birth_day")
//    @JsonFormat(pattern = "dd/MM/yyyy")
//    private Date birthday;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(id, personDTO.id) && Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(address, personDTO.address) && Objects.equals(gender, personDTO.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, gender);
    }
}
