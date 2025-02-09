package application.havenskin.controllers;

import application.havenskin.models.Shipments;
import application.havenskin.dataAccess.ShipmentDTO;
import application.havenskin.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    public List<Shipments> getAllShipments() {
        return shipmentService.getAllShipments();
    }

    @PostMapping
    public Shipments addShipment(@RequestBody Shipments shipment) {
       return shipmentService.createShipment(shipment);
    }

    @GetMapping("/{id}")
    public Shipments getShipmentById(@PathVariable String id) {
       return shipmentService.getShipmentById(id);
    }

    @PutMapping("/{id}")
    public Shipments updateShipment(@PathVariable String id, @RequestBody ShipmentDTO shipment) {
        return shipmentService.updateShipment(id, shipment);
    }

    @DeleteMapping("/{id}")
    public Shipments deleteShipment(@PathVariable String id) {
//        Response<Shipments> response = new Response<>();
//        response.setCode(200);
//        response.setMessage("OK");
//        response.setResult(null);
//        return response;
        return shipmentService.deleteShipment(id);
    }
    @PostMapping("/add-list-shipment")
    public List<Shipments> addListShipment(@RequestBody List<Shipments> shipments) {
        return shipmentService.addListOfShipments(shipments);
    }

    @GetMapping("/{shipmentID}")
    public String getOrderNameSearchOrderID(@PathVariable String shipmentID) {
        return shipmentService.searchOrderIDByShipmentCode(shipmentID);
    }

}
