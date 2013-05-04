package org.example.hibernate

import org.example.model.Address
import org.example.model.CompanyInfo
import org.example.model.Country

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded

/**
 * @author Ivan Khalopik
 */
@Embeddable
public class CompanyInfoImpl implements CompanyInfo {

    @Column(name = "info_code")
    private String code;

    @Embedded
    private AddressImpl address = new AddressImpl();

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(final String code) {
        this.code = code;
    }

    public Address getAddress() {
        return address;
    }
}
