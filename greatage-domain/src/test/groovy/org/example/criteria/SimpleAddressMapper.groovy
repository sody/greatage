package org.example.criteria

import org.example.model.Address
import org.example.model.Country
import org.greatage.domain.EmbedMapper
import org.greatage.domain.EntityMapper
import org.greatage.domain.PropertyMapper

/**
 * @author Ivan Khalopik
 */
public class SimpleAddressMapper extends EmbedMapper<Address> {
    public final PropertyMapper<String> address1$ = property("address1");
    public final PropertyMapper<String> address2$ = property("address2");
    public final PropertyMapper<String> city$ = property("city");
    public final EntityMapper<Long, Country> country$ = entity("country");

    /**
     * Root criteria.
     */
    SimpleAddressMapper() {
        super(null, null);
    }
}
