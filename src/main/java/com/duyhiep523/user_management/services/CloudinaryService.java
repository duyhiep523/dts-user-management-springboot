package com.duyhiep523.user_management.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.duyhiep523.user_management.common.ResponseMessage;
import com.duyhiep523.user_management.exeptions.CloudinaryUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new CloudinaryUploadException(ResponseMessage.Common.FILE_REQUIRED);
            }
            long maxSize = 5 * 1024 * 1024;
            if (file.getSize() > maxSize) {
                throw new CloudinaryUploadException(ResponseMessage.Common.FILE_SIZE_EXCEEDED);
            }
            String fileExtension = getFileExtension(file.getOriginalFilename());
            if (!isImageFile(fileExtension)) {
                throw new CloudinaryUploadException(ResponseMessage.Common.INVALID_IMAGE_FORMAT);
            }


            String encodedFileName = generateUniqueFileName(fileExtension);
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("public_id", encodedFileName, "resource_type", "auto"));

            return (String) uploadResult.get("secure_url");

        } catch (IOException e) {
            throw new CloudinaryUploadException(ResponseMessage.Common.FILE_READ_ERROR, e);
        } catch (NoSuchAlgorithmException e) {
            throw new CloudinaryUploadException(ResponseMessage.Common.FILE_HASH_ERROR, e);
        } catch (Exception e) {
            throw new CloudinaryUploadException(ResponseMessage.Common.FILE_UPLOAD_ERROR + e.getMessage(), e);
        }
    }

    private boolean isImageFile(String extension) {
        String[] allowedExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "webp", "svg"};
        for (String allowed : allowedExtensions) {
            if (allowed.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }

    private String generateUniqueFileName(String fileExtension) throws NoSuchAlgorithmException {
        String uniqueName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis();
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] encodedHash = digest.digest(uniqueName.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedHash) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString() + "." + fileExtension;
    }
}
