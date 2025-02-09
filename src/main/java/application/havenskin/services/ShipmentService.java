package application.havenskin.services;

import application.havenskin.models.Shipments;
import application.havenskin.dataAccess.ShipmentDTO;
import application.havenskin.enums.ShipmentEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.repositories.ShipmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentsRepository shipmentsRepository;
    @Autowired
    private Mapper mapper;
    public List<Shipments> getAllShipments() {
        return shipmentsRepository.findAll();
    }
    public Shipments getShipmentById(String id) {
        return shipmentsRepository.findById(id).get();
    }
    public Shipments createShipment(Shipments shipment) {
        return shipmentsRepository.save(shipment);
    }
    public Shipments updateShipment(String id, ShipmentDTO shipment) {
        Shipments x = shipmentsRepository.findById(id).orElseThrow(()-> new RuntimeException("Shipment not found"));
        mapper.updateShipments(x, shipment);
        return shipmentsRepository.save(x);
    }
    public Shipments deleteShipment(String id) {
//        if(!shipmentsRepository.existsById(id)) {
//            throw new RuntimeException("Shipment with id " + id + " does not exist");
//        }
//        shipmentsRepository.deleteById(id);
        Optional<Shipments> x = shipmentsRepository.findById(id);
        if (x.isPresent()) {
            Shipments shipment = x.get();
            shipment.setStatus(ShipmentEnums.FAILED_DELIVERY.getShipment_status());
            return shipmentsRepository.save(shipment);
        }
        return null;
    }
    public List<Shipments> addListOfShipments(List<Shipments> shipments) {
        return shipmentsRepository.saveAll(shipments);
    }
    public String searchOrderIDByShipmentCode(String orderID)
    {
        if(!shipmentsRepository.existsById(orderID)){
            throw new RuntimeException("Shipment with id " + orderID + " does not exist");
        }
        return shipmentsRepository.findById(orderID).get().getShipmentCode();
    }
}
