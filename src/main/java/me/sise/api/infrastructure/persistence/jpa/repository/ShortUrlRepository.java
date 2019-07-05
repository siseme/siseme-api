package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.ShortUrl;
import me.sise.api.infrastructure.persistence.jpa.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    ShortUrl findByPath(String path);

    ShortUrl findByWebUrl(String webUrl);

    List<ShortUrl> findByUser(SocialUser user);
}
