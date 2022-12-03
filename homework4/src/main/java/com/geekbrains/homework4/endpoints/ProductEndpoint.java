package com.geekbrains.homework4.endpoints;


import com.geekbrains.homework4.services.ProductService;
import com.geekbrains.homework4.soap.products.GetListProductsRequest;
import com.geekbrains.homework4.soap.products.GetListProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.geekbrains.com/homework4/ws/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getListProductsRequest")
    @ResponsePayload
    public GetListProductsResponse getAllProducts(@RequestPayload GetListProductsRequest getListProductsRequest) {
        GetListProductsResponse getListProductsResponse = new GetListProductsResponse();
        productService.getAllProducts().forEach(getListProductsResponse.getProducts()::add);
        return getListProductsResponse;
    }

//    в postman
//
//    http://localhost:8189/app/ws
//
//    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.geekbrains.com/homework4/ws/products">
//            <soapenv:Header/>
//            <soapenv:Body>
//                <f:getListProductsRequest/>
//            </soapenv:Body>
//        </soapenv:Envelope>

}
