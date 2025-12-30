package com.nasa.auth.controller;

import com.nasa.auth.cache.CacheInspectionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cache-inspection")
public class CacheInspectionController {
    private final CacheInspectionService cacheInspectionService;

    CacheInspectionController(CacheInspectionService cacheInspectionService){
        this.cacheInspectionService=cacheInspectionService;
    }

    @GetMapping("/{cacheName}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_NASA_USER','ROLE_EXTERNAL_USER')")
    public String getCache(@PathVariable String cacheName){
        return cacheInspectionService.getCacheConstants(cacheName);
    }

}
