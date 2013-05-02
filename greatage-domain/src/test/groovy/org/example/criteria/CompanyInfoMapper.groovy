package org.example.criteria

import org.example.model.CompanyInfo
import org.greatage.domain.EmbedMapper
import org.greatage.domain.PropertyMapper

/**
 * @author Ivan Khalopik
 */
public class CompanyInfoMapper extends EmbedMapper<CompanyInfo> {
    public final PropertyMapper<String> code$;

    /**
     * Root criteria.
     */
    CompanyInfoMapper() {
        this(null, null);
    }

    /**
     * Embed criteria
     */
    CompanyInfoMapper(final String path, final String property) {
        super(path, property)

        code$ = this.property("code");
    }
}
