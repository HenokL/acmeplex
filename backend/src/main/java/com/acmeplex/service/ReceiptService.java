package com.acmeplex.service;
import com.acmeplex.model.Receipt;
import com.acmeplex.repository.ReceiptRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    // Constructor-based dependency injection for ReceiptRepository
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    // Method to get all receipts
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    // Method to get receipts by user ID
    public List<Receipt> getReceiptsByUserId(Long userId) {
        return receiptRepository.findByUserId(userId);
    }

    // Method to get receipts by movie ID
    public List<Receipt> getReceiptsByMovieId(Long movieId) {
        return receiptRepository.findByMovieId(movieId);
    }

    // Method to get receipts by showtime ID
    public List<Receipt> getReceiptsByShowtimeId(Long showtimeId) {
        return receiptRepository.findByShowtimeId(showtimeId);
    }

    // Method to get receipts by a specific calculation date
    public List<Receipt> getReceiptsByCalculationDate(Date calculationDate) {
        return receiptRepository.findByCalculationDate(calculationDate);
    }

    // Method to get receipts within a specific date range
    public List<Receipt> getReceiptsByCalculationDateBetween(Date startDate, Date endDate) {
        return receiptRepository.findByCalculationDateBetween(startDate, endDate);
    }

    // Method to get receipts by payment ID
    public List<Receipt> getReceiptsByPaymentId(Long paymentId) {
        return receiptRepository.findByPaymentId(paymentId);
    }

    // Method to get a specific receipt by its ID
    public Receipt getReceiptById(Long id) {
        Optional<Receipt> receipt = receiptRepository.findById(id);
        return receipt.orElse(null); // Return null if the receipt doesn't exist
    }

    // Method to create or update a receipt
    public Receipt createOrUpdateReceipt(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    // Method to delete a receipt by ID
    public void deleteReceipt(Long id) {
        receiptRepository.deleteById(id);
    }
}
