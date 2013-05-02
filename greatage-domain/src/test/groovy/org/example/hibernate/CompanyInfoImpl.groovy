package org.example.hibernate

import org.example.model.CompanyInfo

import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * @author Ivan Khalopik
 */
@Embeddable
public class CompanyInfoImpl implements CompanyInfo {

    @Column(name = "info_code")
    private String code;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(final String code) {
        this.code = code;
    }
}
