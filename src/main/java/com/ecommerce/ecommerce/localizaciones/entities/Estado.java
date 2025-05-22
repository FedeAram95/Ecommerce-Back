package com.ecommerce.ecommerce.localizaciones.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "states")
@AllArgsConstructor
@NoArgsConstructor
public class Estado implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    private Long id;
    @Column(name = "name")
    private String nombre;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "fips_code")
    private String fipsCode;
    private String iso2;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private Integer flag;
    @Column(name = "wikiDataId")
    private String wikiDataId;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private List<Ciudad> ciudades;
}
