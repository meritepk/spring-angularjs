package pk.merite.webapp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import pk.merite.webapp.info.CountryInfo;

public interface CountryRepository extends JpaRepository<CountryInfo, String> {

}
