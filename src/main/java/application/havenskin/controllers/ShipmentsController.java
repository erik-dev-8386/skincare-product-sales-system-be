package application.havenskin.controllers;

import application.havenskin.models.Shipments;
import application.havenskin.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/shipments")
public class ShipmentsController {
    @Autowired
    private ShipmentService shipmentService;
    @GetMapping
    public List<Shipments> getAllShipments() {
        return shipmentService.getAllShipment();
    }

    @PostMapping
    public Shipments createShipment(@RequestBody Shipments shipment) {
        return shipmentService.createShipment(shipment);
    }

    @GetMapping("/{id}")
    public Shipments getShipmentById(@PathVariable String id) {
        return shipmentService.getShipmentById(id);
    }

    @PutMapping("/{id}")
    public Shipments updateShipment(@PathVariable String id, @RequestBody Shipments shipment) {
        return shipmentService.updateShipment(id, shipment);
    }

    @DeleteMapping("/{id}")
    public void deleteShipment(@PathVariable String id) {
        shipmentService.deleteShipment(id);
    }

}
