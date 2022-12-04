package com.url_shortener.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @URL(message = "{Url.Format}")
    private String url;
    @Size(max = 30)
    private String alias;
    private String secretCode;
    private Long hitCount;

    public Url(String url, String alias, String secretCode) {
        this.url = url;
        this.alias = alias;
        this.secretCode = secretCode;
    }
}
