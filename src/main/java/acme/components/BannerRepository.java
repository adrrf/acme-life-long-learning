
package acme.components;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messages.Banner;
import acme.framework.helpers.MomentHelper;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select count(b) from Banner b where b.finishTime > :date and b.startTime < :date")
	int countBanners(Date date);

	@Query("select b from Banner b where b.finishTime > :date and b.startTime < :date")
	List<Banner> findManyBanners(Date date);

	default Banner findRandomAdvertisement() {
		Banner result;
		int count, index;
		ThreadLocalRandom random;
		List<Banner> list;
		Date date;

		date = MomentHelper.getCurrentMoment();
		count = this.countBanners(date);
		if (count == 0)
			result = null;
		else {
			random = ThreadLocalRandom.current();
			index = random.nextInt(0, count);

			list = this.findManyBanners(date);
			result = list.isEmpty() ? null : list.get(index);
		}

		return result;
	}
}
