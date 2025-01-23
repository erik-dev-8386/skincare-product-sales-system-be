package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Shipments;
import application.havenskin.response.Response;
import application.havenskin.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/shipments")
public class ShipmentsController {
    @Autowired
    private ShipmentService shipmentService;
    @GetMapping
    public Response<List<Shipments>> getAllShipments() {
        Response<List<Shipments>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(shipmentService.getAllShipment());
        return response;
    }
    @PostMapping
    public Response<Shipments> createShipment(@RequestBody Shipments shipment) {
        Response<Shipments> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(shipmentService.createShipment(shipment));
        return response;
    }
    @GetMapping("/{id}")
    public Response<Shipments> getShipmentById(@PathVariable String id) {
        Response<Shipments> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(shipmentService.getShipmentById(id));
        return response;
    }
    @PostMapping("/{id}")
    public Response<Shipments> updateShipment(@PathVariable String id, @RequestBody Shipments shipment) {
        Response<Shipments> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(shipmentService.updateShipment(id, shipment));
        return response;
    }
    @DeleteMapping("/{id}")
    public void deleteShipment(@PathVariable String id) {
        shipmentService.deleteShipment(id);
    }

}
