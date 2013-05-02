package org.example.criteria

import org.example.model.CompanyInfo
import org.greatage.domain.EmbedMapper
import org.greatage.domain.PropertyMapper

/**
 * @author Ivan Khalopik
 */
public class SimpleCompanyInfoMapper extends EmbedMapper<CompanyInfo> {
    public final PropertyMapper<String> code$ = property("code");

    /**
     * Root criteria.
     */
    SimpleCompanyInfoMapper() {
        super(null, null);
    }
}
