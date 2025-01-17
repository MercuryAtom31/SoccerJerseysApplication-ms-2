package com.soccerjerseysapplication.orders.businesslayer;


import com.soccerjerseysapplication.customers.utils.exceptions.NotFoundException;
import com.soccerjerseysapplication.orders.domainclientlayer.jerseys.JerseyRequestModel;
import com.soccerjerseysapplication.orders.utils.exceptions.InvalidInputException;
import com.soccerjerseysapplication.orders.dataaccesslayer.*;
import com.soccerjerseysapplication.orders.domainclientlayer.customers.CustomerResponseModel;
import com.soccerjerseysapplication.orders.domainclientlayer.jerseys.JerseyResponseModel;
import com.soccerjerseysapplication.orders.domainclientlayer.teams.TeamResponseModel;
import com.soccerjerseysapplication.orders.mapperlayer.OrderRequestMapper;
import com.soccerjerseysapplication.orders.mapperlayer.OrderResponseMapper;
import com.soccerjerseysapplication.orders.presentationlayer.OrderRequestModel;
import com.soccerjerseysapplication.orders.presentationlayer.OrderResponseModel;

import com.soccerjerseysapplication.orders.domainclientlayer.customers.CustomerServiceClient;
import com.soccerjerseysapplication.orders.domainclientlayer.jerseys.JerseyServiceClient;
import com.soccerjerseysapplication.orders.domainclientlayer.teams.TeamServiceClient;
import com.soccerjerseysapplication.orders.presentationlayer.TeamJerseyOrder;
import com.soccerjerseysapplication.orders.utils.exceptions.ItemUnavailableException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderResponseMapper orderResponseMapper;
    private final OrderRequestMapper orderRequestMapper;

    private final JerseyServiceClient jerseyServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final TeamServiceClient teamServiceClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderResponseMapper orderResponseMapper,
                            OrderRequestMapper orderRequestMapper,
                            JerseyServiceClient jerseyServiceClient,
                            CustomerServiceClient customerServiceClient,
                            TeamServiceClient teamServiceClient) {
        this.orderRepository = orderRepository;
        this.orderResponseMapper = orderResponseMapper;
        this.orderRequestMapper = orderRequestMapper;
        this.jerseyServiceClient = jerseyServiceClient;
        this.customerServiceClient = customerServiceClient;
        this.teamServiceClient = teamServiceClient;
    }

    @Override
    public List<OrderResponseModel> getAllOrdersForCustomer(String customerId) {
        //verify customer exists
        CustomerResponseModel foundCustomer = customerServiceClient.getCustomerByCustomerId(customerId);
        if (foundCustomer == null) {
            throw new InvalidInputException("CustomerId provided is invalid: " + customerId);
        }
        List<OrderResponseModel> orderResponseModelList = new ArrayList<>();
        List<Order> customerOrders = orderRepository.findAllByCustomerIdentifier_CustomerId(customerId);
        //for each of these orders you will get by id each service and save their response model to a variable
        // once you have all 3 response models you will call the mapper method "orderAndDetailsToOrderResponseModel"
        // which will return one OrderResponse Model you will then add that order response model
        // to a variable storing a List of order response model and then once you finish iterating over
        // the orders you will have those orders list in a orderResponse list and that's what you return to the controller.
        for (Order i: customerOrders){
            CustomerResponseModel customerResponseModel = customerServiceClient.getCustomerByCustomerId(i.getCustomerIdentifier().getCustomerId());
            TeamResponseModel teamResponseModel = teamServiceClient.getTeamById(i.getTeamIdentifier().getTeamId());
            JerseyResponseModel jerseyResponseModel = jerseyServiceClient.getJerseyById(i.getJerseyIdentifier().getJerseyId());
            orderResponseModelList.add(orderResponseMapper.orderAndDetailsToOrderResponseModel(i, customerResponseModel, jerseyResponseModel, teamResponseModel));
        }
        return orderResponseModelList;
    }

    @Override
    public OrderResponseModel getCustomerOrderByOrderId(String customerId, String orderId) {

        //Verifying customer's existence.
        CustomerResponseModel foundCustomer = customerServiceClient.getCustomerByCustomerId(customerId);
        if (foundCustomer == null) {
            throw new InvalidInputException("CustomerId provided is invalid: " + customerId);
        }
        Order foundOrder = orderRepository.findOrderByCustomerIdentifier_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId);
        if (foundOrder == null) {
            throw new NotFoundException("OrderId provided is unknown: " + orderId);
        }

        TeamResponseModel teamResponseModel = teamServiceClient.getTeamById(foundOrder.getTeamIdentifier().getTeamId());

        TeamResponseModel foundTeam = teamServiceClient.getTeamById(teamResponseModel.getTeamId());
        if (foundTeam == null) {
            throw new NotFoundException("TeamId is invalid: " + teamResponseModel.getTeamId());
        }

        JerseyResponseModel jerseyResponseModel = jerseyServiceClient.getJerseyById(foundOrder.getJerseyIdentifier().getJerseyId());
        JerseyResponseModel foundJersey = jerseyServiceClient.getJerseyById(jerseyResponseModel.getJerseyId());
        if (foundJersey == null) {
            throw new NotFoundException("JerseyId is invalid: " + jerseyResponseModel.getJerseyId());
        }
        return orderResponseMapper.orderAndDetailsToOrderResponseModel(foundOrder, foundCustomer, foundJersey, foundTeam);
    }

//    @Override
//    public OrderResponseModel createCustomerOrder(String customerId, OrderRequestModel orderRequestModel) {
//
//        // Verify customer exists
//        CustomerResponseModel foundCustomer = customerServiceClient.getCustomerByCustomerId(customerId);
//        if (foundCustomer == null) {
//            throw new NotFoundException("CustomerId provided is invalid: " + customerId);
//        }
//
//        // Verify jersey exists
//        JerseyResponseModel foundJersey = jerseyServiceClient.getJerseyById(orderRequestModel.getJerseyIdentifier());
//        if (foundJersey == null) {
//            throw new NotFoundException("JerseyId provided is invalid: " + orderRequestModel.getJerseyIdentifier());
//        }
//
//        // Verify team exists
//        TeamResponseModel foundTeam = teamServiceClient.getTeamById(orderRequestModel.getTeamIdentifier());
//        if (foundTeam == null) {
//            throw new NotFoundException("TeamId is invalid: " + orderRequestModel.getTeamIdentifier());
//        }
//
//        // Retrieve the quantity requested by the user
//        int quantityRequested = orderRequestModel.getQuantity();
//        if (quantityRequested > foundJersey.getQuantity()) {
//            throw new ItemUnavailableException("Insufficient stock for Jersey ID: " + orderRequestModel.getJerseyIdentifier() +
//                    ", Available: " + foundJersey.getQuantity() +
//                    ", Requested: " + quantityRequested);
//        }
//
//        JerseyRequestModel jerseyRequestModel = JerseyRequestModel.builder()
//                .price(foundJersey.getPrice())
//                .color(foundJersey.getColor())
//                .size(foundJersey.getSize())
//                .styles(foundJersey.getStyles())
//                .quantity(foundJersey.getQuantity() - quantityRequested)
//                .build();
//
//        // Update jersey stock
//        jerseyServiceClient.updateJersey(foundJersey.getJerseyId(), jerseyRequestModel);
//
//        // Convert request model to entity and save
//        CustomerIdentifier customerIdentifier = new CustomerIdentifier(customerId);
//        JerseyIdentifier jerseyIdentifier = new JerseyIdentifier(orderRequestModel.getJerseyIdentifier());
//        TeamIdentifier teamIdentifier = new TeamIdentifier(orderRequestModel.getTeamIdentifier());
//
//        Order order = orderRequestMapper.requestModelToEntity(orderRequestModel, customerIdentifier, jerseyIdentifier, teamIdentifier);
//        order.setOrderDate(new Date().toString());
//        double jerseyPrice = jerseyRequestModel.getPrice();
//        double total = jerseyPrice * order.getQuantity();
//        order.setTotalPriceOrder(total);
//        Order savedOrder = orderRepository.save(order);
//
//        // Convert entity to response model
//        return orderResponseMapper.orderAndDetailsToOrderResponseModel(savedOrder, foundCustomer, foundJersey, foundTeam);
//    }
    @Override
    public OrderResponseModel createCustomerOrder(String customerId, OrderRequestModel orderRequestModel) {

        // Verify customer exists
        CustomerResponseModel foundCustomer = customerServiceClient.getCustomerByCustomerId(customerId);
        if (foundCustomer == null) {
            throw new NotFoundException("CustomerId provided is invalid: " + customerId);
        }

        // Verify jersey exists
        JerseyResponseModel foundJersey = jerseyServiceClient.getJerseyById(orderRequestModel.getJerseyIdentifier());
        if (foundJersey == null) {
            throw new NotFoundException("JerseyId provided is invalid: " + orderRequestModel.getJerseyIdentifier());
        }

        // Verify team exists
        TeamResponseModel foundTeam = teamServiceClient.getTeamById(orderRequestModel.getTeamIdentifier());
        if (foundTeam == null) {
            throw new NotFoundException("TeamId is invalid: " + orderRequestModel.getTeamIdentifier());
        }

        // Retrieve the quantity requested by the user
        int quantityRequested = orderRequestModel.getQuantity();
        if (quantityRequested > foundJersey.getQuantity()) {
            throw new ItemUnavailableException("Insufficient stock for Jersey ID: " + orderRequestModel.getJerseyIdentifier() +
                    ", Available: " + foundJersey.getQuantity() +
                    ", Requested: " + quantityRequested);
        }

        JerseyRequestModel jerseyRequestModel = JerseyRequestModel.builder()
                .price(foundJersey.getPrice())
                .color(foundJersey.getColor())
                .size(foundJersey.getSize())
                .styles(foundJersey.getStyles())
                .quantity(foundJersey.getQuantity() - quantityRequested)
                .build();

        // Update jersey stock
        jerseyServiceClient.updateJersey(foundJersey.getJerseyId(), jerseyRequestModel);

        // Convert request model to entity and save
        Order order = orderRequestMapper.requestModelToEntity(orderRequestModel, new OrderIdentifier());
        order.setCustomerIdentifier(new CustomerIdentifier(customerId));
        order.setOrderDate(new Date().toString());
        double jerseyPrice = jerseyRequestModel.getPrice();
        double total = jerseyPrice * order.getQuantity();
        order.setTotalPriceOrder(total);
        Order savedOrder = orderRepository.save(order);

        // Convert entity to response model
        return orderResponseMapper.orderAndDetailsToOrderResponseModel(savedOrder, foundCustomer, foundJersey, foundTeam);
    }

//    @Override
//    public OrderResponseModel updateCustomerOrder(String customerId, String orderId, OrderRequestModel orderRequestModel) {
//
//        // Verify customer exists
//        CustomerResponseModel foundCustomer = customerServiceClient.getCustomerByCustomerId(customerId);
//        if (foundCustomer == null) {
//            throw new InvalidInputException("CustomerId provided is invalid: " + customerId);
//        }
//
//        // Verify that the orderId is valid
//        Order foundOrder = orderRepository.findOrderByCustomerIdentifier_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId);
//        if (foundOrder == null) {
//            throw new NotFoundException("OrderId provided is unknown: " + orderId);
//        }
//
//        TeamResponseModel foundTeam = teamServiceClient.getTeamById(foundOrder.getTeamIdentifier().getTeamId());
//
//        // Verify jersey exists
//        JerseyResponseModel foundJersey = jerseyServiceClient.getJerseyById(orderRequestModel.getJerseyIdentifier());
//        if (foundJersey == null) {
//            throw new NotFoundException("JerseyId provided is invalid: " + orderRequestModel.getJerseyIdentifier());
//        }
//
//        int quantity = 0;
//        // Check if the order request model quantity is different
//        if(orderRequestModel.getQuantity() != foundOrder.getQuantity()){
//            // Check if the order request model asks for more jerseys
//            if(orderRequestModel.getQuantity() > foundOrder.getQuantity()){
//                // Positive difference between the updated quantity and the quantity originally ordered to check if the updated quantity is within the quantity left in the jersey service
//                int differenceInOrderQuantity = orderRequestModel.getQuantity() - foundOrder.getQuantity();
//                // Check if the difference is larger than what is left in the found jersey to then throw an exception
//                if (differenceInOrderQuantity > foundJersey.getQuantity()) {
//                    throw new ItemUnavailableException("Insufficient stock for Jersey ID: " + orderRequestModel.getJerseyIdentifier() +
//                            ", Available: " + foundJersey.getQuantity() +
//                            ", Requested: " + differenceInOrderQuantity);
//                } else {
//                    quantity = orderRequestModel.getQuantity();
//                    // Update the quantity in the jersey service
//                    JerseyRequestModel jerseyRequestModel = JerseyRequestModel.builder()
//                            .price(foundJersey.getPrice())
//                            .color(foundJersey.getColor())
//                            .size(foundJersey.getSize())
//                            .styles(foundJersey.getStyles())
//                            .quantity(foundJersey.getQuantity() - differenceInOrderQuantity)
//                            .build();
//                    jerseyServiceClient.updateJersey(foundJersey.getJerseyId(), jerseyRequestModel);
//                }
//            } else {
//                // Else order request is asking for fewer jerseys so update jersey quantity in the jersey service
//                quantity = orderRequestModel.getQuantity();
//                int diffInQuantity = foundOrder.getQuantity() - orderRequestModel.getQuantity();
//                JerseyRequestModel jerseyRequestModel = JerseyRequestModel.builder()
//                        .price(foundJersey.getPrice())
//                        .color(foundJersey.getColor())
//                        .size(foundJersey.getSize())
//                        .styles(foundJersey.getStyles())
//                        .quantity(foundJersey.getQuantity() + diffInQuantity)
//                        .build();
//                jerseyServiceClient.updateJersey(foundJersey.getJerseyId(), jerseyRequestModel);
//            }
//        } else {
//            // If there is no change in quantity, don't change the order quantity in the order entity and no need to update the jersey service
//            quantity = foundOrder.getQuantity();
//        }
//
//        CustomerIdentifier customerIdentifier = new CustomerIdentifier(customerId);
//        JerseyIdentifier jerseyIdentifier = new JerseyIdentifier(orderRequestModel.getJerseyIdentifier());
//        TeamIdentifier teamIdentifier = new TeamIdentifier(orderRequestModel.getTeamIdentifier());
//
//        Order updatedOrder = orderRequestMapper.requestModelToEntity(orderRequestModel, customerIdentifier, jerseyIdentifier, teamIdentifier);
//        updatedOrder.setId(foundOrder.getId());
//        updatedOrder.setOrderDate(foundOrder.getOrderDate());
//        updatedOrder.setTotalPriceOrder(foundOrder.getTotalPriceOrder());
//        updatedOrder.setQuantity(quantity);
//
//        Order savedOrder = orderRepository.save(updatedOrder);
//        // Need to change the status depending on the order
//
//        return orderResponseMapper.orderAndDetailsToOrderResponseModel(savedOrder, foundCustomer, foundJersey, foundTeam);
//    }
    @Override
    public OrderResponseModel updateCustomerOrder(String customerId, String orderId, OrderRequestModel orderRequestModel) {

        //verify customer exists
        CustomerResponseModel foundCustomer = customerServiceClient.getCustomerByCustomerId(customerId);
        if (foundCustomer == null) {
            throw new InvalidInputException("CustomerId provided is invalid: " + customerId);
        }

        //verify that the orderId is valid
        //if not valid, throw NotFoundException
        //else, return the OrderResponseModel
        Order foundOrder = orderRepository.findOrderByCustomerIdentifier_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId);
        if (foundOrder == null) {
            throw new NotFoundException("OrderId provided is unknown: " + orderId);
        }

        TeamResponseModel foundTeam = teamServiceClient.getTeamById(foundOrder.getTeamIdentifier().getTeamId());


        //Verify jersey exists
        JerseyResponseModel foundJersey = jerseyServiceClient.getJerseyById(orderRequestModel.getJerseyIdentifier());
        if (foundJersey == null) {
            throw new NotFoundException("JerseyId provided is invalid: " + orderRequestModel.getJerseyIdentifier());
        }

        int quantity = 0;
        //check if the order request model quantity is diff
        if(orderRequestModel.getQuantity() != foundOrder.getQuantity()){
            //check if the order request model asks for more jerseys
            if(orderRequestModel.getQuantity() > foundOrder.getQuantity()){
                //positive difference between the updated quantity and the quantity orginially ordered to check if the updated quantity is within the quantity left in the jersey service
                int differenceInOrderQuantity = orderRequestModel.getQuantity() - foundOrder.getQuantity();
                //check if the difference is larger than what is left in the found jersey to then throw an exception
                if (differenceInOrderQuantity > foundJersey.getQuantity()) {
                    throw new ItemUnavailableException("Insufficient stock for Jersey ID: " + orderRequestModel.getJerseyIdentifier() +
                            ", Available: " + foundJersey.getQuantity() +
                            ", Requested: " + differenceInOrderQuantity);
                } else {
                    quantity = orderRequestModel.getQuantity();
                    //update the quantity in the jersey service
                    JerseyRequestModel jerseyRequestModel = JerseyRequestModel.builder()
                            .price(foundJersey.getPrice())
                            .color(foundJersey.getColor())
                            .size(foundJersey.getSize())
                            .styles(foundJersey.getStyles())
                            .quantity(foundJersey.getQuantity() - differenceInOrderQuantity)
                            .build();
                    jerseyServiceClient.updateJersey(foundJersey.getJerseyId(), jerseyRequestModel);
                }
            } else {
                //else order request is asking for less jerseys so update jersey quantity in the jersey service
                quantity = orderRequestModel.getQuantity();
                int diffInQuantity = foundOrder.getQuantity() - orderRequestModel.getQuantity();
                JerseyRequestModel jerseyRequestModel = JerseyRequestModel.builder()
                        .price(foundJersey.getPrice())
                        .color(foundJersey.getColor())
                        .size(foundJersey.getSize())
                        .styles(foundJersey.getStyles())
                        .quantity(foundJersey.getQuantity() + diffInQuantity)
                        .build();
                jerseyServiceClient.updateJersey(foundJersey.getJerseyId(), jerseyRequestModel);

            }
        } else {
            //if their is no change in quantity don't change the order quantity in the order entity and no need to update the jersey service
            quantity = foundOrder.getQuantity();
        }
        Order updatedOrder = Order.builder()
                .orderDate(foundOrder.getOrderDate())
                .jerseyIdentifier(new JerseyIdentifier(orderRequestModel.getJerseyIdentifier()))
                .teamIdentifier(new TeamIdentifier(orderRequestModel.getTeamIdentifier()))
                .totalPriceOrder(foundOrder.getTotalPriceOrder())
                .orderIdentifier(foundOrder.getOrderIdentifier())
                .customerIdentifier(foundOrder.getCustomerIdentifier())
                .quantity(quantity)
                .id(foundOrder.getId())
                .build();

        Order savedOrder = orderRepository.save(updatedOrder);
        // need to change the status depending on the order

        return orderResponseMapper.orderAndDetailsToOrderResponseModel(savedOrder, foundCustomer, foundJersey, foundTeam);
    }

    @Override
    public void deleteCustomerOrder(String customerId, String orderId) {

        // make sure you have auth to delete and the order has been paid completely
        // deleting should be soft and not hard ie. just change the status of the order to CANCELLED

        //verify customer exists
        CustomerResponseModel foundCustomer = customerServiceClient.getCustomerByCustomerId(customerId);
        if (foundCustomer == null) {
            throw new NotFoundException("CustomerId provided is invalid: " + customerId);
        }

        Order order = orderRepository.findOrderByCustomerIdentifier_CustomerIdAndOrderIdentifier_OrderId(customerId, orderId);

        if (order == null) {
            throw new NotFoundException("OrderId provided is unknown: " + orderId);
        }
        orderRepository.delete(order);
    }


    //OLD CODE
////    @Override
////    public OrderResponseModel createOrder(OrderRequestModel requestModel) {
//////        Order order = orderRequestMapper.modelToEntity(requestModel);
//////        Order savedOrder = orderRepository.save(order);
//////        return orderResponseMapper.entityToResponseModel(savedOrder);
////        return null;
////    }
//
//    @Override
//    public OrderResponseModel getCustomerOrderByOrderId(String orderId, String customerId) {
//        return null;
//    }
//
//    /**
//     * This method, createOrder, is designed to handle
//     * the process of creating a new order in a system.
//     * It takes two parameters: an OrderRequestModel object
//     * that contains details about
//     * the order being placed, and a String customerId that represents
//     * the ID of the customer placing the order.
//     */
//    @Override
//    public OrderResponseModel createOrder(OrderRequestModel requestModel, String customerId) {
////        CustomerResponseModel customerResponseModel = customerServiceClient.getCustomerById(customerId);
//////        if(customerResponseModel == null)
//////            throw new NotFoundException(("Cusotmer Id with the id: " + customerId + " is not valid." ));
////        JerseyResponseModel jerseys = jerseyServiceClient.getJerseyById(requestModel.getJerseyIdentifier());
////        TeamResponseModel teams = teamServiceClient.getTeamById(requestModel.getTeamIdentifier());
////        double totalSum = 0;
////
////        totalSum+= jerseys.getPrice();
////       String orderDate = LocalDate.now().toString();
////        Order order = Order.builder()
////                .customerIdentifier(new CustomerIdentifier(customerResponseModel.getCustomerId()))
////                .jerseyIdentifier(new JerseyIdentifier((jerseys.getJerseyId())))
////                .teamIdentifier(new TeamIdentifier(teams.getTeamId()))
////                .orderIdentifier(new OrderIdentifier())
////                .date(orderDate)//orderDate is the name.
////                .total(totalSum) // totalOrderPrice is the new name.
////                .build();
////
////        Order savedOrder = orderRepository.save(order);
////        return orderResponseMapper.entityToResponseModel(jerseys, teams, savedOrder);
//        return null;
//
//        /*
//        // Create the OrderResponseModel
//        OrderResponseModel orderResponse = new OrderResponseModel();
//        orderResponse.setOrderId(order.getOrderIdentifier().getId());
//        orderResponse.setCustomerId(customerResponseModel.getCustomerId());
//        orderResponse.setJerseyId(jerseys.getJerseyId());
//        orderResponse.setTeamId(teams.getTeamId());
//        orderResponse.setOrderDate(orderDate);
//        orderResponse.setTotalAmount(totalSum);
//
//        return orderResponse;
//         */
//    }
//
////    @Override
////    public OrderResponseModel getOrderById(String orderId) {
//////        Order order = orderRepository.findById(orderId).orElseThrow(
//////                () -> new NotFoundException("Order with ID " + orderId + " not found.")
//////        );
//////        return orderResponseMapper.entityToResponseModel(order);
////        return null;
////    }
//
//    @Override
//    public OrderResponseModel updateOrder(OrderRequestModel requestModel, String orderId) {
////        Order existingOrder = orderRepository.findById(orderId).orElseThrow(
////                () -> new NotFoundException("Order with ID " + orderId + " not found.")
////        );
////
////        if (requestModel.getJerseyIdentifier() != null) {
////            JerseyResponseModel jersey = jerseyServiceClient.getJerseyById(requestModel.getJerseyIdentifier());
////            existingOrder.setJerseyIdentifier(new JerseyIdentifier(jersey.getJerseyId()));
////            existingOrder.setTotal(jersey.getPrice());  // Updating the total price if jersey changes
////        }
////
////        if (requestModel.getTeamIdentifier() != null) {
////            TeamResponseModel team = teamServiceClient.getTeamById(requestModel.getTeamIdentifier());
////            existingOrder.setTeamIdentifier(new TeamIdentifier(team.getTeamId()));
////        }
////
////        Order updatedOrder = orderRepository.save(existingOrder);
////        return orderResponseMapper.entityToResponseModel(updatedOrder);
//        return null;
//    }
//
//
//
//    @Override
//    public void deleteOrder(String orderId) {
//        Order order = orderRepository.findById(orderId).orElseThrow(
//                () -> new NotFoundException("Order with ID " + orderId + " not found.")
//        );
//        orderRepository.delete(order);
//    }
//
//
//    @Override
//    public List<OrderResponseModel> getOrders() {
////        List<Order> orders = orderRepository.findAll();
////        //you need to get for each order object each response model for each of the other entity identifiers because they need to be passed to the OrderResponseMapper
////        return orders.stream()
////                .map(order -> orderResponseMapper.entityToResponseModel(order))
////                .collect(Collectors.toList());
//        return null;
//    }
//
//    @Override
//    public OrderResponseModel getOrderById(String orderId) {
////        Order order = orderRepository.findById(orderId).orElseThrow(
////                () -> new NotFoundException("Order with ID " + orderId + " not found.")
////        );
////        return orderResponseMapper.entityToResponseModel(order);
//        return null;
//    }
//
//    @Override
//    public List<OrderResponseModel> getOrdersByCustomerId(String customerId) {
//        return null;
//    }
//
//    @Override
//    public OrderResponseModel processCustomerOrder(OrderRequestModel orderRequestModel, String customerId) {
//        return null;
//    }


    //NEW CODE
}
