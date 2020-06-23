package cn.stb.stbcrmserver.context;

import cn.stb.stbcrmserver.domain.Staff;

public class AcContext {

	private static ThreadLocal<String> LOCAL_Staff_ID = new InheritableThreadLocal<>();
	private static ThreadLocal<Staff> LOCAL_Staff_INFO = new InheritableThreadLocal<>();

	public static void setStaffId(String staffId) {
		LOCAL_Staff_ID.set(staffId);
	}

	public static void setStaff(Staff staff) {
		LOCAL_Staff_INFO.set(staff);
	}

	public static String getStaffId() {
		return LOCAL_Staff_ID.get();
	}

	public static Staff getStaff() {
		return LOCAL_Staff_INFO.get();
	}
}
