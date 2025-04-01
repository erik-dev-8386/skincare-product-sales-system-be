package application.havenskin.repositories;

import application.havenskin.models.CoinWallets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoinWalletsRepository extends JpaRepository<CoinWallets, String> {
    Optional<CoinWallets> findByUserId(String userId);
    Optional<CoinWallets> findByUser_Email(String email);
}
