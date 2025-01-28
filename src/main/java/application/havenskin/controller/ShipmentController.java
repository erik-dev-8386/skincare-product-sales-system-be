package application.havenskin.controller;

import application.havenskin.BusinessObject.Models.Shipments;
import application.havenskin.DTORequest.ShipmentDTO;
import application.havenskin.response.Response;
import application.havenskin.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/haven-skin/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    public Response<List<Shipments>> getAllShipments() {
        Response<List<Shipments>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(shipmentService.getAllShipments());
        return response;
    }

    @PostMapping
    public Response<Shipments> addShipment(@RequestBody Shipments shipment) {
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

    @PutMapping("/{id}")
    public Response<Shipments> updateShipment(@PathVariable String id, @RequestBody ShipmentDTO shipment) {
        Response<Shipments> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(shipmentService.updateShipment(id, shipment));
        return response;
    }

    @DeleteMapping("/{id}")
    public void deleteShipment(@PathVariable String id) {
//        Response<Shipments> response = new Response<>();
//        response.setCode(200);
//        response.setMessage("OK");
//        response.setResult(null);
//        return response;
        shipmentService.deleteShipment(id);
    }
    @PostMapping("/add-list-shipment")
    public Response<List<Shipments>> addListShipment(@RequestBody List<Shipments> shipments) {
        Response<List<Shipments>> response = new Response<>();
        response.setCode(200);
        response.setMessage("OK");
        response.setResult(shipmentService.addListOfShipments(shipments));
        return response;
    }

    @GetMapping("/{shipmentID}")
    public String getOrderNameSearchOrderID(@PathVariable String shipmentID) {
        return shipmentService.searchOrderIDByShipmentCode(shipmentID);
    }

}
