package com.duyhiep523.user_management.specification;

import com.duyhiep523.user_management.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> searchByKeyword(String keyword) {
        return (root, query, cb) -> {
            String kw = "%" + keyword.toLowerCase().trim() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("username")), kw),
                    cb.like(cb.lower(root.get("fullName")), kw),
                    cb.like(cb.lower(root.get("email")), kw),
                    cb.like(cb.lower(root.get("phone")), kw)
            );
        };
    }
}
