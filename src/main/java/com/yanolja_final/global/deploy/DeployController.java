package com.yanolja_final.global.deploy;

import com.yanolja_final.global.util.ResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployController {

    /**
     * 무중단 배포 시 health 체크를 위한 컨트롤러 매핑
     */
    @GetMapping("/health")
    public ResponseDTO<Void> checkHealth() {
        return ResponseDTO.ok();
    }
}
