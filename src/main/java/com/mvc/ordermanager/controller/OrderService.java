package com.mvc.ordermanager.controller;

import com.mvc.ordermanager.dao.IOrderDAO;
import com.mvc.ordermanager.model.OrderBean;
import com.mvc.ordermanager.resource.avro.Order;
import com.mvc.ordermanager.resource.avro.OrderState;
import com.mvc.ordermanager.service.FraudService;
import com.mvc.ordermanager.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.mvc.ordermanager.model.OrderBean.fromBean;

@RestController
@RequestMapping("/api/orders")
public class OrderService {

    private static final String CALL_TIMEOUT = "100";

    private final IOrderDAO orderDAO;
    private final OrderDetailsService orderDetailsService;
    private final FraudService fraudService;

    @Autowired
    public OrderService(IOrderDAO orderDAO, OrderDetailsService orderDetailsService, FraudService fraudService) {
        this.orderDAO = orderDAO;
        this.orderDetailsService = orderDetailsService;
        this.fraudService = fraudService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable final Integer id,
                                      @RequestParam(defaultValue = CALL_TIMEOUT) final Long timeout) {
        try {
            OrderBean order = this.orderDAO.getOrder(id, timeout.intValue());
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error fetching the order", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/validated")
    public ResponseEntity<?> getOrderValidation(@PathVariable final Integer id,
                                      @RequestParam(defaultValue = CALL_TIMEOUT) final Long timeout) {
        try {
            OrderBean order = this.orderDAO.getOrder(id, timeout.intValue());
            if (order.getState() == OrderState.VALIDATED || order.getState() == OrderState.FAILED) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unverified order", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error fetching the order", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> submitOrder(@RequestBody final OrderBean order,
                                         @RequestParam(defaultValue = CALL_TIMEOUT) final Long timeout) {
        try {
            final Order bean = fromBean(order);

            this.orderDetailsService.validate(bean);

            if (bean.getState() != OrderState.FAILED) {
                this.fraudService.validate(bean);
            }

            this.orderDAO.saveOrder(bean, timeout.intValue());

            return ResponseEntity.created(URI.create("/api/orders")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error saving the order", HttpStatus.BAD_REQUEST);
        }
    }
}
