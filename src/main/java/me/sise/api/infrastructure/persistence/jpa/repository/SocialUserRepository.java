package me.sise.api.infrastructure.persistence.jpa.repository;

import me.sise.api.infrastructure.persistence.jpa.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
    SocialUser findByProviderUserId(String providerUserId);
}
