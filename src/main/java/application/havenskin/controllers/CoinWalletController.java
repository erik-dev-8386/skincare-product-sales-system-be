package application.havenskin.controllers;

import application.havenskin.dataAccess.CoinWalletDTO;
import application.havenskin.models.CoinWallets;
import application.havenskin.services.CoinWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/haven-skin/coinWallets")
public class CoinWalletController {
    @Autowired
    private CoinWalletService coinWalletService;

    @GetMapping
    public List<CoinWallets> getAllCoinWallets(){
        return coinWalletService.getAllCoinWallets();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CoinWallets> getCoinWalletByUserId(@PathVariable String userId) {
        CoinWallets wallet = coinWalletService.getCoinWalletByUserId(userId);
        return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CoinWallets> getCoinWalletByEmail(@PathVariable String email) {
        CoinWallets wallet = coinWalletService.getCoinWalletByEmail(email);
        return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public CoinWallets createCoinWallet(@RequestBody CoinWallets coinWallets){
        return coinWalletService.createCoinWallet(coinWallets);
    }

    @GetMapping("/{id}")
    public CoinWallets getCoinWalletById(@PathVariable String id){
        return coinWalletService.getCoinWalletById(id);
    }

    @PutMapping("/{id}")
    public CoinWallets updateCoinWallet(@PathVariable String id, @RequestBody CoinWalletDTO coinWallet){
        return coinWalletService.updateCoinWallet(id, coinWallet);
    }

    @DeleteMapping("/{id}")
    public CoinWallets deleteCoinWallet(@PathVariable String id){
        return coinWalletService.deleteCoinWallet(id);
    }

    @PostMapping("/apply-discount")
    public ResponseEntity<String> applyCoinWalletDiscount(@RequestParam String orderId, @RequestParam boolean useCoinWallet) {
        try {
            coinWalletService.applyCoinWalletDiscount(orderId, useCoinWallet);
            return ResponseEntity.ok("Discount applied successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
