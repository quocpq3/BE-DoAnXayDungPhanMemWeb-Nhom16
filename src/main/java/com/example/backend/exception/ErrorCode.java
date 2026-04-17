package com.example.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1002, "Thông báo lỗi không hợp lệ (Invalid message key)", HttpStatus.BAD_REQUEST),

    // Security & Auth
    UNAUTHORIZED(2001, "Bạn không có quyền chỉnh sửa đánh giá của người khác", HttpStatus.FORBIDDEN),

    // User Errors
    USER_NOT_EXISTED(1001, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),

    // Menu Category Errors
    CATEGORY_NOT_EXISTED(1005, "Không tìm thấy danh mục", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_BLANK(1004, "Tên danh mục không được để trống", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_INVALID(1003, "Tên danh mục phải từ 3 đến 100 ký tự", HttpStatus.BAD_REQUEST),
    SLUG_ALREADY_EXISTS(1006, "Slug already exists", HttpStatus.BAD_REQUEST),

    // Menu Item Errors
    MENU_ITEM_NOT_EXISTED(1006, "Không tìm thấy món ăn", HttpStatus.NOT_FOUND),
    ITEM_NAME_BLANK(1007, "Tên món ăn không được để trống", HttpStatus.BAD_REQUEST),
    SLUG_BLANK(1008, "Slug không được để trống", HttpStatus.BAD_REQUEST),
    CATEGORY_ID_NULL(1009, "Phải chọn danh mục cho món ăn", HttpStatus.BAD_REQUEST),
    BASE_PRICE_NULL(1010, "Giá gốc không được để trống", HttpStatus.BAD_REQUEST),
    PRICE_INVALID(1011, "Giá tiền phải lớn hơn hoặc bằng 0", HttpStatus.BAD_REQUEST),
    DISCOUNT_INVALID(1012, "Giảm giá phải nằm trong khoảng từ 0 đến 100", HttpStatus.BAD_REQUEST),

    //Combo Detail Errors
    COMBO_ID_NULL(1013, "ID Combo không được để trống", HttpStatus.BAD_REQUEST),
    COMPONENT_ID_NULL(1014, "ID món thành phần không được để trống", HttpStatus.BAD_REQUEST),
    QUANTITY_INVALID(1015, "Số lượng món trong combo phải ít nhất là 1", HttpStatus.BAD_REQUEST),

    // Order Errors
    ORDER_NOT_FOUND(1016, "Không tìm thấy đơn hàng", HttpStatus.NOT_FOUND),
    ORDER_DETAILS_REQUIRED(1017, "Đơn hàng phải có ít nhất một món ăn", HttpStatus.BAD_REQUEST),

    // --- Lỗi cho Module Payment ---
    ALREADY_PAID(2002, "Đơn hàng này đã được thanh toán trước đó", HttpStatus.BAD_REQUEST),
    TRANSACTION_ID_REQUIRED(2003, "Bắt buộc phải có mã giao dịch",HttpStatus.BAD_REQUEST),

    ;
    ;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatus statusCode;
}