
package acme.features.administrator.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;
import acme.entities.messages.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorOfferRepository extends AbstractRepository {

	@Query("select o from Offer o")
	Collection<Offer> findOffers();

	@Query("select o from Offer o where o.id = :id")
	Offer findOfferById(int id);

	@Query("select cn from Configuration cn")
	Configuration findConfiguration();

}
