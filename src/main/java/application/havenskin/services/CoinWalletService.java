package application.havenskin.services;

import application.havenskin.dataAccess.CoinWalletDTO;
import application.havenskin.enums.CoinWalletEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.CoinWallets;
import application.havenskin.repositories.CoinWalletsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoinWalletService {
    @Autowired
    private CoinWalletsRepository coinWalletsRepository;
    @Autowired
    private Mapper mapper;

    public List<CoinWallets> getAllCoinWallets() {
        return coinWalletsRepository.findAll();
    }

    public CoinWallets getCoinWalletById(String id) {
        return coinWalletsRepository.findById(id).orElse(null);
    }

    public CoinWallets getCoinWalletByUserId(String userId) {
        return coinWalletsRepository.findByUserId(userId).orElse(null);
    }

    public CoinWallets getCoinWalletByEmail(String email) {
        return coinWalletsRepository.findByUser_Email(email).orElse(null);
    }

    public CoinWallets createCoinWallet(CoinWallets coinWallets) {
        return coinWalletsRepository.save(coinWallets);
    }

    public CoinWallets updateCoinWallet(String id, CoinWalletDTO coinWallet) {
        CoinWallets x = coinWalletsRepository.findById(id).orElse(null);
        if(x == null) {
            throw new RuntimeException("Coin wallet not found");
        }
        mapper.updateCoinWallets(x, coinWallet);
        return coinWalletsRepository.save(x);
    }

    public CoinWallets deleteCoinWallet(String id) {
        Optional<CoinWallets> x = coinWalletsRepository.findById(id);
        if(x.isPresent()) {
            CoinWallets coinWallet = x.get();
            coinWallet.setStatus(CoinWalletEnums.INACTIVE.getValue());
            return coinWalletsRepository.save(coinWallet);
        }
        return null;
    }
}
