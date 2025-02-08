package application.havenskin.services;

import application.havenskin.models.Shipments;
import application.havenskin.repositories.ShipmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentsRepository shipmentsRepository;

    public List<Shipments> getAllShipment(){
        return shipmentsRepository.findAll();
    }
    public Shipments getShipmentById(String id){
        return shipmentsRepository.findById(id).get();
    }
    public Shipments createShipment(Shipments shipment){
        return shipmentsRepository.save(shipment);
    }
    public Shipments updateShipment(String id,Shipments shipment){
        if(!shipmentsRepository.existsById(id)){
            throw  new RuntimeException("Shipment not found");
        }
        return shipmentsRepository.save(shipment);
    }
    public void deleteShipment(String id){
        if(!shipmentsRepository.existsById(id)){
            throw  new RuntimeException("Shipment not found");
        }
        shipmentsRepository.deleteById(id);
    }
}
