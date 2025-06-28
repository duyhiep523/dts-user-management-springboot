package com.duyhiep523.user_management.common;

public class ResponseMessage {

    public static class Authentication {
        public static final String VERIFY_CODE_EXPIRED = "Mã xác thực đã hết hạn";
        public static final String VERIFY_CODE_INCORRECT = "Mã xác thực không chính xác";
        public static final String PERMISSION_DENIED = "Không có quyền truy cập";
        public static final String LOGIN_TO_ACCESS = "Đăng nhập để truy cập tài nguyên này";
        public static final String NAME_PROJECT = "API Quản lý người dùng - Nguyen Duy Hiep";
        public static final String LOGIN_SUCCESS = "Đăng nhập thành công";
    }

    public static class User {
        public static final String REGISTER_SUCCESS = "Đăng ký người dùng thành công";
        public static final String GET_BY_ID_SUCCESS = "Lấy thông tin người dùng theo ID thành công";
        public static final String GET_BY_USERNAME_SUCCESS = "Lấy thông tin người dùng thành công";
        public static final String GET_ALL_SUCCESS = "Lấy danh sách người dùng thành công";
        public static final String UPDATE_AVATAR_SUCCESS = "Cập nhật ảnh đại diện thành công";
        public static final String DELETE_SOFT_SUCCESS = "Xoá người dùng thành công (xoá mềm)";
        public static final String SEARCH_SUCCESS = "Tìm kiếm người dùng thành công";
        public static final String UPDATE_INFO_SUCCESS = "Cập nhật thông tin người dùng thành công";
        public static final String DISABLE_SUCCESS = "Vô hiệu hóa người dùng thành công";
        public static final String REQUIRE_SINGLE_FILE = "Phải chọn đúng 1 ảnh duy nhất";
        public static final String PASSWORD_NOT_MATCH = "Mật khẩu và xác nhận mật khẩu không khớp";
        public static final String USERNAME_ALREADY_EXISTS = "Username đã tồn tại";
        public static final String EMAIL_ALREADY_EXISTS = "Email đã tồn tại";
        public static final String PHONE_ALREADY_EXISTS = "Số điện thoại đã tồn tại";
        public static final String USER_NOT_FOUND = "Người dùng không tồn tại";
        public static final String AVATAR_EMPTY = "Ảnh đại diện không được để trống";
        public static final String USER_ALREADY_DELETED = "Người dùng đã bị xoá trước đó";
        public static final String EMAIL_USED = "Email đã được sử dụng";
        public static final String PHONE_USED = "Số điện thoại đã được sử dụng";
        public static final String USER_ALREADY_DISABLED = "Người dùng đã bị vô hiệu hóa trước đó";
        public static final String USER_NOT_KNOWN = "Không tìm thấy người dùng với thông tin đã cung cấp: ";
        public static final String USERNAME_REQUIRED = "Username không được để trống";
        public static final String PASSWORD_REQUIRED = "Password không được để trống";
        public static final String CONFIRM_PASSWORD_REQUIRED = "Xác nhận mật khẩu là bắt buộc";
        public static final String FULLNAME_REQUIRED = "Họ và tên không được để trống";
        public static final String EMAIL_REQUIRED = "Email là bắt buộc";
        public static final String EMAIL_INVALID = "Email không hợp lệ";
        public static final String EMAIL_FORMAT_INVALID = "Không đúng định dạng email";
        public static final String PHONE_REQUIRED = "Số điện thoại là bắt buộc";
        public static final String PHONE_ONLY_DIGITS = "Số điện thoại chỉ được chứa các chữ số";
        public static final String DOB_PAST = "Ngày sinh phải là ngày trong quá khứ";
        public static final String GENDER_INVALID = "Giới tính không hợp lệ";
        public static final String USER_ALREADY_ADMIN = "Người dùng đã là ADMIN.";
        public static final String UPDATE_ROLE_TO_ADMIN_SUCCESS = "Cập nhật quyền ADMIN thành công";
        public static final String Get_ME_SUCCESS = "Lấy thông tin người dùng thành công";
    }

    public static class Exception {
        public static final String VALIDATION_FAILED = "Dữ liệu không hợp lệ hoặc thiếu trường bắt buộc";
        public static final String JSON_PARSE_ERROR = "Dữ liệu không hợp lệ hoặc sai định dạng. Vui lòng kiểm tra lại.";
        public static final String BAD_REQUEST = "Yêu cầu không hợp lệ";
        public static final String RESOURCE_NOT_FOUND = "Không tìm thấy tài nguyên";
        public static final String ACCESS_DENIED = "Bạn không có quyền truy cập tài nguyên này";
        public static final String INTERNAL_SERVER_ERROR = "Đã xảy ra lỗi không mong muốn";
        public static final String CLOUDINARY_UPLOAD_FAILED = "Tải lên Cloudinary thất bại";
    }
    public static class Common {
        public static final String FILE_REQUIRED = "File không được để trống";
        public static final String FILE_SIZE_EXCEEDED = "Dung lượng file vượt quá 5MB";
        public static final String INVALID_IMAGE_FORMAT = "Chỉ chấp nhận các định dạng ảnh: jpg, jpeg, png, gif, bmp, webp, svg";
        public static final String FILE_READ_ERROR = "Lỗi khi đọc file";
        public static final String FILE_HASH_ERROR = "Lỗi khi tạo mã hóa tên file";
        public static final String FILE_UPLOAD_ERROR = "Lỗi khi upload ảnh: ";
        public static final String INVALID_VALUE = "Giá trị không hợp lệ";
    }
}
