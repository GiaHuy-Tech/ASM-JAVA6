package com.example.demo.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "Ngày tạo đơn phải là ngày hiện tại hoặc trong quá khứ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date createdDate;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Column(columnDefinition = "nvarchar(255)")
    String address;

    @Min(value = 0, message = "Tổng tiền không hợp lệ")
    int total;

    @Min(value = 0, message = "Trạng thái đơn hàng không hợp lệ")
    int status; // 0: chờ xử lý, 1: đã xác nhận, 2: đang giao, 3: hoàn tất, 4: hủy

    @Min(value = 0, message = "Phí vận chuyển không hợp lệ")
    int feeship;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    String paymentMethod;

    @NotNull(message = "Trạng thái thanh toán không được để trống")
    Boolean paymentStatus;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải gồm đúng 10 chữ số")
    String phone;

    @ManyToOne
    @JoinColumn(name = "accountId")
    Account accountId;

    @OneToMany(mappedBy = "orders")
    List<OrderDetail> orderDetails;
}

