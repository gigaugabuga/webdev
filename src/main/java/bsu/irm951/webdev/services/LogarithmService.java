package bsu.irm951.webdev.services;

import org.springframework.stereotype.Service;

@Service
public class LogarithmService {
    public double evaluateLogarithm(double initValue) {
        return Math.log(initValue);
    }
}
