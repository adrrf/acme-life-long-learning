
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messages.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("select b from Banner b")
	Collection<Banner> findBanners();

	@Query("select b from Banner b where b.id = :id")
	Banner findBannerById(int id);
}
