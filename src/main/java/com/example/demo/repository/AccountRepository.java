package com.example.demo.repository;

import java.util.Optional; // Cần import Optional
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    
    // --> THÊM PHƯƠNG THỨC NÀY <--
    // Tìm một tài khoản dựa vào email.
    // Sử dụng Optional<Account> để xử lý trường hợp không tìm thấy email một cách an toàn.
    Optional<Account> findByEmail(String email);

    // (Các phương thức bạn đã có dùng cho chức năng đăng ký)
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}