package in.sujal.expensio.repository;

import in.sujal.expensio.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    //This interface will automatically provide CRUD operations for ProfileEntity
    //We can add custom query methods here, if needed
    Optional<ProfileEntity> findByEmail(String email); //Optional is used to avoid null pointer exception
    Optional<ProfileEntity> findByActivationToken(String activationToken);
}
