package com.example.rent.DTOs;

import com.example.rent.models.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {

    private String searchName;

    private String searchTelephone;

    private String searchRooms;

    private String searchAddress;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null)
            return searchName == null &&
                    searchTelephone == null &&
                    searchRooms == null &&
                    searchAddress == null;
        if (getClass() != o.getClass()) return false;
        SearchDTO searchDTO = (SearchDTO) o;

        return Objects.equals(searchName, searchDTO.searchName) &&
                Objects.equals(searchTelephone, searchDTO.searchTelephone) &&
                Objects.equals(searchRooms, searchDTO.searchRooms) &&
                Objects.equals(searchAddress, searchDTO.searchAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchName, searchTelephone, searchRooms, searchAddress);
    }
}
