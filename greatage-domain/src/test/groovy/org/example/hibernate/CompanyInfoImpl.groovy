package org.example.hibernate

import org.example.model.CompanyInfo
import org.example.model.Country

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 * @author Ivan Khalopik
 */
@Embeddable
public class CompanyInfoImpl implements CompanyInfo {

    @Column(name = "info_code")
    private String code;

    @ManyToOne(targetEntity = CountryImpl.class)
    @JoinColumn(name = "info_country_id")
    private Country country;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public void setCountry(final Country country) {
        this.country = country;
    }
}
