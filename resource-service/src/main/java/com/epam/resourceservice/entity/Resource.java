package com.epam.resourceservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "data", nullable = false, unique = true)
    private byte[] data;

    public Resource() {
    }

    public Resource(Long id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource resource)) return false;
        return Objects.equals(id, resource.id) && Objects.deepEquals(data, resource.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Arrays.hashCode(data));
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                '}';
    }
}
