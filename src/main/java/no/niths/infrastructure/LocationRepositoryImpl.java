package no.niths.infrastructure;

import no.niths.domain.location.Location;
import no.niths.infrastructure.interfaces.LocationRepository;

import org.springframework.stereotype.Repository;

@Repository
public class LocationRepositoryImpl extends
		AbstractGenericRepositoryImpl<Location> implements LocationRepository {

	public LocationRepositoryImpl() {
		super(Location.class, new Location());
	}
}
