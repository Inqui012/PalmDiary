package com.palmD.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palmD.Entity.LoginLog;
import com.palmD.Entity.Users;
import com.palmD.Repository.LoginLog_repo;
import com.palmD.Repository.Users_repo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginLog_serv {
	
	public final LoginLog_repo loginLogRepo; 
	public final Users_repo usersRepo;
	
	public LoginLog saveLogging (HttpServletRequest request, String userId) {
		Users loginUser = usersRepo.findById(userId).orElseThrow(EntityNotFoundException::new);
		String ip = null;
		try {
			ip = getUserIp(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoginLog log = LoginLog.logging(ip, loginUser);
		return loginLogRepo.save(log); 
	}
	
	private String getUserIp(HttpServletRequest request) throws Exception {
	        String ip = null;
	        ip = request.getHeader("X-Forwarded-For");
	        
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("Proxy-Client-IP"); 
	        } 
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("WL-Proxy-Client-IP"); 
	        } 
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("HTTP_CLIENT_IP"); 
	        } 
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("X-Real-IP"); 
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("X-RealIP"); 
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("REMOTE_ADDR");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getRemoteAddr(); 
	        }
			
			return ip;
		}
}
