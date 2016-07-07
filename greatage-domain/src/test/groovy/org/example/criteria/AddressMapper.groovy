package org.example.criteria

import org.example.model.Address
import org.greatage.domain.EmbedMapper
import org.greatage.domain.PropertyMapper

/**
 * @author Ivan Khalopik
 */
public class AddressMapper extends EmbedMapper<Address> {
    public final PropertyMapper<String> address1$;
    public final PropertyMapper<String> address2$;
    public final PropertyMapper<String> city$;
    public final CountryMapper country$;

    /**
     * Root criteria.
     */
    AddressMapper() {
        this(null, null);
    }

    /**
     * Embed criteria
     */
    AddressMapper(final String path, final String property) {
        super(path, property);

        address1$ = this.property("address1");
        address2$ = this.property("address2");
        city$ = this.property("city");
        country$ = new CountryMapper(calculatePath(), calculateProperty("country"));
    }
}
