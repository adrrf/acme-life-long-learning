
package acme.features.any.offer;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messages.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyOfferRepository extends AbstractRepository {

	@Query("select o from Offer o")
	Collection<Offer> findOffers();

	@Query("select o from Offer o where o.id = :id")
	Offer findOfferById(int id);

}
