package pk.merite.webapp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import pk.merite.webapp.info.LanguageInfo;

public interface LanguageRepository extends JpaRepository<LanguageInfo, String> {
}
