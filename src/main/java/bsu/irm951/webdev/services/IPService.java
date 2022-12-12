package bsu.irm951.webdev.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class IPService {

    public String findIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

}
