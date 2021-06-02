package cs.vsu.hotel.service;

import cs.vsu.hotel.entity.Payment;
import cs.vsu.hotel.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void delete(Payment payment) {
        if (payment != null) {
            paymentRepository.delete(payment);
        }
    }
}
