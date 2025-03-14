package application.havenskin.controllers;

import application.havenskin.dataAccess.CreateGHNRequest;
import application.havenskin.dataAccess.ShipmentDTO;
import application.havenskin.models.Shipments;
import application.havenskin.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/haven-skin/shipment")
public class ShipmentController {


    @Autowired
    private ShipmentService shipmentService;
    @PreAuthorize("hasAnyRole('ADMIN','STAFF', 'CUSTOMER')")
    @GetMapping
    public List<Shipments> getAllShipments() {
        return shipmentService.getAllShipments();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
    public Shipments addShipment(@RequestBody Shipments shipment) {
        return shipmentService.createShipment(shipment);
    }
    //
//    @GetMapping("/{id}")
//    public Shipments getShipmentById(@PathVariable String id) {
//        return shipmentService.getShipmentById(id);
//    }
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public Shipments updateShipment(@PathVariable String id, @RequestBody ShipmentDTO shipment) {
        return shipmentService.updateShipment(id, shipment);
    }

//    @DeleteMapping("/{id}")
//    public Shipments deleteShipment(@PathVariable String id) {
    ////        Response<Shipments> response = new Response<>();
    ////        response.setCode(200);
    ////        response.setMessage("OK");
    ////        response.setResult(null);
    ////        return response;
//        return shipmentService.deleteShipment(id);
//    }
//    @PostMapping("/add-list-shipment")
//    public List<Shipments> addListShipment(@RequestBody List<Shipments> shipments) {
//        return shipmentService.addListOfShipments(shipments);
//    }
//
//    @GetMapping("/{shipmentID}")
//    public String getOrderNameSearchOrderID(@PathVariable String shipmentID) {
//        return shipmentService.searchOrderIDByShipmentCode(shipmentID);
//    }
    @PostMapping("/create-an-order")
    public ShipmentDTO addDemo(@RequestBody CreateGHNRequest x) {
        System.out.println(x);
        return shipmentService.buyGHN(x);
    }
    @PostMapping("/provinces")
    public ResponseEntity<List<Map<String,Object>>> getProvinces() {
        return ResponseEntity.ok(shipmentService.getProvince());
    }

    @GetMapping("/districts/{id}")
    public ResponseEntity<List<Map<String,Object>>> getDistricts(@PathVariable int id) {
        return ResponseEntity.ok(shipmentService.getDistrict(id));
    }

    @GetMapping("/wards/{id}")
    public ResponseEntity<List<Map<String,Object>>> getWards(@PathVariable int id) {
        return ResponseEntity.ok(shipmentService.getWards(id));
    }

}
