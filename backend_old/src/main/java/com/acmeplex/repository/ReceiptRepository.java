package com.acmeplex.repository;
import com.acmeplex.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Date;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    // Find all receipts by a specific user
    List<Receipt> findByUserId(Long userId);

    // Find all receipts for a specific movie
    List<Receipt> findByMovieId(Long movieId);

    // Find all receipts by showtime
    List<Receipt> findByShowtimeId(Long showtimeId);

    // Find all receipts generated on a specific date
    List<Receipt> findByCalculationDate(Date calculationDate);

    // Find all receipts within a specific date range
    List<Receipt> findByCalculationDateBetween(Date startDate, Date endDate);

    // Find all receipts for a specific payment
    List<Receipt> findByPaymentId(Long paymentId);
}
