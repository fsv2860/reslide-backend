package com.mygroup.backendReslide.controller;

import com.mygroup.backendReslide.dto.request.InvoiceDetailRequest;
import com.mygroup.backendReslide.exceptions.DiscountNotValidException;
import com.mygroup.backendReslide.exceptions.InvoiceAndDetailDoNotMatchException;
import com.mygroup.backendReslide.exceptions.ProductQuantityException;
import com.mygroup.backendReslide.exceptions.notFound.InvoiceDetailNotFoundException;
import com.mygroup.backendReslide.exceptions.notFound.InvoiceNotFoundException;
import com.mygroup.backendReslide.exceptions.notFound.ProductNotFoundException;
import com.mygroup.backendReslide.model.Product;
import com.mygroup.backendReslide.service.GenericResponseService;
import com.mygroup.backendReslide.service.InvoiceDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice/detail")
@AllArgsConstructor
public class InvoiceDetailController {

    private final InvoiceDetailService invoiceDetailService;
    private final GenericResponseService responseService;

    @PostMapping("/validate")
    public ResponseEntity validate(@RequestBody InvoiceDetailRequest invoiceDetailRequest){
        try{
            return new ResponseEntity(invoiceDetailService.validatesRequest(invoiceDetailRequest), HttpStatus.OK);
        }catch (ProductQuantityException | ProductNotFoundException e){
            return new ResponseEntity(responseService.buildError(e), HttpStatus.CONFLICT);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(responseService.buildError(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{invoiceId}/{detailId}")
    public ResponseEntity delete(@PathVariable Long invoiceId, @PathVariable Long detailId){
        try{
            invoiceDetailService.delete(invoiceId, detailId);
            return new ResponseEntity(responseService.buildInformation("Deleted"), HttpStatus.OK);
        }catch (InvoiceDetailNotFoundException | InvoiceNotFoundException | InvoiceAndDetailDoNotMatchException e){
            return new ResponseEntity(responseService.buildError(e), HttpStatus.CONFLICT);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(responseService.buildError(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/status")
    public ResponseEntity updateStatus(@RequestBody InvoiceDetailRequest invoiceDetailRequest){
        try{
            invoiceDetailService.updateStatus(invoiceDetailRequest);
            return new ResponseEntity("Updated", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(responseService.buildError(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/quantity")
    public ResponseEntity update(@RequestBody InvoiceDetailRequest invoiceDetailRequest){
        try{
            invoiceDetailService.update(invoiceDetailRequest);
            return new ResponseEntity("Updated", HttpStatus.OK);
        }catch (ProductQuantityException | InvoiceDetailNotFoundException |
                InvoiceNotFoundException | InvoiceAndDetailDoNotMatchException |
                DiscountNotValidException e){
            return new ResponseEntity(responseService.buildError(e), HttpStatus.CONFLICT);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(responseService.buildError(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody InvoiceDetailRequest invoiceDetailRequest){
        try{
            invoiceDetailService.create(invoiceDetailRequest);
            return new ResponseEntity(responseService.buildInformation("Created"), HttpStatus.CREATED);
        }catch (ProductQuantityException | InvoiceNotFoundException | DiscountNotValidException e){
            return new ResponseEntity(responseService.buildError(e), HttpStatus.CONFLICT);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(responseService.buildError(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
