package cn.stb.stbcrmserver.utils;

import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.domain.Staff;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie缓存工具
 */
@Slf4j
public class CookieUtils {
	public static final String STAFF_AUTHC = "staff-authc";


	public static void cacheStaffInfo(HttpServletResponse res, Staff staff) {
		saveCookie(res, STAFF_AUTHC, staff.getStaffId()); // todo 实际应该给staffId加密后在传入cookie
		AcContext.setStaff(staff);
		AcContext.setStaffId(staff.getStaffId());
		log.info("缓存的staffId------ {}", AcContext.getStaffId());
		log.info("缓存的员工信息----- {}", AcContext.getStaff());
	}

	public static void saveCookie(HttpServletResponse res, String cookieName, String value) {
		Cookie cookie = new Cookie(cookieName, value);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		cookie.setHttpOnly(true);
		String domain = "localhost";
		if (StringUtils.isNotEmpty(domain)) {
			cookie.setDomain(domain);
		}
		res.addCookie(cookie);
		log.info("缓存的cookie信息------   {} : {}", cookie.getName(), cookie.getValue());
	}

	public static void removeStaffCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(0);
		}
	}

}
