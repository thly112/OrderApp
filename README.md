# ☕ Ứng Dụng Đặt Đồ Cho Quán Cafe - Android App

> Đồ án cuối kỳ môn **Lập Trình Di Động**  
> Trường Đại học Sư Phạm Kỹ Thuật TP.HCM - Khoa Công Nghệ Thông Tin  
> **Nhóm 30** – Học kỳ 2, Năm học 2024–2025

## 👥 Thành viên nhóm

| Họ và tên             | MSSV     |
|-----------------------|----------|
| Trần Mai Di           | 22110291 |
| Trần Thị Thảo Ly      | 22110375 |

**Giảng viên hướng dẫn:** ThS. Nguyễn Hữu Trung

---

## 📱 Giới thiệu

Ứng dụng giúp người dùng:
- Xem menu đồ uống & bánh.
- Đăng ký, đăng nhập, đặt hàng, thanh toán.
- Gửi yêu cầu quên mật khẩu qua email.
- Lưu lịch sử đơn hàng.

Khách chưa đăng nhập (guest) vẫn có thể xem sản phẩm và tìm kiếm, nhưng **phải đăng nhập để mua hàng**.

---

## 🛠️ Công nghệ sử dụng

| Công nghệ | Mô tả |
|----------|-------|
| **Android (Java)** | Phát triển giao diện và logic ứng dụng |
| **Retrofit + RxJava** | Giao tiếp với server qua RESTful API |
| **PHP + MySQL** | Backend xử lý logic và lưu trữ dữ liệu |
| **XAMPP + PhpMyAdmin** | Môi trường phát triển backend local |
| **ZaloPay SDK** | Tích hợp thanh toán trực tuyến |
| **GitHub** | Quản lý mã nguồn và cộng tác nhóm |

---

## 📂 Cấu trúc chính

📁 app/

├── activities/ # Giao diện từng màn hình

├── adapters/ # Adapter cho RecyclerView

├── models/ # Các lớp dữ liệu

├── network/ # Retrofit API Interface

├── utils/ # Các tiện ích dùng chung

└── res/ # Layout, drawable, mipmap, v.v.

---

## 🌐 Chức năng chính

### Người dùng đã đăng nhập:
- ✅ Đăng ký, đăng nhập, đặt lại mật khẩu
- ✅ Xem menu, chi tiết sản phẩm
- ✅ Thêm sản phẩm vào giỏ hàng
- ✅ Thanh toán bằng tiền mặt hoặc **ZaloPay**
- ✅ Cập nhật thông tin cá nhân
- ✅ Xem lại lịch sử đơn hàng

### Khách vãng lai (guest):
- 👀 Xem danh sách sản phẩm
- 🔍 Tìm kiếm sản phẩm
- ❌ Không thể mua hàng nếu chưa đăng nhập

---

## 🔐 API sử dụng

| API         | Phương thức | Mục đích                  |
|-------------|-------------|---------------------------|
| `/dangki.php` | POST        | Đăng ký tài khoản         |
| `/dangnhap.php` | POST     | Đăng nhập tài khoản       |
| `/donhang.php` | POST      | Tạo đơn hàng              |
| `/chitiet.php` | POST      | Lấy sản phẩm phân trang   |
| `/resetpass.php` | POST   | Gửi email đặt lại mật khẩu |
| ...         |             | Xem chi tiết trong tài liệu kỹ thuật |

---

## 💵 Thanh toán

Tích hợp **ZaloPay SDK** cho phép người dùng lựa chọn thanh toán điện tử an toàn.

---

## 📸 Giao diện: https://www.figma.com/design/CskExfRlDUx6lfCkHhWW2t/UI-order-app?node-id=0-1&p=f&t=ECJMPvdDCqdS8s4q-0

---

## 🚀 Hướng dẫn chạy ứng dụng

### Backend
1. Cài đặt XAMPP.
2. Đặt file PHP vào thư mục `htdocs/banhang/`.
3. Tạo CSDL `orderappdb` trong MySQL (import file `orderappdb.sql` nếu có).
4. Kiểm tra API tại `http://localhost/banhang/`.

### Android
1. Clone repo:
   ```bash
   git clone https://github.com/your-username/cafe-order-app.git

📌 Ghi chú
Dữ liệu đơn giản phục vụ demo.

Có thể mở rộng thêm: quản lý sản phẩm, khuyến mãi, đánh giá, v.v.

❤️ Cảm ơn
Xin chân thành cảm ơn thầy Nguyễn Hữu Trung đã hướng dẫn và hỗ trợ nhóm trong suốt quá trình thực hiện đồ án.
