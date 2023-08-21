package com.invoices.InvoiceManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoices> getAllInvoices() {
        return invoiceRepository.findAll();
    }
    public Invoices getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice with id " + id + " not found"));
    }

    public Invoices createInvoice(Invoices invoices) {
        return invoiceRepository.save(invoices);
    }

    public Invoices updateInvoice(Long id, Invoices updatedInvoices) {
        if (!invoiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invoice with id " + id + " not found");
        }
        updatedInvoices.setId(id);
        return invoiceRepository.save(updatedInvoices);
    }

    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invoice with id " + id + " not found");
        }
        invoiceRepository.deleteById(id);
    }
}

