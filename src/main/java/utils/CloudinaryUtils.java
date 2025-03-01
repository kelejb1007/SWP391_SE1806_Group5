/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Nguyen Thanh Trung
 */
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.util.Map;

public class CloudinaryUtils {

    private static final String CLOUD_NAME = "dne2hw6ni";
    private static final String API_KEY = "299295176253241";
    private static final String API_SECRET = "DTIQTToZLlm260kz2sMBA8kzNZ8";

    public static Cloudinary getInstance() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET));
    }
}
