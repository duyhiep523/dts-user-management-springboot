# Hệ thống Quản lý Người dùng

## Mô tả Dự án

Dự án này là một **Hệ thống Quản lý Người dùng (User Management System)** được xây dựng bằng **Spring Boot** và **MySQL**, sử dụng **Docker Compose** để dễ dàng triển khai. Hệ thống cung cấp đầy đủ các chức năng để quản lý người dùng, bao gồm đăng ký, đăng nhập, quản lý thông tin cá nhân, và các chức năng quản trị viên như quản lý vai trò, vô hiệu hóa hoặc xóa người dùng.

Với kiến trúc API RESTful, hệ thống này có thể dễ dàng tích hợp với các ứng dụng frontend khác. Bảo mật được đảm bảo thông qua việc sử dụng **JSON Web Tokens (JWT)** cho xác thực và phân quyền truy cập.

## Các Tính năng Chính

* **Xác thực & Phân quyền:**
    * Đăng ký tài khoản người dùng mới.
    * Đăng nhập và nhận JWT (Access Token & Refresh Token).
    * Làm mới Access Token sử dụng Refresh Token.
    * Phân quyền dựa trên vai trò (ví dụ: `ADMIN`, `USER`) với `@PreAuthorize`.
* **Quản lý người dùng:**
    * Xem chi tiết thông tin người dùng bằng ID hoặc Username.
    * Xem thông tin của người dùng hiện tại (đã đăng nhập).
    * Cập nhật thông tin cá nhân của người dùng.
    * Cập nhật ảnh đại diện (avatar) cho người dùng upload lên cloudinary.
    * Tìm kiếm người dùng với các tùy chọn phân trang và sắp xếp.
* **Tính năng dành cho Quản trị viên (ADMIN):**
    * Lấy danh sách tất cả người dùng.
    * Xóa mềm (soft delete) người dùng.
    * Vô hiệu hóa tài khoản người dùng.

* **Cập nhật vai trò của người dùng thành `ADMIN`(chỉ để test cho phân quyền).**

## Yêu cầu Hệ thống

Để chạy dự án này, bạn cần cài đặt:

* **Docker:**
* **Docker Compose** 
## Cài đặt và Khởi chạy

Để khởi chạy dự án, hãy làm theo các bước sau:

1.  **Clone Repository:**
    ```bash
    git clone https://github.com/duyhiep523/dts-user-management-springboot
    cd dts-user-management-springboot-main
    ```

2.  **Khởi tạo Môi trường Docker Compose:**
    Dự án sử dụng `docker-compose.yml` để định nghĩa các dịch vụ (ứng dụng Spring Boot và cơ sở dữ liệu MySQL).
    Chạy lệnh sau trong thư mục gốc của dự án (nơi chứa file `docker-compose.yml`):

    ```bash
    docker-compose up --build -d
    ```
    * `--build`: Lệnh này sẽ build lại image Docker cho ứng dụng Spring Boot của bạn.
    * `-d`: Chạy các container ở chế độ nền (detached mode).

3.  **Kiểm tra trạng thái các Container:**
    Bạn có thể kiểm tra xem các container đã chạy thành công hay chưa bằng lệnh:
    ```bash
    docker-compose ps
    ```
    Đảm bảo rằng cả `mysql_container-user` và `container-user-app` đều đang ở trạng thái `Up`.

## Cấu hình Cơ sở dữ liệu

* **Database:** `user_db`
* **User:** `user`
* **Password:** `123456`
* **Port:** Cổng MySQL bên trong container (`3306`) được ánh xạ ra cổng `2003` trên máy host của bạn (`localhost:2003`).

## Sử dụng API
Truy cập API thông qua địa chỉ `http://localhost:8080/swagger-ui/index.html` bằng swagger để xem và thử nghiệm các API đã được định nghĩa trong dự án.

### API Xác thực (Authentication API - `/api/auth`)

* **Đăng ký tài khoản mới:**
    * `POST /api/users/register`
    * Body: `RegisterRequest` (ví dụ: username, password, email, v.v.)

* **Đăng nhập:**
    * `POST /api/auth/login`
    * Body: `LoginRequest` (username, password)
    * Phản hồi: `LoginResponse` chứa `token` (Access Token) và `refreshToken`.

* **Làm mới Token:**
    * `POST /api/auth/refresh`
    * Body: `TokenRefreshRequest` (chứa `refreshToken`)
    * Phản hồi: `TokenRefreshResponse` chứa `accessToken` và `refreshToken` mới.

### API Người dùng (User API - `/api/users`)

* **Lấy thông tin người dùng theo ID:**
    * `GET /api/users/id/{userId}`
    * Ví dụ: `GET /api/users/id/123e4567-e89b-12d3-a456-426614174000`

* **Lấy thông tin người dùng theo Username:**
    * `GET /api/users/{username}`
    * Ví dụ: `GET /api/users/john.doe`

* **Lấy thông tin người dùng hiện tại (cần xác thực):**
    * `GET /api/users/me`

* **Tìm kiếm người dùng:**
    * `GET /api/users/search?keyword={keyword}&page={page}&size={size}&sortBy={sortBy}&sortDir={sortDir}`
    * Ví dụ: `GET /api/users/search?keyword=test&page=0&size=5&sortBy=email&sortDir=desc`

* **Cập nhật thông tin người dùng:**
    * `PUT /api/users/{userId}`
    * Body: `UpdateUserRequest` (ví dụ: email, phone, address, v.v.)

* **Cập nhật ảnh đại diện:**
    * `PATCH /api/users/{userId}/avatar`
    * Content-Type: `multipart/form-data`
    * Form Data: `file` (chỉ 1 file ảnh)

---

### API Người dùng (Dành cho ADMIN - `/api/users`)

* **Lấy tất cả người dùng:**
    * `GET /api/users` (Yêu cầu vai trò ADMIN)

* **Xóa mềm người dùng:**
    * `DELETE /api/users/{userId}` (Yêu cầu vai trò ADMIN)

* **Vô hiệu hóa người dùng:**
    * `PATCH /api/users/{userId}/disable` (Yêu cầu vai trò ADMIN)

* **Cập nhật vai trò người dùng thành ADMIN:**
    * `PUT /api/users/{userId}/role/admin` (Yêu cầu vai trò ADMIN)

## Dừng và Xóa Container

Để dừng các container và xóa chúng cùng với các volume dữ liệu:

```bash
docker-compose down -v