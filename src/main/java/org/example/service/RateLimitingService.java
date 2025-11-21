package org.example.service;

import org.example.ratelimit.RateLimitInfo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class RateLimitingService {
    private static final int maximoDePeticiones=10;
    private static final long tiempo=60_000;
    private final Map<String, RateLimitInfo>peticiones=new ConcurrentHashMap<>();

    public boolean permitido(String ip){
        long ahora=System.currentTimeMillis();
        peticiones.putIfAbsent(ip, new RateLimitInfo(0, ahora));
        RateLimitInfo info = peticiones.get(ip);
        synchronized (info){
            if(ahora-info.getWindowStart()>tiempo){
                info.setWindowStart(ahora);
                info.setRequestCount(1);
                return true;
            }
            if(info.getRequestCount()<maximoDePeticiones){
                info.setRequestCount(info.getRequestCount()+1);
                return true;
            }
            return false;
        }
    }

}
