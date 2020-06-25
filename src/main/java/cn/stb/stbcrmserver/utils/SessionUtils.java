package cn.stb.stbcrmserver.utils;

import cn.stb.stbcrmserver.domain.Staff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static void cacheStaffSession (HttpServletRequest request, Staff staff) {
        HttpSession session = request.getSession();
        session.setAttribute("staffId", staff.getStaffId());
        session.setAttribute("staff", staff);
    }
}
