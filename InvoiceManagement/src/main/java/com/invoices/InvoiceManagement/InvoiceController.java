package com.invoices.InvoiceManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public List<Invoices> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Invoices getInvoice(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    @PostMapping
    public Invoices createInvoice(@RequestBody Invoices invoices) {
        return invoiceService.createInvoice(invoices);
    }

    @PutMapping("/{id}")
    public Invoices updateInvoice(@PathVariable Long id, @RequestBody Invoices invoices) {
        return invoiceService.updateInvoice(id, invoices);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }
}
