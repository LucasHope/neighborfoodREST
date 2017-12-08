package main.neighbourfood;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RequirementRepository requirementRepository;

    @Autowired
    ResponseRepository responseRepository;

    @GetMapping("/allorders")
    public Iterable<Orders> naytaKaikki(){
        return orderRepository.findAll();
    }

    @PostMapping("/addorder")
    public void lisaaYksi(@RequestBody Orders order){
        orderRepository.save(order);
    }

    @PostMapping("/removeorder")
    public void poistaYksi(@RequestBody Orders order){
        orderRepository.delete(order);
    }

    @PostMapping ("/addorderwithreqs")
    public void orderWithReqs(@RequestBody Orders order){
        List<Requirement> requirements = order.getRequirements();
        System.out.println(order);
        Orders uusi = orderRepository.save(order);
        System.out.println(uusi);
        int j = uusi.getId();
        System.out.println(j);
        for (int i = 0; i < requirements.size(); i++) {
            requirements.get(i).setOrder(uusi);
            Requirement req = requirementRepository.save(requirements.get(i));
        }
    }
    @GetMapping("/order/{id}/responses")
    public List<Response> responsesForOrder(@PathVariable(name = "id")int order_id) {
        Orders o = orderRepository.findOne(order_id);
        return orderRepository.findAllResponsesForOrder(o);
    }
    @PostMapping("/order/{id}/responses")
    public void addResponse(@PathVariable(name = "id")int order_id, @RequestBody Response response){
        Orders o = orderRepository.findOne(order_id);
        response.setOrder_id(o);
        responseRepository.save(response);
    }

}
