package application.havenskin.services;

import application.havenskin.dataAccess.CreateGHNRequest;
import application.havenskin.dataAccess.ShipmentDTO;
import application.havenskin.enums.ShipmentEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Shipments;
import application.havenskin.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private Mapper mapper;

    @Value("${ghn.api.key}")
    private String ghnApiKey;

    @Value("${ghn.shop.id}")
    private String ghnShopId;

    @Value("${ghn.api.url}")
    private String ghnApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    public List<Shipments> getAllShipments() {
        return shipmentRepository.findAll();
    }

//    public Shipments getShipmentById(String id) {
//
//        return shipmentRepository.findById(id).get();
//    }
    public Shipments createShipment(Shipments shipment) {
        return shipmentRepository.save(shipment);
    }
    public Shipments updateShipment(String id, ShipmentDTO shipment) {
        Shipments x = shipmentRepository.findById(id).orElseThrow(()-> new RuntimeException("Shipment not found"));
        mapper.updateShipments(x, shipment);
        return shipmentRepository.save(x);
    }
    public Shipments deleteShipment(String id) {
//        if(!shipmentsRepository.existsById(id)) {
//            throw new RuntimeException("Shipment with id " + id + " does not exist");
//        }
//        shipmentsRepository.deleteById(id);
        Optional<Shipments> x = shipmentRepository.findById(id);
        if (x.isPresent()) {
            Shipments shipment = x.get();
            shipment.setStatus(ShipmentEnums.FAILED_DELIVERY.getShipment_status());
            return shipmentRepository.save(shipment);
        }
        return null;
    }
    public List<Shipments> addListOfShipments(List<Shipments> shipments) {
        return shipmentRepository.saveAll(shipments);
    }
    public String searchOrderIDByShipmentCode(String orderID)
    {
        if(!shipmentRepository.existsById(orderID)){
            throw new RuntimeException("Shipment with id " + orderID + " does not exist");
        }
        return shipmentRepository.findById(orderID).get().getOrderCode();
    }
    public ShipmentDTO buyGHN(CreateGHNRequest createGhnRequest) {
       // String url = ghnApiUrl + "v2/shipping-order/create";
        String url = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", ghnApiKey);  // KHÔNG PHẢI "Token_id"
        headers.set("ShopId", ghnShopId); // KHÔNG PHẢI "Shop_id"

//        HttpEntity<CreateGHNRequest> requestbody = new HttpEntity<>(createGhnRequest, headers);
        Map<String, Object> requestbody = new HashMap<>();
        requestbody.put("to_name", createGhnRequest.getTo_name());
        requestbody.put("from_name", createGhnRequest.getFrom_name());
        requestbody.put("from_phone", createGhnRequest.getFrom_phone());
        requestbody.put("from_address", createGhnRequest.getFrom_address());
        requestbody.put("from_ward_name", createGhnRequest.getFrom_ward_name());
        requestbody.put("from_district_name", createGhnRequest.getFrom_district_name());
        requestbody.put("from_province_name", createGhnRequest.getFrom_province_name());
        requestbody.put("to_phone", createGhnRequest.getTo_phone());
        requestbody.put("to_address", createGhnRequest.getTo_address());
        requestbody.put("to_ward_code", createGhnRequest.getTo_ward_code());
        requestbody.put("to_district_id", createGhnRequest.getTo_district_id());
        requestbody.put("weight", createGhnRequest.getWeight());
        requestbody.put("length", createGhnRequest.getLength());
        requestbody.put("width", createGhnRequest.getWidth());
        requestbody.put("height", createGhnRequest.getHeight());
        requestbody.put("service_type_id", createGhnRequest.getService_type_id());
        requestbody.put("payment_type_id", createGhnRequest.getPayment_type_id());
        requestbody.put("required_note", createGhnRequest.getRequired_note());
        //requestbody.put("Items")
        List<Map<String, Object>> itemsList = new ArrayList<>();
        for (CreateGHNRequest.Item item : createGhnRequest.getItems()) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("name", item.getName());
            itemMap.put("quantity", item.getQuantity());
            itemMap.put("price", item.getPrice());
            itemsList.add(itemMap);
        }
        requestbody.put("items", itemsList);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestbody, headers);
        ResponseEntity<ShipmentDTO> response = restTemplate.exchange(url, HttpMethod.POST,request, ShipmentDTO.class);
        if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            System.out.println("API GHN:" + response.getBody());
            Shipments shipments = mapper.toShipments(response.getBody());
            System.out.println(shipments);
            shipmentRepository.save(shipments);
            return response.getBody();
        }
        return null;
    }
    public List<Map<String, Object>> getProvince(){
        //String url = ghnApiUrl + "/master-data/province";
        String url = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", ghnApiKey);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        System.out.println(response.getBody().get("data"));
        if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            Object data = response.getBody().get("data");
            // ghn tra response:code,message,data

            if(data instanceof List)
                return (List<Map<String, Object>>) data;
        }
        return Collections.emptyList();
    }
    public List<Map<String, Object>> getDistrict(int provinceId){
        //String url = ghnApiUrl + "/master-data/district";
        String url = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("province_id", provinceId);
        HttpEntity<Map<String, Integer>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            Object data = response.getBody().get("data");

            if(data instanceof List)
                return (List<Map<String, Object>>) data;
        }
        return Collections.emptyList();
    }
    public List<Map<String, Object>> getWards(int districtId){
        //String url = ghnApiUrl + "master-data/ward?district_id=" + districtId;
        String url = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?" + districtId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", ghnApiKey);
        Map<String, Object> requestbody = new HashMap<>();
        requestbody.put("district_id", districtId);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestbody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            Object data = response.getBody().get("data");
            if(data instanceof List)
                return (List<Map<String, Object>>) data;
        }
        return Collections.emptyList();
    }
}

